package storage;

import exception.NotExistStorageException;
import model.*;
import sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", (SqlHelper.SqlExecutor<Void>) ps -> {
            ps.execute();
            return null;
        });

    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            updateContactToDb(resume);
            updateSectionToDb(resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContactToDb(resume, conn);
            insertSectionToDb(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c on r.uuid = c.resume_uuid " +
                        "LEFT JOIN section s on r.uuid = s.resume_uuid " +
                        "    WHERE r.uuid = ? \n",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        resume = addContactToResume(rs, resume);
                        resume = addSectionToResume(rs, resume);
                    } while ((rs.next()));
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        return sqlHelper.transactionExecute(conn -> {
            ResultSet rs;
            String uuid;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                rs = ps.executeQuery();
                Resume resume;
                while (rs.next()) {
                    uuid = rs.getString("uuid");
                    resume = new Resume(uuid, rs.getString("full_name"));
                    resumes.put(uuid, resume);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    uuid = rs.getString("resume_uuid");
                    if (resumes.containsKey(uuid)) {
                        resumes.put(uuid, addContactToResume(rs, resumes.get(uuid)));
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section")) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    uuid = rs.getString("resume_uuid");
                    if (resumes.containsKey(uuid)) {
                        resumes.put(uuid, addSectionToResume(rs, resumes.get(uuid)));
                    }
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                });
    }

    private Resume addContactToResume(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        String type = rs.getString("type");
        if (value != null) {
            resume.addContact(ContactType.valueOf(type), value);
        }
        return resume;
    }

    private Resume addSectionToResume(ResultSet rs, Resume resume) throws SQLException {
        String secValue = rs.getString("section_value");
        String secType = rs.getString("section_type");
        if (secValue != null) {
            SectionType sectionType = SectionType.valueOf(secType);
            Section section = null;
            switch (sectionType) {
                case OBJECTIVE:
                case PERSONAL:
                    section = new SectionText(secValue);
                    break;
                case QUALIFICATIONS:
                case ACHIEVEMENTS:
                    section = new SectionItemsList(secValue.split("\n"));
            }
            resume.addSection(sectionType, section);
        }
        return resume;
    }

    private void insertContactToDb(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("" +
                             "INSERT INTO contact (value, resume_uuid, type) " +
                             "     VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, resume.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSectionToDb(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("" +
                             "INSERT INTO section (section_value, resume_uuid, section_type) " +
                             "     VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                ps.setString(1, e.getValue().toString());
                ps.setString(2, resume.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void updateContactToDb(Resume resume) {
        updateToDb(resume, "DELETE FROM contact WHERE resume_uuid = ?", this::insertContactToDb);
    }

    private void updateSectionToDb(Resume resume) {
        updateToDb(resume, "DELETE FROM section WHERE resume_uuid = ?", this::insertSectionToDb);
    }

    private void updateToDb(Resume resume, String sql, UpdateConSec updateConSec) {
        sqlHelper.transactionExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement(sql)) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            updateConSec.update(resume, conn);
            return null;
        });
    }

    private interface UpdateConSec {
        void update(Resume resume, Connection conn) throws SQLException;
    }


}
