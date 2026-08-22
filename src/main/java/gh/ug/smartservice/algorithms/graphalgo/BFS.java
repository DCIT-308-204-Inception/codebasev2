package gh.ug.smartservice.algorithms.graphalgo;

import gh.ug.smartservice.datastructures.api.SimpleGraph;
import gh.ug.smartservice.datastructures.queue.LinkedQueue;

import java.util.ArrayList;
import java.util.List;

public final class BFS {

    private BFS() {
    }

    public static TraversalResult run(SimpleGraph graph, int source) {
        int n = graph.vertexCount();
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();

        LinkedQueue<Integer> queue = new LinkedQueue<>();
        visited[source] = true;
        queue.enqueue(source);

        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            order.add(current);
            for (int neighbour : graph.neighbours(current)) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    queue.enqueue(neighbour);
                }
            }
        }
        return new TraversalResult(order);
    }
}
