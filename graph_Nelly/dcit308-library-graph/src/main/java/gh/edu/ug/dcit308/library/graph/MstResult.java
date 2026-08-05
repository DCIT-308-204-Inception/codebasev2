package gh.edu.ug.dcit308.library.graph;

import java.util.Arrays;

public final class MstResult {
    private final boolean spanning;
    private final Edge[] edges;
    private final long totalWeight;

    public MstResult(boolean spanning, Edge[] edges, long totalWeight) {
        this.spanning = spanning;
        this.edges = Arrays.copyOf(edges, edges.length);
        this.totalWeight = totalWeight;
    }

    public boolean isSpanning() {
        return spanning;
    }

    public Edge[] getEdges() {
        return Arrays.copyOf(edges, edges.length);
    }

    public long getTotalWeight() {
        return totalWeight;
    }

    @Override
    public String toString() {
        return "spanning=" + spanning
                + ", totalWeight=" + totalWeight
                + ", edges=" + Arrays.toString(edges);
    }
}
