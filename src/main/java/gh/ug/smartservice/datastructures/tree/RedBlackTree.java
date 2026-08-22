package gh.ug.smartservice.datastructures.tree;

public class RedBlackTree<T extends Comparable<T>> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private final class Node {
        T value;
        Node left;
        Node right;
        Node parent;
        boolean color;

        Node(T value) {
            this.value = value;
            this.color = RED;
        }
    }

    private Node root;
    private int size;

    public void insert(T value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        Node node = new Node(value);
        Node parent = null;
        Node current = root;
        while (current != null) {
            parent = current;
            int cmp = value.compareTo(current.value);
            if (cmp == 0) {
                return;
            }
            current = cmp < 0 ? current.left : current.right;
        }
        node.parent = parent;
        if (parent == null) {
            root = node;
        } else if (value.compareTo(parent.value) < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }
        size++;
        fixInsert(node);
    }

    public boolean contains(T value) {
        if (value == null) {
            throw new NullPointerException("value cannot be null");
        }
        Node current = root;
        while (current != null) {
            int cmp = value.compareTo(current.value);
            if (cmp == 0) {
                return true;
            }
            current = cmp < 0 ? current.left : current.right;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void fixInsert(Node node) {
        while (node.parent != null && node.parent.color == RED) {
            Node grandparent = node.parent.parent;
            if (node.parent == grandparent.left) {
                Node uncle = grandparent.right;
                if (isRed(uncle)) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = BLACK;
                    grandparent.color = RED;
                    rotateRight(grandparent);
                }
            } else {
                Node uncle = grandparent.left;
                if (isRed(uncle)) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    grandparent.color = RED;
                    rotateLeft(grandparent);
                }
            }
        }
        root.color = BLACK;
    }

    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private void rotateLeft(Node node) {
        Node pivot = node.right;
        node.right = pivot.left;
        if (pivot.left != null) {
            pivot.left.parent = node;
        }
        pivot.parent = node.parent;
        if (node.parent == null) {
            root = pivot;
        } else if (node == node.parent.left) {
            node.parent.left = pivot;
        } else {
            node.parent.right = pivot;
        }
        pivot.left = node;
        node.parent = pivot;
    }

    private void rotateRight(Node node) {
        Node pivot = node.left;
        node.left = pivot.right;
        if (pivot.right != null) {
            pivot.right.parent = node;
        }
        pivot.parent = node.parent;
        if (node.parent == null) {
            root = pivot;
        } else if (node == node.parent.right) {
            node.parent.right = pivot;
        } else {
            node.parent.left = pivot;
        }
        pivot.right = node;
        node.parent = pivot;
    }
}
