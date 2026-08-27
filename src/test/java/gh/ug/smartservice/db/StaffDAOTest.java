package gh.ug.smartservice.db;

import gh.ug.smartservice.model.Staff;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaffDAOTest {

    private final StaffDAO dao = new StaffDAO();

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
        dao.insert(new Staff(1, "librarian", 5, 3, "available"));

        List<Staff> staff = dao.findAll();
        assertEquals(1, staff.size());
        assertEquals("librarian", staff.get(0).getRole());
    }

    @Test
    void findAllOnEmptyTable() {
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    void insertDuplicateIdThrows() {
        dao.insert(new Staff(1, "librarian", 5, 3, "available"));
        assertThrows(IllegalStateException.class,
            () -> dao.insert(new Staff(1, "shelver", 6, 1, "busy")));
    }
}
