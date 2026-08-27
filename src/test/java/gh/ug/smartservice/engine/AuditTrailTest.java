package gh.ug.smartservice.engine;

import gh.ug.smartservice.db.AuditEventDAO;
import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.db.DatabaseConnection;
import gh.ug.smartservice.db.DatabaseInitializer;
import gh.ug.smartservice.model.AuditEvent;
import gh.ug.smartservice.model.BookRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.EmptyStackException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuditTrailTest {

    private final BookRequestDAO bookRequestDAO = new BookRequestDAO();
    private final AuditEventDAO auditEventDAO = new AuditEventDAO();
    private AuditTrail auditTrail;
    private BookRequest request;

    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        DatabaseConnection.useUrl("jdbc:sqlite:" + tempDir.resolve("test.db"));
        DatabaseInitializer.initialize();
        auditTrail = new AuditTrail(bookRequestDAO, auditEventDAO);

        LocalDateTime now = LocalDateTime.now();
        request = new BookRequest(1, 0, 1, "issue", 5, now, now.plusHours(1), "NEW");
        bookRequestDAO.insert(request);
    }

    @AfterEach
    void tearDown() {
        DatabaseConnection.close();
    }

    @Test
    void dispatchUpdatesStatusAndLogsEvent() {
        auditTrail.dispatch(request);

        assertEquals("IN_PROGRESS", bookRequestDAO.findAll().get(0).getStatus());
        List<AuditEvent> events = auditEventDAO.findAll();
        assertEquals(1, events.size());
        assertEquals("DISPATCH", events.get(0).getEventType());
    }

    @Test
    void undoRevertsStatusAndLogsEvent() {
        auditTrail.dispatch(request);
        auditTrail.undoLast();

        assertEquals("NEW", bookRequestDAO.findAll().get(0).getStatus());
        List<AuditEvent> events = auditEventDAO.findAll();
        assertEquals(2, events.size());
        assertEquals("UNDO", events.get(1).getEventType());
    }

    @Test
    void multipleDispatchesUndoInLifoOrder() {
        LocalDateTime now = LocalDateTime.now();
        BookRequest second = new BookRequest(2, 0, 2, "issue", 3, now, now.plusHours(1), "NEW");
        bookRequestDAO.insert(second);

        auditTrail.dispatch(request);
        auditTrail.dispatch(second);
        assertEquals(2, auditTrail.undoStackSize());

        auditTrail.undoLast();
        assertEquals("NEW", findById(2).getStatus());
        assertEquals("IN_PROGRESS", findById(1).getStatus());

        auditTrail.undoLast();
        assertEquals("NEW", findById(1).getStatus());
        assertFalse(auditTrail.hasUndoableAction());
    }

    @Test
    void undoWithNothingToUndoThrows() {
        assertThrows(EmptyStackException.class, () -> auditTrail.undoLast());
    }

    private BookRequest findById(int requestId) {
        return bookRequestDAO.findAll().stream()
            .filter(r -> r.getRequestId() == requestId)
            .findFirst()
            .orElseThrow();
    }
}
