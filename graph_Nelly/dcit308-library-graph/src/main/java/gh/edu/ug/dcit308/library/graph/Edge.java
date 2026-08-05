package gh.edu.ug.dcit308.library.graph;

/**
 * An undirected weighted connection between two library locations.
 */
public final class Edge {
    private final int fromIndex;
    private final int toIndex;
    private final int weight;

    public Edge(int fromIndex, int toIndex, int weight) {
        if (fromIndex < 0 || toIndex < 0) {
            throw new IllegalArgumentException("Vertex indices must be non-negative");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight must be non-negative");
        }
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.weight = weight;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return fromIndex + " --(" + weight + ")-- " + toIndex;
    }
}
