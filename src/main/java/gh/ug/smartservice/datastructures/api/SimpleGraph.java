package gh.ug.smartservice.datastructures.api;

import java.util.List;

/** Implemented by both AdjacencyListGraph and AdjacencyMatrixGraph so graph
 *  algorithms (BFS/DFS/Dijkstra/Prim/Kruskal) can work against either. */
public interface SimpleGraph {
    void addVertex(int vertexId);
    void addEdge(int fromId, int toId, double weight);
    List<Integer> neighbours(int vertexId);
    double weight(int fromId, int toId);
    int vertexCount();
}
