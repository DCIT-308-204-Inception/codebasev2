package gh.ug.smartservice.algorithms.graphalgo;

import gh.ug.smartservice.datastructures.api.SimpleGraph;
import gh.ug.smartservice.datastructures.disjointset.DisjointSet;

import java.util.ArrayList;
import java.util.List;

public final class Kruskal {

    private Kruskal() {
    }

    public static MstResult run(SimpleGraph graph) {
        int n = graph.vertexCount();
        List<Edge> candidates = new ArrayList<>();
        for (int v = 0; v < n; v++) {
            for (int neighbour : graph.neighbours(v)) {
                if (v < neighbour) {
                    candidates.add(new Edge(v, neighbour, graph.weight(v, neighbour)));
                }
            }
        }
        candidates.sort((a, b) -> Double.compare(a.getWeight(), b.getWeight()));

        DisjointSet sets = new DisjointSet(n);
        List<Edge> edges = new ArrayList<>();
        double totalWeight = 0;
        int edgesUsed = 0;

        for (Edge edge : candidates) {
            if (sets.union(edge.getFrom(), edge.getTo())) {
                edges.add(edge);
                totalWeight += edge.getWeight();
                edgesUsed++;
                if (edgesUsed == n - 1) {
                    break;
                }
            }
        }

        boolean spanning = n > 0 && edgesUsed == n - 1;
        return new MstResult(spanning, edges, totalWeight);
    }
}
