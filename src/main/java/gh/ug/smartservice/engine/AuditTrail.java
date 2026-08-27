package gh.ug.smartservice.engine;

import gh.ug.smartservice.datastructures.stack.ArrayStack;
import gh.ug.smartservice.db.AuditEventDAO;
import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.model.AuditEvent;
import gh.ug.smartservice.model.BookRequest;

import java.time.LocalDateTime;

public class AuditTrail {

    private record UndoRecord(int requestId, String previousStatus) {
    }

    private final BookRequestDAO bookRequestDAO;
    private final AuditEventDAO auditEventDAO;
    private final ArrayStack<UndoRecord> undoStack = new ArrayStack<>();

    public AuditTrail(BookRequestDAO bookRequestDAO, AuditEventDAO auditEventDAO) {
        this.bookRequestDAO = bookRequestDAO;
        this.auditEventDAO = auditEventDAO;
    }

    public void dispatch(BookRequest request) {
        undoStack.push(new UndoRecord(request.getRequestId(), request.getStatus()));
        bookRequestDAO.updateStatus(request.getRequestId(), "IN_PROGRESS");
        log("DISPATCH", "request " + request.getRequestId() + " -> IN_PROGRESS (was " + request.getStatus() + ")");
    }

    /** Reverts the most recent dispatch. Throws java.util.EmptyStackException if there's nothing to undo. */
    public void undoLast() {
        UndoRecord record = undoStack.pop();
        bookRequestDAO.updateStatus(record.requestId(), record.previousStatus());
        log("UNDO", "request " + record.requestId() + " reverted to " + record.previousStatus());
    }

    public boolean hasUndoableAction() {
        return !undoStack.isEmpty();
    }

    public int undoStackSize() {
        return undoStack.size();
    }

    private void log(String type, String description) {
        auditEventDAO.insert(new AuditEvent(0, type, description, LocalDateTime.now()));
    }
}
