package gh.edu.ug.dcit308.library.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LibraryGraphTest {
    private LibraryGraph graph;

    @BeforeEach
    void setUp() {
        graph = GraphDemo.sampleGraph();
    }

    @Test
    void addsLocationsAndEdges() {
        assertEquals(6, graph.getVertexCount());
        assertEquals(9, graph.getEdgeCount());
        assertTrue(graph.containsLocation("ENT"));
        assertTrue(graph.hasEdge("ENT", "ISS"));
        assertEquals(2, graph.getEdgeWeight("ENT", "ISS"));
    }

    @Test
    void resizesWhenInitialCapacityIsExceeded() {
        LibraryGraph small = new LibraryGraph(1);
        small.addLocation(location("A"));
        small.addLocation(location("B"));
        small.addLocation(location("C"));

        assertEquals(3, small.getVertexCount());
    }

    @Test
    void rejectsDuplicateLocationIds() {
        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addLocation(location("ENT")));
    }

    @Test
    void rejectsBlankLocationFields() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Location("", "Name", "Area", "Type"));
    }

    @Test
    void rejectsUnknownLocationInEdge() {
        assertThrows(
                NoSuchElementException.class,
                () -> graph.addUndirectedEdge("ENT", "MISSING", 4));
    }

    @Test
    void rejectsNegativeEdgeWeight() {
        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addUndirectedEdge("ENT", "ISS", -1));
    }

    @Test
    void rejectsSelfLoop() {
        assertThrows(
                IllegalArgumentException.class,
                () -> graph.addUndirectedEdge("ENT", "ENT", 1));
    }

    @Test
    void updatesExistingEdgeWithoutIncreasingEdgeCount() {
        graph.addUndirectedEdge("ENT", "ISS", 9);

        assertEquals(9, graph.getEdgeCount());
        assertEquals(9, graph.getEdgeWeight("ENT", "ISS"));
        assertEquals(9, graph.getEdgeWeight("ISS", "ENT"));
    }

    @Test
    void removesEdgeFromBothRepresentations() {
        assertTrue(graph.removeUndirectedEdge("ENT", "ISS"));
        assertFalse(graph.hasEdge("ENT", "ISS"));
        assertEquals(8, graph.getEdgeCount());
        assertFalse(graph.removeUndirectedEdge("ENT", "ISS"));
    }

    @Test
    void adjacencyMatrixIsSymmetric() {
        int[][] matrix = graph.getAdjacencyMatrixCopy();

        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix.length; column++) {
                assertEquals(matrix[row][column], matrix[column][row]);
            }
        }
    }

    @Test
    void bfsVisitsEveryReachableLocationOnce() {
        TraversalResult result = graph.breadthFirstSearch("ENT");

        assertArrayEquals(
                new String[]{"ENT", "REF", "ISS", "PER", "ICT", "ARC"},
                result.getLocationIds());
    }

    @Test
    void dfsVisitsEveryReachableLocationOnce() {
        TraversalResult result = graph.depthFirstSearch("ENT");

        assertArrayEquals(
                new String[]{"ENT", "REF", "PER", "ARC", "ICT", "ISS"},
                result.getLocationIds());
    }

    @Test
    void traversalStaysInsideConnectedComponent() {
        graph.addLocation(location("ISO"));

        assertArrayEquals(
                new String[]{"ISO"},
                graph.breadthFirstSearch("ISO").getLocationIds());
    }

    @Test
    void dijkstraFindsMinimumWeightPath() {
        ShortestPathResult result = graph.dijkstra("ENT", "ARC");

        assertTrue(result.isReachable());
        assertEquals(11, result.getTotalWeight());
        assertArrayEquals(
                new String[]{"ENT", "ISS", "ICT", "PER", "ARC"},
                result.getPathIds());
    }

    @Test
    void dijkstraReturnsZeroForSameSourceAndDestination() {
        ShortestPathResult result = graph.dijkstra("ENT", "ENT");

        assertTrue(result.isReachable());
        assertEquals(0, result.getTotalWeight());
        assertArrayEquals(new String[]{"ENT"}, result.getPathIds());
    }

    @Test
    void dijkstraReportsUnreachableDestination() {
        graph.addLocation(location("ISO"));

        ShortestPathResult result = graph.dijkstra("ENT", "ISO");

        assertFalse(result.isReachable());
        assertEquals(0, result.getPath().length);
    }

    @Test
    void primProducesSpanningTreeWithExpectedWeight() {
        MstResult result = graph.primMinimumSpanningTree("ENT");

        assertTrue(result.isSpanning());
        assertEquals(graph.getVertexCount() - 1, result.getEdges().length);
        assertEquals(11, result.getTotalWeight());
    }

    @Test
    void kruskalProducesSpanningTreeWithExpectedWeight() {
        MstResult result = graph.kruskalMinimumSpanningTree();

        assertTrue(result.isSpanning());
        assertEquals(graph.getVertexCount() - 1, result.getEdges().length);
        assertEquals(11, result.getTotalWeight());
    }

    @Test
    void mstAlgorithmsAgreeOnTotalWeight() {
        long primWeight = graph.primMinimumSpanningTree("ENT").getTotalWeight();
        long kruskalWeight = graph.kruskalMinimumSpanningTree().getTotalWeight();

        assertEquals(primWeight, kruskalWeight);
    }

    @Test
    void disconnectedGraphDoesNotClaimToHaveSpanningTree() {
        graph.addLocation(location("ISO"));

        assertFalse(graph.primMinimumSpanningTree("ENT").isSpanning());
        assertFalse(graph.kruskalMinimumSpanningTree().isSpanning());
    }

    @Test
    void emptyGraphRejectsTraversalStart() {
        LibraryGraph empty = new LibraryGraph();

        assertThrows(
                NoSuchElementException.class,
                () -> empty.breadthFirstSearch("NONE"));
    }

    private static Location location(String id) {
        return new Location(id, "Location " + id, "Balme Library", "Section");
    }
}
