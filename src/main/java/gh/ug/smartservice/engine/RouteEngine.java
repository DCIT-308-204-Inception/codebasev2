package gh.ug.smartservice.engine;

import gh.ug.smartservice.algorithms.graphalgo.BFS;
import gh.ug.smartservice.algorithms.graphalgo.DFS;
import gh.ug.smartservice.algorithms.graphalgo.Dijkstra;
import gh.ug.smartservice.algorithms.graphalgo.Kruskal;
import gh.ug.smartservice.algorithms.graphalgo.MstResult;
import gh.ug.smartservice.algorithms.graphalgo.Prim;
import gh.ug.smartservice.algorithms.graphalgo.ShortestPathResult;
import gh.ug.smartservice.datastructures.graph.AdjacencyListGraph;
import gh.ug.smartservice.datastructures.hash.CustomMap;
import gh.ug.smartservice.db.PathDAO;
import gh.ug.smartservice.db.ShelfDAO;
import gh.ug.smartservice.model.Path;
import gh.ug.smartservice.model.Shelf;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class RouteEngine {

    private final AdjacencyListGraph graph = new AdjacencyListGraph();

    // Shelf IDs in the seed data aren't densely packed (e.g. a "Returns Desk"
    // uses id 999), but the graph algorithms assume vertices are numbered
    // 0..vertexCount-1. This maps real shelf ids to/from that dense range.
    private final CustomMap<Integer, Integer> shelfIdToVertex = new CustomMap<>();
    private final int[] vertexToShelfId;

    public RouteEngine(ShelfDAO shelfDAO, PathDAO pathDAO) {
        List<Shelf> shelves = shelfDAO.findAll();
        vertexToShelfId = new int[shelves.size()];

        int nextVertex = 0;
        for (Shelf shelf : shelves) {
            shelfIdToVertex.put(shelf.getShelfId(), nextVertex);
            vertexToShelfId[nextVertex] = shelf.getShelfId();
            graph.addVertex(nextVertex);
            nextVertex++;
        }

        // Aisles/walkways are walkable in both directions, even though a
        // road is only stored once in the seed data.
        for (Path path : pathDAO.findAll()) {
            Integer from = shelfIdToVertex.get(path.getFromShelfId());
            Integer to = shelfIdToVertex.get(path.getToShelfId());
            if (from != null && to != null) {
                graph.addEdge(from, to, path.getDistance());
                graph.addEdge(to, from, path.getDistance());
            }
        }
    }

    /** Fastest route (by distance) from a shelf to every other shelf, indexed by internal vertex, not shelf id. */
    public ShortestPathResult fastestRoutesFrom(int shelfId) {
        return Dijkstra.run(graph, vertexOf(shelfId));
    }

    /** Shelves reachable from the given shelf, in breadth-first order. */
    public List<Integer> reachableShelves(int shelfId) {
        return toShelfIds(BFS.run(graph, vertexOf(shelfId)).getOrder());
    }

    /** Shelves reachable from the given shelf, in depth-first order. */
    public List<Integer> depthFirstTraversal(int shelfId) {
        return toShelfIds(DFS.run(graph, vertexOf(shelfId)).getOrder());
    }

    /** Minimum-cost network of aisles that keeps every shelf connected, grown from one shelf. */
    public MstResult minimumConnectionNetworkFrom(int shelfId) {
        return Prim.run(graph, vertexOf(shelfId));
    }

    /** Minimum-cost network of aisles that keeps every shelf connected, built edge-by-edge. */
    public MstResult minimumConnectionNetwork() {
        return Kruskal.run(graph);
    }

    private int vertexOf(int shelfId) {
        Integer vertex = shelfIdToVertex.get(shelfId);
        if (vertex == null) {
            throw new NoSuchElementException("Unknown shelf: " + shelfId);
        }
        return vertex;
    }

    private List<Integer> toShelfIds(List<Integer> vertices) {
        List<Integer> shelfIds = new ArrayList<>();
        for (int vertex : vertices) {
            shelfIds.add(vertexToShelfId[vertex]);
        }
        return shelfIds;
    }
}
