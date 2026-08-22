package gh.ug.smartservice.model;

import java.time.LocalDateTime;

/**
 * Maps 1:1 to the `book_requests` table. One row per patron action —
 * issue, return, reservation, or search request routed through the library.
 */
public class BookRequest {
    private int requestId;
    private int sourceShelfId;       // where the request originates (e.g. circulation desk)
    private int destinationShelfId;  // shelf the book/task must be resolved at
    private String category;         // "issue", "return", "reservation", "reshelve", "search"
    private int urgency;             // priority weight (derive from index numbers per Section 2)
    private LocalDateTime timeSubmitted;
    private LocalDateTime deadline;
    private String status;           // "pending", "in-progress", "completed", "cancelled"

    public BookRequest() {}

    public BookRequest(int requestId, int sourceShelfId, int destinationShelfId, String category,
                        int urgency, LocalDateTime timeSubmitted, LocalDateTime deadline, String status) {
        this.requestId = requestId;
        this.sourceShelfId = sourceShelfId;
        this.destinationShelfId = destinationShelfId;
        this.category = category;
        this.urgency = urgency;
        this.timeSubmitted = timeSubmitted;
        this.deadline = deadline;
        this.status = status;
    }

    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }
    public int getSourceShelfId() { return sourceShelfId; }
    public void setSourceShelfId(int sourceShelfId) { this.sourceShelfId = sourceShelfId; }
    public int getDestinationShelfId() { return destinationShelfId; }
    public void setDestinationShelfId(int destinationShelfId) { this.destinationShelfId = destinationShelfId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getUrgency() { return urgency; }
    public void setUrgency(int urgency) { this.urgency = urgency; }
    public LocalDateTime getTimeSubmitted() { return timeSubmitted; }
    public void setTimeSubmitted(LocalDateTime timeSubmitted) { this.timeSubmitted = timeSubmitted; }
    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
