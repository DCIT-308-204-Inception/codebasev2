package gh.ug.smartservice.engine;

import gh.ug.smartservice.algorithms.optimisation.GreedyAssignment;
import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.db.DatabaseConnection;
import gh.ug.smartservice.db.DatabaseInitializer;
import gh.ug.smartservice.db.StaffDAO;
import gh.ug.smartservice.model.BookRequest;
import gh.ug.smartservice.model.Staff;
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

class OptimisationEngineTest {

    private final BookRequestDAO bookRequestDAO = new BookRequestDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private OptimisationEngine engine;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + tempDir.resolve("test.db"));
        DatabaseInitializer.initialize();
        engine = new OptimisationEngine(bookRequestDAO, staffDAO);

        LocalDateTime now = LocalDateTime.now();
        bookRequestDAO.insert(new BookRequest(1, 5, 5, "issue", 9, now, now.plusHours(1), "NEW"));
        bookRequestDAO.insert(new BookRequest(2, 5, 6, "issue", 5, now, now.plusHours(1), "NEW"));
        bookRequestDAO.insert(new BookRequest(3, 5, 7, "issue", 1, now, now.plusHours(1), "COMPLETED"));

        staffDAO.insert(new Staff(1, "librarian", 5, 3, "available"));
        staffDAO.insert(new Staff(2, "shelver", 6, 0, "busy"));
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    void assignsPendingRequestsPreferringHigherValue() {
        List<GreedyAssignment.Assignment> assignments = engine.assignPendingRequestsToStaff();
        assertEquals(2, assignments.size());
        assertEquals("1", assignments.get(0).taskId());
    }

    @Test
    void zeroCapacityStaffContributesNoValue() {
        List<GreedyAssignment.Assignment> assignments = engine.assignPendingRequestsToStaff();
        boolean anyAssignedToBusyStaff = assignments.stream().anyMatch(a -> a.workerId().equals("2") && a.value() > 0);
        assertFalse(anyAssignedToBusyStaff);
    }

    @Test
    void selectsHighUrgencyRequestsWithinBudget() {
        List<BookRequest> selected = engine.selectRequestsWithinBudget();
        assertTrue(selected.stream().anyMatch(r -> r.getRequestId() == 1));
    }

    @Test
    void completedRequestsAreExcludedFromOptimisation() {
        List<BookRequest> selected = engine.selectRequestsWithinBudget();
        assertTrue(selected.stream().noneMatch(r -> r.getRequestId() == 3));
    }

    @Test
    void emptyDatabaseProducesNoAssignmentsOrSelections(@TempDir Path anotherTempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + anotherTempDir.resolve("empty.db"));
        DatabaseInitializer.initialize();
        OptimisationEngine emptyEngine = new OptimisationEngine(new BookRequestDAO(), new StaffDAO());

        assertTrue(emptyEngine.assignPendingRequestsToStaff().isEmpty());
        assertTrue(emptyEngine.selectRequestsWithinBudget().isEmpty());
    }
}
