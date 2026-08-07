import java.util.ArrayList;
import java.util.List;

public class BTree {

    private Node root;
    private final int t;

    public BTree(int t) {
        this.t = t;
        this.root = new Node(t, true);
    }


    private static class Node {
        int[] keys;
        int t;
        Node[] children;
        int n;
        boolean leaf;

        Node(int t, boolean leaf) {
            this.t = t;
            this.leaf = leaf;
            this.keys = new int[2 * t - 1];
            this.children = new Node[2 * t];
            this.n = 0;
        }


        Node search(int key) {
            int i = 0;
            while (i < n && key > keys[i]) i++;

            if (i < n && keys[i] == key) return this;
            if (leaf) return null;
            return children[i].search(key);
        }


        void insertNonFull(int key) {
            int i = n - 1;

            if (leaf) {
                while (i >= 0 && keys[i] > key) {
                    keys[i + 1] = keys[i];
                    i--;
                }
                keys[i + 1] = key;
                n++;
            } else {
                while (i >= 0 && keys[i] > key) i--;
                i++;

                if (children[i].n == 2 * t - 1) {
                    splitChild(i, children[i]);
                    if (keys[i] < key) i++;
                }
                children[i].insertNonFull(key);
            }
        }


        void splitChild(int i, Node y) {
            Node z = new Node(y.t, y.leaf);
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


        void traverse(List<Integer> result) {
            int i;
            for (i = 0; i < n; i++) {
                if (!leaf) children[i].traverse(result);
                result.add(keys[i]);
            }
            if (!leaf) children[i].traverse(result);
        }
    }


    public boolean search(int key) {
        return root.search(key) != null;
    }


    public void insert(int key) {
        if (root.n == 2 * t - 1) {
            Node s = new Node(t, false);
            s.children[0] = root;
            s.splitChild(0, root);

            int i = 0;
            if (s.keys[0] < key) i++;
            s.children[i].insertNonFull(key);

            root = s;
        } else {
            root.insertNonFull(key);
        }
    }


    public List<Integer> traverse() {
        List<Integer> result = new ArrayList<>();
        root.traverse(result);
        return result;
    }


    public static void main(String[] args) {
        BTree tree = new BTree(3);
        int[] values = {10, 20, 5, 6, 12, 30, 7, 17, 3, 25, 1, 15};
        for (int v : values) {
            tree.insert(v);
        }

        System.out.println("In-order traversal: " + tree.traverse());

        int searchKey = 6;
        System.out.println("Search " + searchKey + ": " + tree.search(searchKey));

        searchKey = 100;
        System.out.println("Search " + searchKey + ": " + tree.search(searchKey));
    }
}