package gh.ug.smartservice.datastructures.graph;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListGraphTest {

    @Test
    void addEdgeAndNeighbours() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(0, 1, 2.5);
        graph.addEdge(0, 2, 1.0);
        assertEquals(2, graph.neighbours(0).size());
        assertEquals(2.5, graph.weight(0, 1));
    }

    @Test
    void updatingExistingEdgeOverwritesWeight() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1, 5.0);
        graph.addEdge(0, 1, 3.0);
        assertEquals(3.0, graph.weight(0, 1));
        assertEquals(1, graph.neighbours(0).size());
    }

    @Test
    void isolatedVertexHasNoNeighbours() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        assertTrue(graph.neighbours(0).isEmpty());
        assertEquals(1, graph.vertexCount());
    }

    @Test
    void unknownVertexThrows() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        assertThrows(NoSuchElementException.class, () -> graph.neighbours(5));
        assertThrows(NoSuchElementException.class, () -> graph.weight(0, 5));
    }
}
