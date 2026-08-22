package gh.ug.smartservice.algorithms.graphalgo;

import gh.ug.smartservice.datastructures.api.SimpleGraph;
import gh.ug.smartservice.datastructures.heap.BinaryHeapPriorityQueue;

import java.util.Arrays;

public final class Dijkstra {

    private Dijkstra() {
    }

    public static ShortestPathResult run(SimpleGraph graph, int source) {
        int n = graph.vertexCount();
        double[] distances = new double[n];
        int[] predecessors = new int[n];
        boolean[] settled = new boolean[n];
        Arrays.fill(distances, Double.POSITIVE_INFINITY);
        Arrays.fill(predecessors, -1);
        distances[source] = 0;

        BinaryHeapPriorityQueue<Integer> frontier = new BinaryHeapPriorityQueue<>();
        frontier.insert(source, 0);

        while (!frontier.isEmpty()) {
            int current = frontier.extractMin();
            if (settled[current]) {
                continue;
            }
            settled[current] = true;

            for (int neighbour : graph.neighbours(current)) {
                double candidate = distances[current] + graph.weight(current, neighbour);
                if (candidate < distances[neighbour]) {
                    distances[neighbour] = candidate;
                    predecessors[neighbour] = current;
                    frontier.insert(neighbour, (int) Math.round(candidate));
                }
            }
        }

        boolean reachable = false;
        for (double d : distances) {
            if (d != Double.POSITIVE_INFINITY) {
                reachable = true;
                break;
            }
        }
        return new ShortestPathResult(reachable, distances, predecessors);
    }
}
