package gh.ug.smartservice.engine;

import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.db.DatabaseConnection;
import gh.ug.smartservice.db.DatabaseInitializer;
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

class SchedulingEngineTest {

    private final BookRequestDAO bookRequestDAO = new BookRequestDAO();
    private SchedulingEngine engine;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + tempDir.resolve("test.db"));
        DatabaseInitializer.initialize();
        engine = new SchedulingEngine(bookRequestDAO);

        LocalDateTime base = LocalDateTime.of(2026, 1, 1, 9, 0);
        bookRequestDAO.insert(new BookRequest(1, 0, 1, "issue", 2, base, base.plusHours(2), "NEW"));
        bookRequestDAO.insert(new BookRequest(2, 0, 2, "issue", 9, base.plusMinutes(10), base.plusHours(2), "NEW"));
        bookRequestDAO.insert(new BookRequest(3, 0, 3, "issue", 5, base.plusMinutes(20), base.plusHours(2), "IN_PROGRESS"));
        bookRequestDAO.insert(new BookRequest(4, 0, 4, "issue", 1, base.plusMinutes(30), base.plusHours(2), "COMPLETED"));
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    void fifoOrdersBySubmissionTimeAndExcludesCompleted() {
        List<BookRequest> order = engine.dispatchFifo();
        assertEquals(3, order.size());
        assertEquals(1, order.get(0).getRequestId());
        assertEquals(2, order.get(1).getRequestId());
        assertEquals(3, order.get(2).getRequestId());
    }

    @Test
    void urgencyOrdersHighestFirst() {
        List<BookRequest> order = engine.dispatchByUrgency();
        assertEquals(2, order.get(0).getRequestId());
        assertEquals(3, order.get(1).getRequestId());
        assertEquals(1, order.get(2).getRequestId());
    }

    @Test
    void shiftCapacityDispatchesAllPendingInFifoBatches() {
        List<BookRequest> order = engine.dispatchByShiftCapacity(2);
        assertEquals(3, order.size());
        assertEquals(1, order.get(0).getRequestId());
    }

    @Test
    void urgentInterruptsJumpToFront() {
        List<BookRequest> order = engine.dispatchWithUrgentInterrupts(8);
        assertEquals(2, order.get(0).getRequestId());
    }

    @Test
    void emptyDatabaseProducesEmptyOrders(@TempDir Path anotherTempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + anotherTempDir.resolve("empty.db"));
        DatabaseInitializer.initialize();
        SchedulingEngine emptyEngine = new SchedulingEngine(new BookRequestDAO());

        assertTrue(emptyEngine.dispatchFifo().isEmpty());
        assertTrue(emptyEngine.dispatchByUrgency().isEmpty());
    }

    @Test
    void invalidShiftCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> engine.dispatchByShiftCapacity(0));
    }
}
