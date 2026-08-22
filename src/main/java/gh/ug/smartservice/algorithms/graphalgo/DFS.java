package gh.ug.smartservice.algorithms.graphalgo;

import gh.ug.smartservice.datastructures.api.SimpleGraph;
import gh.ug.smartservice.datastructures.stack.ArrayStack;

import java.util.ArrayList;
import java.util.List;

public final class DFS {

    private DFS() {
    }

    public static TraversalResult run(SimpleGraph graph, int source) {
        int n = graph.vertexCount();
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();

        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(source);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (visited[current]) {
                continue;
            }
            visited[current] = true;
            order.add(current);
            List<Integer> neighbours = graph.neighbours(current);
            for (int i = neighbours.size() - 1; i >= 0; i--) {
                int neighbour = neighbours.get(i);
                if (!visited[neighbour]) {
                    stack.push(neighbour);
                }
            }
        }
        return new TraversalResult(order);
    }
}
