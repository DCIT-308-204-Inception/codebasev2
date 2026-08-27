package gh.ug.smartservice.db;

import gh.ug.smartservice.model.AlgorithmRun;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlgorithmRunDAOTest {

    private final AlgorithmRunDAO dao = new AlgorithmRunDAO();

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + tempDir.resolve("test.db"));
        DatabaseInitializer.initialize();
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    void insertAndFindAllAssignsAutoIncrementId() {
        dao.insert(new AlgorithmRun(0, "QuickSort", 1000, 500_000L, 0, LocalDateTime.now()));
        dao.insert(new AlgorithmRun(0, "MergeSort", 1000, 600_000L, 0, LocalDateTime.now()));

        List<AlgorithmRun> runs = dao.findAll();
        assertEquals(2, runs.size());
        assertTrue(runs.get(0).getRunId() > 0);
        assertTrue(runs.get(1).getRunId() > runs.get(0).getRunId());
    }

    @Test
    void findAllOnEmptyTable() {
        assertTrue(dao.findAll().isEmpty());
    }
}
