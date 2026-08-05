package gh.edu.ug.dcit308.library.graph;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Custom undirected weighted graph for a library-location network.
 *
 * The graph stores the same edges in:
 * 1. an adjacency list, used by traversal and shortest-path algorithms; and
 * 2. an adjacency matrix, used for constant-time edge lookup and demonstration.
 *
 * Assumption: edge weights represent non-negative travel time or distance.
 */
public final class LibraryGraph {
    private static final int DEFAULT_CAPACITY = 8;
    private static final int NO_EDGE = -1;
    private static final long INFINITY = Long.MAX_VALUE / 4;

    private Location[] locations;
    private EdgeNode[] adjacencyHeads;
    private int[][] adjacencyMatrix;
    private int vertexCount;
    private int edgeCount;

    public LibraryGraph() {
        this(DEFAULT_CAPACITY);
    }

    public LibraryGraph(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("initialCapacity must be at least 1");
        }

        locations = new Location[initialCapacity];
        adjacencyHeads = new EdgeNode[initialCapacity];
        adjacencyMatrix = new int[initialCapacity][initialCapacity];
        fillMatrixWithNoEdge(adjacencyMatrix);
    }

    public int addLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("location must not be null");
        }
        if (indexOf(location.getLocationId()) >= 0) {
            throw new IllegalArgumentException(
                    "Duplicate location ID: " + location.getLocationId());
        }

        ensureCapacity(vertexCount + 1);
        locations[vertexCount] = location;
        adjacencyMatrix[vertexCount][vertexCount] = 0;
        vertexCount++;
        return vertexCount - 1;
    }

    public void addUndirectedEdge(String firstLocationId,
                                  String secondLocationId,
                                  int weight) {
        int first = requireLocationIndex(firstLocationId);
        int second = requireLocationIndex(secondLocationId);

        if (first == second) {
            throw new IllegalArgumentException("Self-loops are not supported");
        }
        if (weight < 0) {
            throw new IllegalArgumentException(
                    "Dijkstra and MST algorithms require non-negative weights");
        }

        boolean newEdge = adjacencyMatrix[first][second] == NO_EDGE;
        adjacencyHeads[first] = putNeighbour(adjacencyHeads[first], second, weight);
        adjacencyHeads[second] = putNeighbour(adjacencyHeads[second], first, weight);
        adjacencyMatrix[first][second] = weight;
        adjacencyMatrix[second][first] = weight;

        if (newEdge) {
            edgeCount++;
        }
    }

    public boolean removeUndirectedEdge(String firstLocationId,
                                        String secondLocationId) {
        int first = requireLocationIndex(firstLocationId);
        int second = requireLocationIndex(secondLocationId);

        if (adjacencyMatrix[first][second] == NO_EDGE) {
            return false;
        }

        adjacencyHeads[first] = removeNeighbour(adjacencyHeads[first], second);
        adjacencyHeads[second] = removeNeighbour(adjacencyHeads[second], first);
        adjacencyMatrix[first][second] = NO_EDGE;
        adjacencyMatrix[second][first] = NO_EDGE;
        edgeCount--;
        return true;
    }

    public boolean containsLocation(String locationId) {
        return indexOf(locationId) >= 0;
    }

    public boolean hasEdge(String firstLocationId, String secondLocationId) {
        int first = requireLocationIndex(firstLocationId);
        int second = requireLocationIndex(secondLocationId);
        return first != second && adjacencyMatrix[first][second] != NO_EDGE;
    }

    public int getEdgeWeight(String firstLocationId, String secondLocationId) {
        int first = requireLocationIndex(firstLocationId);
        int second = requireLocationIndex(secondLocationId);
        int weight = adjacencyMatrix[first][second];

        if (first == second) {
            return 0;
        }
        if (weight == NO_EDGE) {
            throw new NoSuchElementException(
                    "No edge joins " + firstLocationId + " and " + secondLocationId);
        }
        return weight;
    }

    public Location getLocation(String locationId) {
        return locations[requireLocationIndex(locationId)];
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public int[][] getAdjacencyMatrixCopy() {
        int[][] copy = new int[vertexCount][vertexCount];
        for (int row = 0; row < vertexCount; row++) {
            System.arraycopy(adjacencyMatrix[row], 0, copy[row], 0, vertexCount);
        }
        return copy;
    }

    public String adjacencyListText() {
        StringBuilder output = new StringBuilder();

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            output.append(locations[vertex].getLocationId()).append(": ");
            EdgeNode current = adjacencyHeads[vertex];

            while (current != null) {
                output.append(locations[current.to].getLocationId())
                        .append("(").append(current.weight).append(")");
                current = current.next;
                if (current != null) {
                    output.append(" -> ");
                }
            }
            if (vertex < vertexCount - 1) {
                output.append(System.lineSeparator());
            }
        }
        return output.toString();
    }

    public String adjacencyMatrixText() {
        StringBuilder output = new StringBuilder("      ");
        for (int column = 0; column < vertexCount; column++) {
            output.append(String.format("%6s", locations[column].getLocationId()));
        }
        output.append(System.lineSeparator());

        for (int row = 0; row < vertexCount; row++) {
            output.append(String.format("%6s", locations[row].getLocationId()));
            for (int column = 0; column < vertexCount; column++) {
                int value = adjacencyMatrix[row][column];
                String cell = value == NO_EDGE ? "-" : Integer.toString(value);
                output.append(String.format("%6s", cell));
            }
            if (row < vertexCount - 1) {
                output.append(System.lineSeparator());
            }
        }
        return output.toString();
    }

    public TraversalResult breadthFirstSearch(String startLocationId) {
        int start = requireLocationIndex(startLocationId);
        boolean[] visited = new boolean[vertexCount];
        int[] order = new int[vertexCount];
        int visitedCount = 0;
        IntQueue queue = new IntQueue(Math.max(1, vertexCount));

        visited[start] = true;
        queue.enqueue(start);

        while (!queue.isEmpty()) {
            int current = queue.dequeue();
            order[visitedCount++] = current;

            EdgeNode neighbour = adjacencyHeads[current];
            while (neighbour != null) {
                if (!visited[neighbour.to]) {
                    visited[neighbour.to] = true;
                    queue.enqueue(neighbour.to);
                }
                neighbour = neighbour.next;
            }
        }

        return new TraversalResult("BFS", locationsFromIndices(order, visitedCount));
    }

    public TraversalResult depthFirstSearch(String startLocationId) {
        int start = requireLocationIndex(startLocationId);
        boolean[] visited = new boolean[vertexCount];
        int[] order = new int[vertexCount];
        int visitedCount = 0;
        IntStack stack = new IntStack(Math.max(1, vertexCount));

        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (visited[current]) {
                continue;
            }

            visited[current] = true;
            order[visitedCount++] = current;

            /*
             * Neighbours are copied, then pushed in reverse adjacency-list order.
             * This makes the first neighbour in the list get processed first.
             */
            int degree = degreeOf(current);
            int[] neighbours = new int[degree];
            int position = 0;
            EdgeNode neighbour = adjacencyHeads[current];
            while (neighbour != null) {
                neighbours[position++] = neighbour.to;
                neighbour = neighbour.next;
            }

            for (int i = neighbours.length - 1; i >= 0; i--) {
                if (!visited[neighbours[i]]) {
                    stack.push(neighbours[i]);
                }
            }
        }

        return new TraversalResult("DFS", locationsFromIndices(order, visitedCount));
    }

    public ShortestPathResult dijkstra(String sourceLocationId,
                                       String destinationLocationId) {
        int source = requireLocationIndex(sourceLocationId);
        int destination = requireLocationIndex(destinationLocationId);

        long[] distance = new long[vertexCount];
        int[] predecessor = new int[vertexCount];
        boolean[] settled = new boolean[vertexCount];
        Arrays.fill(distance, INFINITY);
        Arrays.fill(predecessor, -1);

        DistanceHeap frontier = new DistanceHeap(Math.max(4, edgeCount + 1));
        distance[source] = 0;
        frontier.insert(source, 0);

        while (!frontier.isEmpty()) {
            HeapEntry next = frontier.extractMin();
            int current = next.vertex;

            if (settled[current] || next.priority != distance[current]) {
                continue;
            }

            settled[current] = true;
            if (current == destination) {
                break;
            }

            EdgeNode edge = adjacencyHeads[current];
            while (edge != null) {
                if (!settled[edge.to]) {
                    long candidate = distance[current] + edge.weight;
                    if (candidate < distance[edge.to]) {
                        distance[edge.to] = candidate;
                        predecessor[edge.to] = current;
                        frontier.insert(edge.to, candidate);
                    }
                }
                edge = edge.next;
            }
        }

        if (distance[destination] == INFINITY) {
            return ShortestPathResult.unreachable();
        }

        Location[] path = rebuildPath(source, destination, predecessor);
        return ShortestPathResult.reachable(distance[destination], path);
    }

    public MstResult primMinimumSpanningTree(String startLocationId) {
        int start = requireLocationIndex(startLocationId);
        long[] key = new long[vertexCount];
        int[] parent = new int[vertexCount];
        boolean[] included = new boolean[vertexCount];
        Arrays.fill(key, INFINITY);
        Arrays.fill(parent, -1);
        key[start] = 0;

        Edge[] treeEdges = new Edge[Math.max(0, vertexCount - 1)];
        int treeEdgeCount = 0;
        long totalWeight = 0;

        for (int step = 0; step < vertexCount; step++) {
            int current = minimumUnincludedVertex(key, included);
            if (current == -1) {
                break;
            }

            included[current] = true;
            if (parent[current] != -1) {
                treeEdges[treeEdgeCount++] =
                        new Edge(parent[current], current, (int) key[current]);
                totalWeight += key[current];
            }

            EdgeNode edge = adjacencyHeads[current];
            while (edge != null) {
                if (!included[edge.to] && edge.weight < key[edge.to]) {
                    key[edge.to] = edge.weight;
                    parent[edge.to] = current;
                }
                edge = edge.next;
            }
        }

        boolean spanning = vertexCount == 0 || treeEdgeCount == vertexCount - 1;
        return new MstResult(
                spanning,
                Arrays.copyOf(treeEdges, treeEdgeCount),
                totalWeight);
    }

    public MstResult kruskalMinimumSpanningTree() {
        Edge[] edges = uniqueEdges();
        mergeSortEdges(edges);

        DisjointSet components = new DisjointSet(vertexCount);
        Edge[] treeEdges = new Edge[Math.max(0, vertexCount - 1)];
        int treeEdgeCount = 0;
        long totalWeight = 0;

        for (Edge edge : edges) {
            int firstRoot = components.find(edge.getFromIndex());
            int secondRoot = components.find(edge.getToIndex());

            if (firstRoot != secondRoot) {
                components.union(firstRoot, secondRoot);
                treeEdges[treeEdgeCount++] = edge;
                totalWeight += edge.getWeight();

                if (treeEdgeCount == vertexCount - 1) {
                    break;
                }
            }
        }

        boolean spanning = vertexCount == 0 || treeEdgeCount == vertexCount - 1;
        return new MstResult(
                spanning,
                Arrays.copyOf(treeEdges, treeEdgeCount),
                totalWeight);
    }

    public String describeEdge(Edge edge) {
        if (edge == null) {
            throw new IllegalArgumentException("edge must not be null");
        }
        if (edge.getFromIndex() >= vertexCount || edge.getToIndex() >= vertexCount) {
            throw new IllegalArgumentException("edge does not belong to this graph");
        }

        return locations[edge.getFromIndex()].getLocationId()
                + " --(" + edge.getWeight() + ")-- "
                + locations[edge.getToIndex()].getLocationId();
    }

    private EdgeNode putNeighbour(EdgeNode head, int to, int weight) {
        EdgeNode current = head;
        while (current != null) {
            if (current.to == to) {
                current.weight = weight;
                return head;
            }
            current = current.next;
        }
        return new EdgeNode(to, weight, head);
    }

    private EdgeNode removeNeighbour(EdgeNode head, int to) {
        if (head == null) {
            return null;
        }
        if (head.to == to) {
            return head.next;
        }

        EdgeNode current = head;
        while (current.next != null) {
            if (current.next.to == to) {
                current.next = current.next.next;
                return head;
            }
            current = current.next;
        }
        return head;
    }

    private int indexOf(String locationId) {
        if (locationId == null) {
            return -1;
        }

        String cleanedId = locationId.trim();
        for (int i = 0; i < vertexCount; i++) {
            if (locations[i].getLocationId().equals(cleanedId)) {
                return i;
            }
        }
        return -1;
    }

    private int requireLocationIndex(String locationId) {
        int index = indexOf(locationId);
        if (index < 0) {
            throw new NoSuchElementException("Unknown location ID: " + locationId);
        }
        return index;
    }

    private void ensureCapacity(int requiredCapacity) {
        if (requiredCapacity <= locations.length) {
            return;
        }

        int newCapacity = Math.max(requiredCapacity, locations.length * 2);
        locations = Arrays.copyOf(locations, newCapacity);
        adjacencyHeads = Arrays.copyOf(adjacencyHeads, newCapacity);

        int[][] largerMatrix = new int[newCapacity][newCapacity];
        fillMatrixWithNoEdge(largerMatrix);

        for (int row = 0; row < vertexCount; row++) {
            System.arraycopy(
                    adjacencyMatrix[row], 0,
                    largerMatrix[row], 0,
                    vertexCount);
        }
        for (int i = 0; i < vertexCount; i++) {
            largerMatrix[i][i] = 0;
        }

        adjacencyMatrix = largerMatrix;
    }

    private static void fillMatrixWithNoEdge(int[][] matrix) {
        for (int[] row : matrix) {
            Arrays.fill(row, NO_EDGE);
        }
    }

    private int degreeOf(int vertex) {
        int degree = 0;
        EdgeNode current = adjacencyHeads[vertex];
        while (current != null) {
            degree++;
            current = current.next;
        }
        return degree;
    }

    private Location[] locationsFromIndices(int[] indices, int count) {
        Location[] result = new Location[count];
        for (int i = 0; i < count; i++) {
            result[i] = locations[indices[i]];
        }
        return result;
    }

    private Location[] rebuildPath(int source,
                                   int destination,
                                   int[] predecessor) {
        int length = 1;
        int cursor = destination;

        while (cursor != source) {
            cursor = predecessor[cursor];
            if (cursor == -1) {
                return new Location[0];
            }
            length++;
        }

        Location[] path = new Location[length];
        cursor = destination;

        for (int position = length - 1; position >= 0; position--) {
            path[position] = locations[cursor];
            cursor = predecessor[cursor];
        }
        return path;
    }

    private int minimumUnincludedVertex(long[] key, boolean[] included) {
        long bestKey = INFINITY;
        int bestVertex = -1;

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            if (!included[vertex] && key[vertex] < bestKey) {
                bestKey = key[vertex];
                bestVertex = vertex;
            }
        }
        return bestVertex;
    }

    private Edge[] uniqueEdges() {
        Edge[] edges = new Edge[edgeCount];
        int position = 0;

        for (int from = 0; from < vertexCount; from++) {
            EdgeNode current = adjacencyHeads[from];
            while (current != null) {
                if (from < current.to) {
                    edges[position++] = new Edge(from, current.to, current.weight);
                }
                current = current.next;
            }
        }
        return edges;
    }

    private static void mergeSortEdges(Edge[] edges) {
        if (edges.length < 2) {
            return;
        }
        Edge[] workspace = new Edge[edges.length];
        mergeSortEdges(edges, workspace, 0, edges.length - 1);
    }

    private static void mergeSortEdges(Edge[] edges,
                                       Edge[] workspace,
                                       int low,
                                       int high) {
        if (low >= high) {
            return;
        }

        int middle = low + (high - low) / 2;
        mergeSortEdges(edges, workspace, low, middle);
        mergeSortEdges(edges, workspace, middle + 1, high);
        mergeEdges(edges, workspace, low, middle, high);
    }

    private static void mergeEdges(Edge[] edges,
                                   Edge[] workspace,
                                   int low,
                                   int middle,
                                   int high) {
        int left = low;
        int right = middle + 1;
        int output = low;

        while (left <= middle && right <= high) {
            if (edges[left].getWeight() <= edges[right].getWeight()) {
                workspace[output++] = edges[left++];
            } else {
                workspace[output++] = edges[right++];
            }
        }

        while (left <= middle) {
            workspace[output++] = edges[left++];
        }
        while (right <= high) {
            workspace[output++] = edges[right++];
        }

        for (int i = low; i <= high; i++) {
            edges[i] = workspace[i];
        }
    }

    private static final class EdgeNode {
        private final int to;
        private int weight;
        private EdgeNode next;

        private EdgeNode(int to, int weight, EdgeNode next) {
            this.to = to;
            this.weight = weight;
            this.next = next;
        }
    }

    private static final class IntQueue {
        private int[] values;
        private int front;
        private int size;

        private IntQueue(int capacity) {
            values = new int[Math.max(1, capacity)];
        }

        private void enqueue(int value) {
            if (size == values.length) {
                grow();
            }
            int rear = (front + size) % values.length;
            values[rear] = value;
            size++;
        }

        private int dequeue() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue is empty");
            }
            int value = values[front];
            front = (front + 1) % values.length;
            size--;
            return value;
        }

        private boolean isEmpty() {
            return size == 0;
        }

        private void grow() {
            int[] larger = new int[values.length * 2];
            for (int i = 0; i < size; i++) {
                larger[i] = values[(front + i) % values.length];
            }
            values = larger;
            front = 0;
        }
    }

    private static final class IntStack {
        private int[] values;
        private int size;

        private IntStack(int capacity) {
            values = new int[Math.max(1, capacity)];
        }

        private void push(int value) {
            if (size == values.length) {
                values = Arrays.copyOf(values, values.length * 2);
            }
            values[size++] = value;
        }

        private int pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Stack is empty");
            }
            return values[--size];
        }

        private boolean isEmpty() {
            return size == 0;
        }
    }

    private static final class HeapEntry {
        private final int vertex;
        private final long priority;

        private HeapEntry(int vertex, long priority) {
            this.vertex = vertex;
            this.priority = priority;
        }
    }

    private static final class DistanceHeap {
        private HeapEntry[] entries;
        private int size;

        private DistanceHeap(int capacity) {
            entries = new HeapEntry[Math.max(1, capacity)];
        }

        private void insert(int vertex, long priority) {
            if (size == entries.length) {
                entries = Arrays.copyOf(entries, entries.length * 2);
            }

            int child = size++;
            entries[child] = new HeapEntry(vertex, priority);

            while (child > 0) {
                int parent = (child - 1) / 2;
                if (entries[parent].priority <= entries[child].priority) {
                    break;
                }
                swap(parent, child);
                child = parent;
            }
        }

        private HeapEntry extractMin() {
            if (isEmpty()) {
                throw new NoSuchElementException("Heap is empty");
            }

            HeapEntry minimum = entries[0];
            entries[0] = entries[--size];
            entries[size] = null;

            int parent = 0;
            while (true) {
                int left = parent * 2 + 1;
                int right = left + 1;
                int smallest = parent;

                if (left < size
                        && entries[left].priority < entries[smallest].priority) {
                    smallest = left;
                }
                if (right < size
                        && entries[right].priority < entries[smallest].priority) {
                    smallest = right;
                }
                if (smallest == parent) {
                    break;
                }

                swap(parent, smallest);
                parent = smallest;
            }

            return minimum;
        }

        private boolean isEmpty() {
            return size == 0;
        }

        private void swap(int first, int second) {
            HeapEntry temporary = entries[first];
            entries[first] = entries[second];
            entries[second] = temporary;
        }
    }

    private static final class DisjointSet {
        private final int[] parent;
        private final int[] rank;

        private DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];

            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        private int find(int item) {
            if (parent[item] != item) {
                parent[item] = find(parent[item]);
            }
            return parent[item];
        }

        private void union(int first, int second) {
            int firstRoot = find(first);
            int secondRoot = find(second);

            if (firstRoot == secondRoot) {
                return;
            }

            if (rank[firstRoot] < rank[secondRoot]) {
                parent[firstRoot] = secondRoot;
            } else if (rank[firstRoot] > rank[secondRoot]) {
                parent[secondRoot] = firstRoot;
            } else {
                parent[secondRoot] = firstRoot;
                rank[firstRoot]++;
            }
        }
    }
}
