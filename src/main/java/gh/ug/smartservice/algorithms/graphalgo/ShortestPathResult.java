package gh.ug.smartservice.algorithms.graphalgo;

public class ShortestPathResult {
    private final boolean reachable;
    private final double[] distances;
    private final int[] predecessors;

    public ShortestPathResult(boolean reachable, double[] distances, int[] predecessors) {
        this.reachable = reachable;
        this.distances = distances;
        this.predecessors = predecessors;
    }

    public boolean isReachable() {
        return reachable;
    }

    public double[] getDistances() {
        return distances;
    }

    public int[] getPredecessors() {
        return predecessors;
    }
}
