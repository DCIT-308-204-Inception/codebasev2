package gh.ug.smartservice.datastructures.graph;

import gh.ug.smartservice.datastructures.api.SimpleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AdjacencyMatrixGraph implements SimpleGraph {

    private static final double NO_EDGE = Double.NaN;

    private final double[][] matrix;
    private final boolean[] present;
    private int vertexCount;

    public AdjacencyMatrixGraph(int maxVertices) {
        if (maxVertices < 1) {
            throw new IllegalArgumentException("maxVertices must be at least 1");
        }
        matrix = new double[maxVertices][maxVertices];
        present = new boolean[maxVertices];
        for (double[] row : matrix) {
            java.util.Arrays.fill(row, NO_EDGE);
        }
    }

    @Override
    public void addVertex(int vertexId) {
        requireCapacity(vertexId);
        present[vertexId] = true;
        if (vertexId + 1 > vertexCount) {
            vertexCount = vertexId + 1;
        }
    }

    @Override
    public void addEdge(int fromId, int toId, double weight) {
        requireVertex(fromId);
        requireVertex(toId);
        matrix[fromId][toId] = weight;
    }

    @Override
    public List<Integer> neighbours(int vertexId) {
        requireVertex(vertexId);
        List<Integer> result = new ArrayList<>();
        for (int to = 0; to < present.length; to++) {
            if (present[to] && !Double.isNaN(matrix[vertexId][to])) {
                result.add(to);
            }
        }
        return result;
    }

    @Override
    public double weight(int fromId, int toId) {
        requireVertex(fromId);
        requireVertex(toId);
        double value = matrix[fromId][toId];
        if (Double.isNaN(value)) {
            throw new NoSuchElementException("No edge from " + fromId + " to " + toId);
        }
        return value;
    }

    @Override
    public int vertexCount() {
        return vertexCount;
    }

    private void requireCapacity(int vertexId) {
        if (vertexId < 0 || vertexId >= present.length) {
            throw new IndexOutOfBoundsException("vertexId " + vertexId + " exceeds maxVertices");
        }
    }

    private void requireVertex(int vertexId) {
        if (vertexId < 0 || vertexId >= present.length || !present[vertexId]) {
            throw new NoSuchElementException("Unknown vertex: " + vertexId);
        }
    }
}
