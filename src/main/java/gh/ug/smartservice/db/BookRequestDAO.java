package gh.ug.smartservice.db;

import gh.ug.smartservice.model.BookRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookRequestDAO {

    public void insert(BookRequest request) {
        String sql = "INSERT INTO book_requests "
            + "(requestId, sourceShelfId, destinationShelfId, category, urgency, timeSubmitted, deadline, status) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, request.getRequestId());
            statement.setInt(2, request.getSourceShelfId());
            statement.setInt(3, request.getDestinationShelfId());
            statement.setString(4, request.getCategory());
            statement.setInt(5, request.getUrgency());
            statement.setString(6, request.getTimeSubmitted().toString());
            statement.setString(7, request.getDeadline().toString());
            statement.setString(8, request.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not insert book request " + request.getRequestId(), e);
        }
    }

    public List<BookRequest> findAll() {
        String sql = "SELECT requestId, sourceShelfId, destinationShelfId, category, urgency, "
            + "timeSubmitted, deadline, status FROM book_requests";
        List<BookRequest> requests = new ArrayList<>();
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                requests.add(new BookRequest(
                    rows.getInt("requestId"),
                    rows.getInt("sourceShelfId"),
                    rows.getInt("destinationShelfId"),
                    rows.getString("category"),
                    rows.getInt("urgency"),
                    LocalDateTime.parse(rows.getString("timeSubmitted")),
                    LocalDateTime.parse(rows.getString("deadline")),
                    rows.getString("status")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read book requests", e);
        }
        return requests;
    }
}
