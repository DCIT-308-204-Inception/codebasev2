package gh.ug.smartservice.db;

import gh.ug.smartservice.model.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathDAOTest {

    private final PathDAO dao = new PathDAO();

    @BeforeEach
    void setUp(@TempDir java.nio.file.Path tempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + tempDir.resolve("test.db"));
        DatabaseInitializer.initialize();
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    void insertAndFindAll() {
        dao.insert(new Path(1, 2, 10.5, 120.0, 1.2));
        dao.insert(new Path(2, 3, 5.0, 60.0, 1.0));

        List<Path> paths = dao.findAll();
        assertEquals(2, paths.size());
        assertEquals(1, paths.get(0).getFromShelfId());
        assertEquals(2, paths.get(0).getToShelfId());
    }

    @Test
    void findAllOnEmptyTable() {
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    void insertZeroDistancePath() {
        dao.insert(new Path(1, 1, 0.0, 0.0, 1.0));
        assertEquals(1, dao.findAll().size());
    }
}
