package storage;

import exception.NotExistStorageException;
import model.*;
import sql.SqlHelper;
import util.JsonParser;

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
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            deleteContactsFromDb(resume, conn);
            insertContactsToDb(resume, conn);
            deleteSectionsFromDb(resume, conn);
            insertSectionsToDb(resume, conn);
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
            insertContactsToDb(resume, conn);
            insertSectionsToDb(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM  contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    r = addContactToResume(rs, r);
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM  section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    r = addSectionToResume(rs, r);
                }
            }

            return r;
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
        if (secValue != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("section_type"));
            resume.addSection(sectionType, JsonParser.read(secValue, Section.class));
        }
        return resume;
    }

    private void insertContactsToDb(Resume resume, Connection conn) throws SQLException {
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

    private void insertSectionsToDb(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("" +
                             "INSERT INTO section (section_value, resume_uuid, section_type) " +
                             "     VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                Section section = e.getValue();
                ps.setString(1, JsonParser.write(section, Section.class));
                ps.setString(2, resume.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContactsFromDb(Resume resume, Connection conn) throws SQLException {
        deleteFromDb(resume, conn, "DELETE FROM contact WHERE resume_uuid = ?");
    }

    private void deleteSectionsFromDb(Resume resume, Connection conn) throws SQLException {
        deleteFromDb(resume, conn, "DELETE FROM section WHERE resume_uuid = ?");
    }

    private void deleteFromDb(Resume resume, Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement(sql)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}
