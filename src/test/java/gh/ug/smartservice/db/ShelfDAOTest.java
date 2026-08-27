package gh.ug.smartservice.db;

import gh.ug.smartservice.model.Shelf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShelfDAOTest {

    private final ShelfDAO dao = new ShelfDAO();

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
    void insertAndFindAll() {
        dao.insert(new Shelf(1, "Fiction A3", "Ground Floor", "Fiction", 1.0, 2.0));
        dao.insert(new Shelf(2, "Reference Desk", "Reading Room", "Reference", 3.0, 4.0));

        List<Shelf> shelves = dao.findAll();
        assertEquals(2, shelves.size());
        assertEquals("Fiction A3", shelves.get(0).getName());
    }

    @Test
    void findAllOnEmptyTable() {
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    void insertDuplicateIdThrows() {
        dao.insert(new Shelf(1, "Fiction A3", "Ground Floor", "Fiction", 1.0, 2.0));
        assertThrows(IllegalStateException.class,
            () -> dao.insert(new Shelf(1, "Duplicate", "Ground Floor", "Fiction", 0.0, 0.0)));
    }
}
