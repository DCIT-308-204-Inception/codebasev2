package gh.ug.smartservice.db;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public static void initialize() {
        String schema;
        try {
            schema = Files.readString(Path.of("data/schema.sql"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        Connection connection = DatabaseConnection.get();
        try (Statement statement = connection.createStatement()) {
            for (String sql : schema.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    statement.execute(trimmed);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not initialize database schema", e);
        }
    }
}
