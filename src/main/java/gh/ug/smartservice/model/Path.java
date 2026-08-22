package gh.ug.smartservice.model;

/**
 * Maps 1:1 to the `paths` table — a weighted edge between two shelves/zones
 * (an aisle or walkway inside the library). Used to build the navigation
 * graph for BFS/DFS/Dijkstra/MST.
 */
public class Path {
    private int fromShelfId;
    private int toShelfId;
    private double distance;      // metres between shelves
    private double travelTime;    // estimated seconds to walk the aisle
    private double congestionWeight; // penalty for crowded/narrow aisles, foot traffic

    public Path() {}

    public Path(int fromShelfId, int toShelfId, double distance,
                double travelTime, double congestionWeight) {
        this.fromShelfId = fromShelfId;
        this.toShelfId = toShelfId;
        this.distance = distance;
        this.travelTime = travelTime;
        this.congestionWeight = congestionWeight;
    }

    public int getFromShelfId() { return fromShelfId; }
    public void setFromShelfId(int fromShelfId) { this.fromShelfId = fromShelfId; }
    public int getToShelfId() { return toShelfId; }
    public void setToShelfId(int toShelfId) { this.toShelfId = toShelfId; }
    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }
    public double getTravelTime() { return travelTime; }
    public void setTravelTime(double travelTime) { this.travelTime = travelTime; }
    public double getCongestionWeight() { return congestionWeight; }
    public void setCongestionWeight(double congestionWeight) { this.congestionWeight = congestionWeight; }
}
