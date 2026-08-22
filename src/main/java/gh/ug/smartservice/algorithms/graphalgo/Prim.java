package gh.ug.smartservice.algorithms.graphalgo;

import gh.ug.smartservice.datastructures.api.SimpleGraph;

import java.util.ArrayList;
import java.util.List;

public final class Prim {

    private Prim() {
    }

    public static MstResult run(SimpleGraph graph, int source) {
        int n = graph.vertexCount();
        boolean[] inTree = new boolean[n];
        double[] minEdge = new double[n];
        int[] connectingVertex = new int[n];
        java.util.Arrays.fill(minEdge, Double.POSITIVE_INFINITY);
        java.util.Arrays.fill(connectingVertex, -1);
        minEdge[source] = 0;

        List<Edge> edges = new ArrayList<>();
        double totalWeight = 0;
        int verticesInTree = 0;

        for (int iteration = 0; iteration < n; iteration++) {
            int current = -1;
            for (int v = 0; v < n; v++) {
                if (!inTree[v] && minEdge[v] != Double.POSITIVE_INFINITY
                        && (current == -1 || minEdge[v] < minEdge[current])) {
                    current = v;
                }
            }
            if (current == -1) {
                break;
            }
            inTree[current] = true;
            verticesInTree++;
            if (connectingVertex[current] != -1) {
                edges.add(new Edge(connectingVertex[current], current, minEdge[current]));
                totalWeight += minEdge[current];
            }
            for (int neighbour : graph.neighbours(current)) {
                double w = graph.weight(current, neighbour);
                if (!inTree[neighbour] && w < minEdge[neighbour]) {
                    minEdge[neighbour] = w;
                    connectingVertex[neighbour] = current;
                }
            }
        }

        boolean spanning = verticesInTree == n;
        return new MstResult(spanning, edges, totalWeight);
    }
}
