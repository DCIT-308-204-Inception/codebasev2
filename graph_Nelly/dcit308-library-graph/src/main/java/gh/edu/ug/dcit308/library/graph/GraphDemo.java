package gh.edu.ug.dcit308.library.graph;

import java.util.Arrays;

public final class GraphDemo {
    private GraphDemo() {
    }

    public static void main(String[] args) {
        LibraryGraph graph = sampleGraph();

        System.out.println("LIBRARY GRAPH DEMONSTRATION");
        System.out.println("===========================");
        System.out.println("Vertices: " + graph.getVertexCount());
        System.out.println("Edges: " + graph.getEdgeCount());

        System.out.println("\nAdjacency list");
        System.out.println(graph.adjacencyListText());

        System.out.println("\nAdjacency matrix");
        System.out.println(graph.adjacencyMatrixText());

        TraversalResult bfs = graph.breadthFirstSearch("ENT");
        TraversalResult dfs = graph.depthFirstSearch("ENT");
        System.out.println("\nBFS from ENT: " + Arrays.toString(bfs.getLocationIds()));
        System.out.println("DFS from ENT: " + Arrays.toString(dfs.getLocationIds()));

        ShortestPathResult shortest = graph.dijkstra("ENT", "ARC");
        System.out.println("\nShortest ENT -> ARC: " + shortest);

        MstResult prim = graph.primMinimumSpanningTree("ENT");
        System.out.println("\nPrim MST total: " + prim.getTotalWeight());
        printEdges(graph, prim.getEdges());

        MstResult kruskal = graph.kruskalMinimumSpanningTree();
        System.out.println("\nKruskal MST total: " + kruskal.getTotalWeight());
        printEdges(graph, kruskal.getEdges());
    }

    public static LibraryGraph sampleGraph() {
        LibraryGraph graph = new LibraryGraph(4);

        graph.addLocation(new Location("ENT", "Main Entrance", "Ground Floor", "Entrance"));
        graph.addLocation(new Location("ISS", "Issue Desk", "Ground Floor", "Service Desk"));
        graph.addLocation(new Location("REF", "Reference Section", "First Floor", "Book Section"));
        graph.addLocation(new Location("ICT", "ICT Library", "First Floor", "Computer Area"));
        graph.addLocation(new Location("PER", "Periodicals Section", "First Floor", "Book Section"));
        graph.addLocation(new Location("ARC", "Archive Room", "Second Floor", "Restricted Store"));

        graph.addUndirectedEdge("ENT", "ISS", 2);
        graph.addUndirectedEdge("ENT", "REF", 6);
        graph.addUndirectedEdge("ISS", "REF", 3);
        graph.addUndirectedEdge("ISS", "ICT", 5);
        graph.addUndirectedEdge("REF", "ICT", 2);
        graph.addUndirectedEdge("REF", "PER", 4);
        graph.addUndirectedEdge("ICT", "PER", 1);
        graph.addUndirectedEdge("ICT", "ARC", 7);
        graph.addUndirectedEdge("PER", "ARC", 3);

        return graph;
    }

    private static void printEdges(LibraryGraph graph, Edge[] edges) {
        for (Edge edge : edges) {
            System.out.println("  " + graph.describeEdge(edge));
        }
    }
}
