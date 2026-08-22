package gh.ug.smartservice.model;

/**
 * Maps 1:1 to the `shelves` table. A node in the library's internal layout
 * graph — a shelf, section, desk, or zone that patrons/staff move between.
 */
public class Shelf {
    private int shelfId;
    private String name;         // e.g. "Fiction A3", "Reference Desk", "Returns Bin"
    private String section;      // e.g. "Ground Floor", "Reading Room 2"
    private String category;     // e.g. "Fiction", "Periodicals", "Reserve", "Circulation Desk"
    private double xCoordinate;  // internal layout position (in place of latitude)
    private double yCoordinate;  // internal layout position (in place of longitude)

    public Shelf() {}

    public Shelf(int shelfId, String name, String section, String category,
                 double xCoordinate, double yCoordinate) {
        this.shelfId = shelfId;
        this.name = name;
        this.section = section;
        this.category = category;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getShelfId() { return shelfId; }
    public void setShelfId(int shelfId) { this.shelfId = shelfId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getXCoordinate() { return xCoordinate; }
    public void setXCoordinate(double xCoordinate) { this.xCoordinate = xCoordinate; }
    public double getYCoordinate() { return yCoordinate; }
    public void setYCoordinate(double yCoordinate) { this.yCoordinate = yCoordinate; }

    @Override
    public String toString() {
        return "Shelf{" + shelfId + ", " + name + ", " + section + "}";
    }
}
