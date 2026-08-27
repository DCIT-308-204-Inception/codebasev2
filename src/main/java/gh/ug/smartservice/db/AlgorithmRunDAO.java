package gh.ug.smartservice.db;

import gh.ug.smartservice.model.AlgorithmRun;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmRunDAO {

    public void insert(AlgorithmRun run) {
        String sql = "INSERT INTO algorithm_runs (algorithmName, inputSize, timeNs, memoryKb, dateRun) "
            + "VALUES (?, ?, ?, ?, ?)";
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, run.getAlgorithmName());
            statement.setInt(2, run.getInputSize());
            statement.setLong(3, run.getTimeNs());
            statement.setLong(4, run.getMemoryKb());
            statement.setString(5, run.getDateRun().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Could not insert algorithm run " + run.getAlgorithmName(), e);
        }
    }

    public List<AlgorithmRun> findAll() {
        String sql = "SELECT runId, algorithmName, inputSize, timeNs, memoryKb, dateRun FROM algorithm_runs";
        List<AlgorithmRun> runs = new ArrayList<>();
        Connection connection = DatabaseConnection.get();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                runs.add(new AlgorithmRun(
                    rows.getInt("runId"),
                    rows.getString("algorithmName"),
                    rows.getInt("inputSize"),
                    rows.getLong("timeNs"),
                    rows.getLong("memoryKb"),
                    LocalDateTime.parse(rows.getString("dateRun"))));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read algorithm runs", e);
        }
        return runs;
    }
}
