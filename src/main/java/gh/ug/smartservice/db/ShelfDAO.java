package gh.ug.smartservice.db;

import gh.ug.smartservice.model.Shelf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShelfDAO {

    public void insert(Shelf shelf) {
        String sql = "INSERT INTO shelves (shelfId, name, section, category, xCoordinate, yCoordinate) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, shelf.getShelfId());
            statement.setString(2, shelf.getName());
            statement.setString(3, shelf.getSection());
            statement.setString(4, shelf.getCategory());
            statement.setDouble(5, shelf.getXCoordinate());
            statement.setDouble(6, shelf.getYCoordinate());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not insert shelf " + shelf.getShelfId(), e);
        }
    }

    public List<Shelf> findAll() {
        String sql = "SELECT shelfId, name, section, category, xCoordinate, yCoordinate FROM shelves";
        List<Shelf> shelves = new ArrayList<>();
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                shelves.add(new Shelf(
                    rows.getInt("shelfId"),
                    rows.getString("name"),
                    rows.getString("section"),
                    rows.getString("category"),
                    rows.getDouble("xCoordinate"),
                    rows.getDouble("yCoordinate")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read shelves", e);
        }
        return shelves;
    }
}
