package gh.ug.smartservice.model;

/**
 * Maps 1:1 to the `staff` table — library staff, shelvers, or book trolleys
 * that can be assigned to fulfil a BookRequest.
 */
public class Staff {
    private int staffId;
    private String role;               // "librarian", "shelver", "trolley", "intern"
    private int homeShelfId;           // base station / desk they start from
    private int capacity;              // max concurrent requests or books they can carry
    private String availabilityStatus; // "available", "busy", "off-duty"

    public Staff() {}

    public Staff(int staffId, String role, int homeShelfId, int capacity, String availabilityStatus) {
        this.staffId = staffId;
        this.role = role;
        this.homeShelfId = homeShelfId;
        this.capacity = capacity;
        this.availabilityStatus = availabilityStatus;
    }

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getHomeShelfId() { return homeShelfId; }
    public void setHomeShelfId(int homeShelfId) { this.homeShelfId = homeShelfId; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(String availabilityStatus) { this.availabilityStatus = availabilityStatus; }
}
