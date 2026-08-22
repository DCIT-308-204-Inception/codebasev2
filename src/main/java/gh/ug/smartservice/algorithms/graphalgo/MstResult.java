package gh.ug.smartservice.algorithms.graphalgo;

import java.util.List;

public class MstResult {
    private final boolean spanning;
    private final List<Edge> edges;
    private final double totalWeight;

    public MstResult(boolean spanning, List<Edge> edges, double totalWeight) {
        this.spanning = spanning;
        this.edges = edges;
        this.totalWeight = totalWeight;
    }

    public boolean isSpanning() {
        return spanning;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public double getTotalWeight() {
        return totalWeight;
    }
}
