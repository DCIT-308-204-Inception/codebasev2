package gh.ug.smartservice.algorithms.graphalgo;

import gh.ug.smartservice.datastructures.graph.AdjacencyListGraph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphAlgorithmsTest {

    private AdjacencyListGraph sampleGraph() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        for (int i = 0; i < 5; i++) {
            graph.addVertex(i);
        }
        graph.addEdge(0, 1, 4);
        graph.addEdge(1, 0, 4);
        graph.addEdge(0, 2, 1);
        graph.addEdge(2, 0, 1);
        graph.addEdge(2, 1, 2);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 3, 5);
        graph.addEdge(3, 1, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(3, 2, 8);
        return graph;
    }

    @Test
    void bfsVisitsAllReachableVertices() {
        TraversalResult result = BFS.run(sampleGraph(), 0);
        assertEquals(4, result.getOrder().size());
        assertEquals(0, result.getOrder().get(0));
    }

    @Test
    void dfsVisitsAllReachableVertices() {
        TraversalResult result = DFS.run(sampleGraph(), 0);
        assertEquals(4, result.getOrder().size());
        assertEquals(0, result.getOrder().get(0));
    }

    @Test
    void bfsOnUnreachableVertexIsOnlyItself() {
        TraversalResult result = BFS.run(sampleGraph(), 4);
        assertEquals(1, result.getOrder().size());
    }

    @Test
    void dijkstraFindsShortestPath() {
        ShortestPathResult result = Dijkstra.run(sampleGraph(), 0);
        assertTrue(result.isReachable());
        assertEquals(0.0, result.getDistances()[0]);
        assertEquals(3.0, result.getDistances()[1]);
        assertEquals(1.0, result.getDistances()[2]);
        assertEquals(Double.POSITIVE_INFINITY, result.getDistances()[4]);
    }

    @Test
    void primAndKruskalProduceSameWeightSpanningTree() {
        MstResult prim = Prim.run(sampleGraph(), 0);
        MstResult kruskal = Kruskal.run(sampleGraph());
        assertFalse(prim.isSpanning());
        assertFalse(kruskal.isSpanning());
        assertEquals(3, prim.getEdges().size());
        assertEquals(prim.getTotalWeight(), kruskal.getTotalWeight());
    }
}
