package storage;

import exception.NotExistStorageException;
import model.Resume;
import sql.ConnectionFactory;
import util.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
        SqlHelper.execute(connectionFactory, "DELETE FROM resume", PreparedStatement::execute);

    }

    @Override
    public void update(Resume resume) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ? RETURNING uuid")) {
//            ps.setString(1, resume.getFullName());
//            ps.setString(2, resume.getUuid());
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException(resume.getUuid());
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

        SqlHelper.execute(connectionFactory, "UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        });
    }

    @Override
    public void save(Resume resume) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
//            ps.setString(1, resume.getUuid());
//            ps.setString(2, resume.getFullName());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

        SqlHelper.execute(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
//            ps.setString(1, uuid);
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException(uuid);
//            }
//
//            Resume resume = new Resume(uuid, rs.getString("full_name"));
//            return resume;
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

        final Resume[] result = new Resume[1];
        SqlHelper.execute(connectionFactory, "SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            result[0] = new Resume(uuid, rs.getString("full_name"));
        });
        return result[0];
    }

    @Override
    public void delete(String uuid) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume r WHERE r.uuid = ?")) {
//            ps.setString(1, uuid);
//            int count = ps.executeUpdate();
//            if(count == 0){
//                throw new NotExistStorageException(uuid);
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

        SqlHelper.execute(connectionFactory, "DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume")) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()){
//                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
        SqlHelper.execute(connectionFactory, "SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
        });
        Collections.sort(list);
        return list;
    }

    @Override
    public int size() {
        final int[] size = {0};
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM resume")) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()){
//                size[0] = rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//
        SqlHelper.execute(connectionFactory, "SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                size[0] = rs.getInt(1);
            }
        });
        return size[0];
    }
}
