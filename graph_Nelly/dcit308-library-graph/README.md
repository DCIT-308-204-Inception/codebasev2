# DCIT 308 Library Graph Module

## Purpose

This module models movement between locations in a Ghanaian library or records
office. A `Location` is a graph vertex. A path between two locations is an
undirected weighted edge. The edge weight can represent walking time, distance,
or another non-negative route cost.

The sample dataset uses fictional operational locations within a university
library context and contains no personal information.

## Implemented features

- Dynamically resized vertex storage
- Custom adjacency list
- Adjacency matrix
- Add, update, inspect, and remove undirected weighted edges
- Breadth-first search (BFS)
- Depth-first search (DFS)
- Dijkstra shortest path
- Prim minimum spanning tree
- Kruskal minimum spanning tree
- Custom circular queue used by BFS
- Custom stack used by DFS
- Custom binary min-heap used by Dijkstra
- Custom disjoint set with path compression and union by rank used by Kruskal
- Custom merge sort for Kruskal's edge ordering
- JUnit tests for normal, boundary, disconnected, duplicate, and invalid cases

## Design assumptions

1. The graph is undirected because a walkway between two library sections can
   normally be travelled in both directions.
2. All edge weights are non-negative. This is required by Dijkstra's algorithm.
3. Location IDs are unique.
4. Adding an existing edge updates its weight instead of creating a parallel
   edge.
5. Self-loops are rejected.
6. BFS and DFS return the vertices reachable from the selected start vertex.
7. Prim and Kruskal report `spanning=false` when the graph is disconnected.

## Project layout

```text
src/main/java/gh/edu/ug/dcit308/library/graph/
    Edge.java
    GraphDemo.java
    LibraryGraph.java
    Location.java
    MstResult.java
    ShortestPathResult.java
    TraversalResult.java

src/test/java/gh/edu/ug/dcit308/library/graph/
    LibraryGraphTest.java
```

## Running in IntelliJ IDEA

1. Open the folder containing `pom.xml`.
2. Allow IntelliJ to import the Maven project.
3. Confirm that the Project SDK is JDK 17 or newer.
4. Open `GraphDemo.java`.
5. Click the green Run arrow beside `main`.

## To run the tests:
1. Open `LibraryGraphTest.java`.
2. Click the green Run arrow beside the test class.
3. Confirm that every test passes.

Terminal alternatives:

```bash
mvn test
mvn package
```

## Main operation complexities

Let `V` be the number of vertices and `E` the number of undirected edges.

| Operation | Time | Additional space |
|---|---:|---:|
| Add location | O(V) because duplicate IDs are checked linearly | O(1) amortised, excluding resize |
| Add/update edge | O(deg(u) + deg(v)) | O(1) |
| Matrix edge lookup | O(1) | O(1) |
| BFS | O(V + E) | O(V) |
| DFS | O(V + E) | O(V) |
| Dijkstra with custom min-heap | O((V + E) log E) | O(V + E) |
| Prim in this implementation | O(V^2 + E) | O(V) |
| Kruskal | O(E log E) | O(V + E) |
| Adjacency list storage | O(V + E) | O(V + E) |
| Adjacency matrix storage | O(V^2) | O(V^2) |

The Dijkstra heap permits duplicate entries instead of implementing
decrease-key. Stale entries are ignored when extracted. This keeps the code
clear while preserving the stated asymptotic bound.

## Edge cases covered

- Empty graph
- One isolated vertex
- Duplicate location ID
- Blank location details
- Unknown vertex ID
- Negative weight
- Self-loop
- Updating an existing edge
- Removing a missing edge
- Disconnected graph
- Unreachable shortest path
- Source equal to destination

[//]: # (## Oral defence points)

[//]: # ()
[//]: # (Be prepared to explain:)

[//]: # ()
[//]: # (- Why an adjacency list is space-efficient for sparse graphs.)

[//]: # (- Why an adjacency matrix gives O&#40;1&#41; edge lookup but uses O&#40;V^2&#41; memory.)

[//]: # (- Why BFS uses a queue and DFS uses a stack.)

[//]: # (- Why Dijkstra cannot safely handle negative edge weights.)

[//]: # (- The difference between shortest path and minimum spanning tree.)

[//]: # (- How Prim grows one tree and how Kruskal joins components.)

[//]: # (- How path compression and union by rank improve disjoint-set operations.)

[//]: # (- Why a disconnected graph has a minimum spanning forest rather than one MST.)
