package gh.edu.ug.dcit308.library.graph;

import java.util.Arrays;

public final class TraversalResult {
    private final String algorithm;
    private final Location[] order;

    public TraversalResult(String algorithm, Location[] order) {
        this.algorithm = algorithm;
        this.order = Arrays.copyOf(order, order.length);
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public Location[] getOrder() {
        return Arrays.copyOf(order, order.length);
    }

    public String[] getLocationIds() {
        String[] ids = new String[order.length];
        for (int i = 0; i < order.length; i++) {
            ids[i] = order[i].getLocationId();
        }
        return ids;
    }

    @Override
    public String toString() {
        return algorithm + " " + Arrays.toString(getLocationIds());
    }
}
