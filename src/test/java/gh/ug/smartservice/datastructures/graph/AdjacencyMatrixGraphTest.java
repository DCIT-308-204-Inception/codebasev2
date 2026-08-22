package gh.ug.smartservice.datastructures.graph;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyMatrixGraphTest {

    @Test
    void addEdgeAndNeighbours() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(5);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(0, 1, 4.0);
        graph.addEdge(0, 2, 1.5);
        assertEquals(2, graph.neighbours(0).size());
        assertEquals(4.0, graph.weight(0, 1));
    }

    @Test
    void isolatedVertexHasNoNeighbours() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(3);
        graph.addVertex(0);
        assertTrue(graph.neighbours(0).isEmpty());
    }

    @Test
    void capacityBoundary() {
        assertThrows(IllegalArgumentException.class, () -> new AdjacencyMatrixGraph(0));
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(2);
        assertThrows(IndexOutOfBoundsException.class, () -> graph.addVertex(5));
    }

    @Test
    void missingEdgeThrows() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(2);
        graph.addVertex(0);
        graph.addVertex(1);
        assertThrows(NoSuchElementException.class, () -> graph.weight(0, 1));
    }
}
