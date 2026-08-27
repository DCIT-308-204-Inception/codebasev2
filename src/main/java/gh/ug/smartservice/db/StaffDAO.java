package gh.ug.smartservice.db;

import gh.ug.smartservice.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    public void insert(Staff staff) {
        String sql = "INSERT INTO staff (staffId, role, homeShelfId, capacity, availabilityStatus) "
            + "VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, staff.getStaffId());
            statement.setString(2, staff.getRole());
            statement.setInt(3, staff.getHomeShelfId());
            statement.setInt(4, staff.getCapacity());
            statement.setString(5, staff.getAvailabilityStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not insert staff " + staff.getStaffId(), e);
        }
    }

    public List<Staff> findAll() {
        String sql = "SELECT staffId, role, homeShelfId, capacity, availabilityStatus FROM staff";
        List<Staff> staffList = new ArrayList<>();
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                staffList.add(new Staff(
                    rows.getInt("staffId"),
                    rows.getString("role"),
                    rows.getInt("homeShelfId"),
                    rows.getInt("capacity"),
                    rows.getString("availabilityStatus")));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read staff", e);
        }
        return staffList;
    }
}
