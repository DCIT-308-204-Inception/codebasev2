package gh.ug.smartservice.engine;

import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.db.DatabaseConnection;
import gh.ug.smartservice.db.DatabaseInitializer;
import gh.ug.smartservice.db.ShelfDAO;
import gh.ug.smartservice.model.BookRequest;
import gh.ug.smartservice.model.Shelf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IndexingEngineTest {

    private final ShelfDAO shelfDAO = new ShelfDAO();
    private final BookRequestDAO bookRequestDAO = new BookRequestDAO();

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + tempDir.resolve("test.db"));
        DatabaseInitializer.initialize();

        shelfDAO.insert(new Shelf(1, "Fiction A3", "Ground Floor", "Fiction", 1.0, 1.0));
        shelfDAO.insert(new Shelf(2, "Fiction A4", "Ground Floor", "Fiction", 1.0, 2.0));
        shelfDAO.insert(new Shelf(3, "Reference Desk", "Reading Room", "Reference", 3.0, 4.0));

        LocalDateTime now = LocalDateTime.now();
        bookRequestDAO.insert(new BookRequest(1, 0, 1, "issue", 5, now, now.plusHours(1), "NEW"));
        bookRequestDAO.insert(new BookRequest(2, 0, 2, "issue", 3, now, now.plusHours(1), "NEW"));
        bookRequestDAO.insert(new BookRequest(3, 0, 3, "return", 1, now, now.plusHours(1), "COMPLETED"));
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    void sortedCategoriesAreDistinctAndOrdered() {
        IndexingEngine engine = new IndexingEngine(shelfDAO, bookRequestDAO);
        assertEquals(List.of("Fiction", "Reference"), engine.sortedCategories());
    }

    @Test
    void sortedStatusesAreDistinct() {
        IndexingEngine engine = new IndexingEngine(shelfDAO, bookRequestDAO);
        assertEquals(List.of("COMPLETED", "NEW"), engine.sortedStatuses());
    }

    @Test
    void findShelvesByCategoryGroupsCorrectly() {
        IndexingEngine engine = new IndexingEngine(shelfDAO, bookRequestDAO);
        List<Shelf> fiction = engine.findShelvesByCategory("Fiction");
        assertEquals(2, fiction.size());
        assertTrue(engine.hasCategory("Fiction"));
        assertFalse(engine.hasCategory("Nonexistent"));
    }

    @Test
    void findRequestsByStatusGroupsCorrectly() {
        IndexingEngine engine = new IndexingEngine(shelfDAO, bookRequestDAO);
        assertEquals(2, engine.findRequestsByStatus("NEW").size());
        assertEquals(1, engine.findRequestsByStatus("COMPLETED").size());
    }

    @Test
    void missingKeyReturnsEmptyList() {
        IndexingEngine engine = new IndexingEngine(shelfDAO, bookRequestDAO);
        assertTrue(engine.findShelvesByCategory("Nonexistent").isEmpty());
        assertTrue(engine.findRequestsByStatus("CANCELLED").isEmpty());
    }

    @Test
    void emptyDatabaseProducesEmptyIndex(@TempDir Path anotherTempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + anotherTempDir.resolve("empty.db"));
        DatabaseInitializer.initialize();
        IndexingEngine emptyEngine = new IndexingEngine(new ShelfDAO(), new BookRequestDAO());

        assertTrue(emptyEngine.sortedCategories().isEmpty());
        assertFalse(emptyEngine.hasCategory("Fiction"));
    }
}
