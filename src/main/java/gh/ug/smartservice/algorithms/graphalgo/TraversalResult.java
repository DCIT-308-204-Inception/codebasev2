package gh.ug.smartservice.algorithms.graphalgo;

import java.util.List;

public class TraversalResult {
    private final List<Integer> order;

    public TraversalResult(List<Integer> order) {
        this.order = order;
    }

    public List<Integer> getOrder() {
        return order;
    }
}
