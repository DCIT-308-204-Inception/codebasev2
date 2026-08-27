package gh.ug.smartservice.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static String jdbcUrl = "jdbc:sqlite:data/smart_library.db";
    private static Connection connection;

    private DatabaseConnection() {
    }

    /** Points future connections at a different database (used by tests). */
    public static synchronized void useUrl(String url) {
        close();
        jdbcUrl = url;
    }

    public static synchronized Connection get() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(jdbcUrl);
            }
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Could not open database connection", e);
        }
    }

    public static synchronized void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not close database connection", e);
        } finally {
            connection = null;
        }
    }
}
