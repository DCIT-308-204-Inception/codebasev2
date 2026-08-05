# Week 1 Graph Submission Checklist

## IntelliJ and repository setup

- [ ] Clone/open the team's GitHub repository in IntelliJ.
- [ ] Confirm the project uses JDK 17 or adapt the `pom.xml` release value to
      the exact JDK selected by the group.
- [ ] Put the production classes under
      `src/main/java/gh/edu/ug/dcit308/library/graph/`.
- [ ] Put the test class under
      `src/test/java/gh/edu/ug/dcit308/library/graph/`.
- [ ] Keep the group's existing `pom.xml` if one already exists; merge only the
      JUnit dependency/configuration rather than replacing team settings.

## Required graph implementation

- [ ] `Location` vertex model implemented.
- [ ] `Edge` weighted edge model implemented.
- [ ] Graph dynamically expands when location capacity is exceeded.
- [ ] Adjacency list implemented without Java `HashMap`.
- [ ] Adjacency matrix implemented.
- [ ] Locations can be added and found by unique ID.
- [ ] Undirected weighted edges can be added.
- [ ] Existing edge weights can be updated.
- [ ] Edges can be removed from both representations.
- [ ] BFS implemented with a custom queue.
- [ ] DFS implemented with a custom stack.
- [ ] Dijkstra implemented with a custom min-heap.
- [ ] Prim implemented.
- [ ] Kruskal implemented with custom disjoint set.
- [ ] Kruskal edge ordering uses a from-scratch merge sort.

## Correctness evidence

- [ ] Run all JUnit tests.
- [ ] Confirm duplicate IDs are rejected.
- [ ] Confirm negative weights and self-loops are rejected.
- [ ] Confirm BFS and DFS work from a connected vertex.
- [ ] Confirm traversal of an isolated vertex returns only that vertex.
- [ ] Confirm Dijkstra returns the expected route and distance.
- [ ] Confirm Dijkstra reports an unreachable destination.
- [ ] Confirm Prim and Kruskal return the same MST total.
- [ ] Confirm disconnected graphs report `spanning=false`.
- [ ] Save a screenshot of the passing tests for the report/development log.
- [ ] Run `GraphDemo` and save its console output.

## Documentation and understanding

- [ ] README explains the library interpretation of vertices and edges.
- [ ] README states assumptions and edge cases.
- [ ] README contains time and space complexities.
- [ ] You can explain adjacency list versus adjacency matrix.
- [ ] You can manually trace BFS.
- [ ] You can manually trace DFS.
- [ ] You can trace Dijkstra's distance and predecessor updates.
- [ ] You can explain Prim versus Kruskal.
- [ ] You can explain why negative weights are rejected.
- [ ] You can explain why MST is unavailable for a disconnected graph.

## Git submission

- [ ] Run `mvn test` or the IntelliJ test runner one final time.
- [ ] Use IntelliJ's Reformat Code on your changed Java files.
- [ ] Inspect `git diff` before committing.
- [ ] Do not commit `.idea/`, `target/`, or machine-specific files.
- [ ] Commit with a clear message, for example:
      `Implement custom graph representations and algorithms`
- [ ] Push to your own branch unless the group explicitly requires direct
      pushes to the shared branch.
- [ ] Open a pull request or notify the group lead according to team procedure.
- [ ] Record your contribution in the development log.

## Not required for this week's graph code unless your group says otherwise

- [ ] Database/JDBC loading of locations and roads.
- [ ] Runtime experiments at 50, 100, 200, and 500 inputs.
- [ ] Performance line graphs.
- [ ] Final report trace-table formatting.
- [ ] Console menu integration.

These later tasks should reuse this module rather than rewrite it.
