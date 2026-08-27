package gh.ug.smartservice.experiments;

import gh.ug.smartservice.algorithms.graphalgo.Dijkstra;
import gh.ug.smartservice.datastructures.graph.AdjacencyListGraph;
import gh.ug.smartservice.model.AlgorithmRun;
import gh.ug.smartservice.util.Stopwatch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class GraphExperiment {

    private static final int EDGES_PER_VERTEX = 4;

    private GraphExperiment() {
    }

    public static List<AlgorithmRun> run(int[] inputSizes, int trialsPerSize) {
        List<AlgorithmRun> results = new ArrayList<>();
        Random random = new Random(9808);

        for (int size : inputSizes) {
            for (int trial = 0; trial < trialsPerSize; trial++) {
                AdjacencyListGraph graph = randomConnectedGraph(size, random);

                Stopwatch stopwatch = new Stopwatch();
                stopwatch.start();
                Dijkstra.run(graph, 0);
                long elapsedNs = stopwatch.stop();
                results.add(new AlgorithmRun(0, "Dijkstra", size, elapsedNs, 0, LocalDateTime.now()));
            }
        }
        return results;
    }

    private static AdjacencyListGraph randomConnectedGraph(int size, Random random) {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        for (int v = 0; v < size; v++) {
            graph.addVertex(v);
        }
        // Ring first, so every vertex is reachable from vertex 0.
        for (int v = 0; v < size; v++) {
            int next = (v + 1) % size;
            double weight = 1 + random.nextInt(20);
            graph.addEdge(v, next, weight);
            graph.addEdge(next, v, weight);
        }
        // Extra random edges for a denser graph.
        for (int v = 0; v < size; v++) {
            for (int e = 0; e < EDGES_PER_VERTEX; e++) {
                int to = random.nextInt(size);
                if (to != v) {
                    double weight = 1 + random.nextInt(20);
                    graph.addEdge(v, to, weight);
                }
            }
        }
        return graph;
    }
}
