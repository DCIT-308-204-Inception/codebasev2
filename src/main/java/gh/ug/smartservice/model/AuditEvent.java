package gh.ug.smartservice.model;

import java.time.LocalDateTime;

/** Maps 1:1 to the `audit_events` table — one row per dispatch/undo action. */
public class AuditEvent {
    private int eventId;
    private String eventType;
    private String description;
    private LocalDateTime timestamp;

    public AuditEvent() {}

    public AuditEvent(int eventId, String eventType, String description, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.description = description;
        this.timestamp = timestamp;
    }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
