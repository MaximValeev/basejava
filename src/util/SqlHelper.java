package util;

import exception.StorageException;
import sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

//    public SqlHelper(ConnectionFactory connectionFactory, String SqlQuery, QueryHandle queryHandle) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement(SqlQuery)) {
//            queryHandle.execute(ps);
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    public static  void execute(ConnectionFactory connectionFactory, String sql, QueryHandle queryHandle) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            queryHandle.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }


    public interface QueryHandle {
        void execute(PreparedStatement ps) throws SQLException;
    }

}
