package gh.ug.smartservice.datastructures.tree;

import java.util.ArrayList;
import java.util.List;

public class BTree<T extends Comparable<T>> {

    private Node root;
    private final int t;

    public BTree(int t) {
        if (t < 2) {
            throw new IllegalArgumentException("minimum degree t must be >= 2");
        }
        this.t = t;
        this.root = new Node(true);
    }

    private final class Node {
        Object[] keys;
        Node[] children;
        int n;
        boolean leaf;

        @SuppressWarnings("unchecked")
        Node(boolean leaf) {
            this.leaf = leaf;
            this.keys = new Object[2 * t - 1];
            this.children = (Node[]) new BTree.Node[2 * t];
            this.n = 0;
        }

        @SuppressWarnings("unchecked")
        T key(int idx) {
            return (T) keys[idx];
        }

        Node search(T key) {
            int i = 0;
            while (i < n && key.compareTo(key(i)) > 0) i++;

            if (i < n && key.compareTo(key(i)) == 0) return this;
            if (leaf) return null;
            return children[i].search(key);
        }

        void insertNonFull(T key) {
            int i = n - 1;

            if (leaf) {
                while (i >= 0 && key.compareTo(key(i)) < 0) {
                    keys[i + 1] = keys[i];
                    i--;
                }
                keys[i + 1] = key;
                n++;
            } else {
                while (i >= 0 && key.compareTo(key(i)) < 0) i--;
                i++;

                if (children[i].n == 2 * t - 1) {
                    splitChild(i, children[i]);
                    if (key.compareTo(key(i)) > 0) i++;
                }
                children[i].insertNonFull(key);
            }
        }

        void splitChild(int i, Node y) {
            Node z = new Node(y.leaf);
            z.n = t - 1;

            for (int j = 0; j < t - 1; j++) {
                z.keys[j] = y.keys[j + t];
            }

            if (!y.leaf) {
                for (int j = 0; j < t; j++) {
                    z.children[j] = y.children[j + t];
                }
            }

            y.n = t - 1;

            for (int j = n; j >= i + 1; j--) {
                children[j + 1] = children[j];
            }
            children[i + 1] = z;

            for (int j = n - 1; j >= i; j--) {
                keys[j + 1] = keys[j];
            }
            keys[i] = y.keys[t - 1];
            n++;
        }

        void traverse(List<T> result) {
            int i;
            for (i = 0; i < n; i++) {
                if (!leaf) children[i].traverse(result);
                result.add(key(i));
            }
            if (!leaf) children[i].traverse(result);
        }
    }

    public boolean search(T key) {
        if (key == null) {
            throw new NullPointerException("key cannot be null");
        }
        return root.search(key) != null;
    }

    public void insert(T key) {
        if (key == null) {
            throw new NullPointerException("key cannot be null");
        }
        if (root.n == 2 * t - 1) {
            Node s = new Node(false);
            s.children[0] = root;
            s.splitChild(0, root);

            int i = 0;
            if (key.compareTo(s.key(0)) > 0) i++;
            s.children[i].insertNonFull(key);

            root = s;
        } else {
            root.insertNonFull(key);
        }
    }

    public List<T> traverse() {
        List<T> result = new ArrayList<>();
        root.traverse(result);
        return result;
    }
}
