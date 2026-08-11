public class EmmanuelAsante_ServiceRequest {

    private int requestId;
    private String source;
    private String destination;
    private String category;
    private String urgency;
    private String timeSubmitted;
    private String deadline;
    private String status;

    public EmmanuelAsante_ServiceRequest(int requestId, String source, String destination,
                          String category, String urgency,
                          String timeSubmitted, String deadline,
                          String status) {

        this.requestId = requestId;
        this.source = source;
        this.destination = destination;
        this.category = category;
        this.urgency = urgency;
        this.timeSubmitted = timeSubmitted;
        this.deadline = deadline;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getCategory() {
        return category;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getTimeSubmitted() {
        return timeSubmitted;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Request ID: " + requestId +
                "\nSource: " + source +
                "\nDestination: " + destination +
                "\nCategory: " + category +
                "\nUrgency: " + urgency +
                "\nTime Submitted: " + timeSubmitted +
                "\nDeadline: " + deadline +
                "\nStatus: " + status;
    }
}
