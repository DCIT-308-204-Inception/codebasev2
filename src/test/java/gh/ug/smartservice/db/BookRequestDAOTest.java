package gh.ug.smartservice.db;

import gh.ug.smartservice.model.BookRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookRequestDAOTest {

    private final BookRequestDAO dao = new BookRequestDAO();

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
        LocalDateTime now = LocalDateTime.now();
        dao.insert(new BookRequest(1, 10, 20, "issue", 5, now, now.plusHours(1), "pending"));

        List<BookRequest> requests = dao.findAll();
        assertEquals(1, requests.size());
        assertEquals("issue", requests.get(0).getCategory());
        assertEquals(5, requests.get(0).getUrgency());
    }

    @Test
    void findAllOnEmptyTable() {
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    void insertDuplicateIdThrows() {
        LocalDateTime now = LocalDateTime.now();
        dao.insert(new BookRequest(1, 10, 20, "issue", 5, now, now.plusHours(1), "pending"));
        assertThrows(IllegalStateException.class,
            () -> dao.insert(new BookRequest(1, 11, 21, "return", 1, now, now.plusHours(1), "pending")));
    }
}
