package gh.ug.smartservice.datastructures.graph;

import gh.ug.smartservice.datastructures.api.SimpleGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class AdjacencyListGraph implements SimpleGraph {

    private static final int DEFAULT_CAPACITY = 8;

    private static final class Node {
        int to;
        double weight;
        Node next;

        Node(int to, double weight, Node next) {
            this.to = to;
            this.weight = weight;
            this.next = next;
        }
    }

    private Node[] heads;
    private boolean[] present;
    private int vertexCount;

    public AdjacencyListGraph() {
        heads = new Node[DEFAULT_CAPACITY];
        present = new boolean[DEFAULT_CAPACITY];
    }

    @Override
    public void addVertex(int vertexId) {
        ensureCapacity(vertexId + 1);
        present[vertexId] = true;
        if (vertexId + 1 > vertexCount) {
            vertexCount = vertexId + 1;
        }
    }

    @Override
    public void addEdge(int fromId, int toId, double weight) {
        requireVertex(fromId);
        requireVertex(toId);
        heads[fromId] = insertOrUpdate(heads[fromId], toId, weight);
    }

    @Override
    public List<Integer> neighbours(int vertexId) {
        requireVertex(vertexId);
        List<Integer> result = new ArrayList<>();
        Node current = heads[vertexId];
        while (current != null) {
            result.add(current.to);
            current = current.next;
        }
        return result;
    }

    @Override
    public double weight(int fromId, int toId) {
        requireVertex(fromId);
        requireVertex(toId);
        Node current = heads[fromId];
        while (current != null) {
            if (current.to == toId) {
                return current.weight;
            }
            current = current.next;
        }
        throw new NoSuchElementException("No edge from " + fromId + " to " + toId);
    }

    @Override
    public int vertexCount() {
        return vertexCount;
    }

    private Node insertOrUpdate(Node head, int to, double weight) {
        Node current = head;
        while (current != null) {
            if (current.to == to) {
                current.weight = weight;
                return head;
            }
            current = current.next;
        }
        return new Node(to, weight, head);
    }

    private void requireVertex(int vertexId) {
        if (vertexId < 0 || vertexId >= heads.length || !present[vertexId]) {
            throw new NoSuchElementException("Unknown vertex: " + vertexId);
        }
    }

    private void ensureCapacity(int required) {
        if (required <= heads.length) {
            return;
        }
        int newCapacity = Math.max(required, heads.length * 2);
        heads = Arrays.copyOf(heads, newCapacity);
        present = Arrays.copyOf(present, newCapacity);
    }
}
