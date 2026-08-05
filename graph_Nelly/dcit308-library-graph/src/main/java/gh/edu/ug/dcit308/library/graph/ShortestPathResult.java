package gh.edu.ug.dcit308.library.graph;

import java.util.Arrays;

public final class ShortestPathResult {
    private final boolean reachable;
    private final long totalWeight;
    private final Location[] path;

    private ShortestPathResult(boolean reachable, long totalWeight, Location[] path) {
        this.reachable = reachable;
        this.totalWeight = totalWeight;
        this.path = Arrays.copyOf(path, path.length);
    }

    public static ShortestPathResult reachable(long totalWeight, Location[] path) {
        return new ShortestPathResult(true, totalWeight, path);
    }

    public static ShortestPathResult unreachable() {
        return new ShortestPathResult(false, Long.MAX_VALUE, new Location[0]);
    }

    public boolean isReachable() {
        return reachable;
    }

    public long getTotalWeight() {
        return totalWeight;
    }

    public Location[] getPath() {
        return Arrays.copyOf(path, path.length);
    }

    public String[] getPathIds() {
        String[] ids = new String[path.length];
        for (int i = 0; i < path.length; i++) {
            ids[i] = path[i].getLocationId();
        }
        return ids;
    }

    @Override
    public String toString() {
        if (!reachable) {
            return "No path";
        }
        return "weight=" + totalWeight + ", path=" + Arrays.toString(getPathIds());
    }
}
