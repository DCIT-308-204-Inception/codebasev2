package gh.ug.smartservice.db;

import gh.ug.smartservice.model.Path;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PathDAO {

    public void insert(Path path) {
        String sql = "INSERT INTO paths (fromShelfId, toShelfId, distance, travelTime, congestionWeight) "
            + "VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, path.getFromShelfId());
            statement.setInt(2, path.getToShelfId());
            statement.setDouble(3, path.getDistance());
            statement.setDouble(4, path.getTravelTime());
            statement.setDouble(5, path.getCongestionWeight());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(
                "Could not insert path " + path.getFromShelfId() + "->" + path.getToShelfId(), e);
        }
    }

    public List<Path> findAll() {
        String sql = "SELECT fromShelfId, toShelfId, distance, travelTime, congestionWeight FROM paths";
        List<Path> paths = new ArrayList<>();
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                paths.add(new Path(
                    rows.getInt("fromShelfId"),
                    rows.getInt("toShelfId"),
                    rows.getDouble("distance"),
                    rows.getDouble("travelTime"),
                    rows.getDouble("congestionWeight")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read paths", e);
        }
        return paths;
    }
}
