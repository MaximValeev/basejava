package storage;

import exception.NotExistStorageException;
import model.ContactType;
import model.Resume;
import sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE r.uuid = ?\n ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        resume = addContactToResume(rs, resume);
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
        return sqlHelper.execute("" +
                "SELECT * FROM resume r" +
                "    LEFT JOIN contact c2 on r.uuid = c2.resume_uuid" +
                "     ORDER BY r.full_name, r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            String prevId = null;
            Resume resume = null;
            while (rs.next()) {
                String id = rs.getString("uuid");
                if (!id.equals(prevId)) {
                    resume = new Resume(id, rs.getString("full_name"));
                    prevId = id;
                    list.add(resume);
                }
                resume = addContactToResume(rs, resume);
            }
            return list;
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
        if (value != null && type != null) {
            resume.addContact(ContactType.valueOf(type), value);
        }
        return resume;
    }

    private void insertContactToDb(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("INSERT INTO contact (value, resume_uuid, type) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, resume.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void updateContactToDb(Resume resume) {
        sqlHelper.transactionExecute(conn -> {

            try (PreparedStatement ps =
                         conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            insertContactToDb(resume, conn);
            return null;
        });
    }


}
