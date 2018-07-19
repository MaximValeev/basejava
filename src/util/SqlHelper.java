package util;

import exception.StorageException;
import sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(String sql, QueryHandle<T> queryHandle) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            return queryHandle.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface QueryHandle<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

}
