package gh.ug.smartservice.db;

import gh.ug.smartservice.model.AuditEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuditEventDAO {

    public void insert(AuditEvent event) {
        String sql = "INSERT INTO audit_events (eventType, description, timestamp) VALUES (?, ?, ?)";
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, event.getEventType());
            statement.setString(2, event.getDescription());
            statement.setString(3, event.getTimestamp().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not insert audit event " + event.getEventType(), e);
        }
    }

    public List<AuditEvent> findAll() {
        String sql = "SELECT eventId, eventType, description, timestamp FROM audit_events";
        List<AuditEvent> events = new ArrayList<>();
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                events.add(new AuditEvent(
                    rows.getInt("eventId"),
                    rows.getString("eventType"),
                    rows.getString("description"),
                    LocalDateTime.parse(rows.getString("timestamp"))));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read audit events", e);
        }
        return events;
    }
}
