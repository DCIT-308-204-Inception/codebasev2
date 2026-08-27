package gh.ug.smartservice.engine;

import gh.ug.smartservice.db.AuditEventDAO;
import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.model.AuditEvent;
import gh.ug.smartservice.model.BookRequest;
import gh.ug.smartservice.ui.MenuCommand;

import java.util.List;
import java.util.Optional;

public class AuditMenuCommand implements MenuCommand {

    @Override
    public String label() {
        return "Dispatch a request and undo it (audit trail demo)";
    }

    @Override
    public void execute() {
        BookRequestDAO bookRequestDAO = new BookRequestDAO();
        AuditEventDAO auditEventDAO = new AuditEventDAO();
        AuditTrail auditTrail = new AuditTrail(bookRequestDAO, auditEventDAO);

        Optional<BookRequest> next = bookRequestDAO.findAll().stream()
            .filter(r -> "NEW".equalsIgnoreCase(r.getStatus()))
            .findFirst();

        if (next.isEmpty()) {
            System.out.println("No NEW requests to dispatch.");
            return;
        }

        BookRequest request = next.get();
        System.out.println("Dispatching request " + request.getRequestId() + " (was " + request.getStatus() + ")");
        auditTrail.dispatch(request);

        System.out.println("Undoing last dispatch...");
        auditTrail.undoLast();

        List<AuditEvent> events = auditEventDAO.findAll();
        System.out.println("\nAudit log (" + events.size() + " events):");
        events.stream().skip(Math.max(0, events.size() - 5)).forEach(event ->
            System.out.println("  [" + event.getEventType() + "] " + event.getDescription()));
    }
}
