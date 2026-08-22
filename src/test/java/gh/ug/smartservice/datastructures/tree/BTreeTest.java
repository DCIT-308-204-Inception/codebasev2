package gh.ug.smartservice.datastructures.tree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {

    @Test
    void insertAndSearch() {
        BTree<Integer> tree = new BTree<>(3);
        int[] values = {10, 20, 5, 6, 12, 30, 7, 17};
        for (int v : values) {
            tree.insert(v);
        }
        for (int v : values) {
            assertTrue(tree.search(v));
        }
        assertFalse(tree.search(999));
    }

    @Test
    void traverseIsSorted() {
        BTree<Integer> tree = new BTree<>(2);
        for (int v : new int[]{5, 3, 8, 1, 4, 7, 9, 2, 6}) {
            tree.insert(v);
        }
        List<Integer> traversal = tree.traverse();
        for (int i = 1; i < traversal.size(); i++) {
            assertTrue(traversal.get(i - 1) < traversal.get(i));
        }
    }

    @Test
    void minimumDegreeBoundary() {
        assertThrows(IllegalArgumentException.class, () -> new BTree<Integer>(1));
    }

    @Test
    void nullKeyThrows() {
        BTree<Integer> tree = new BTree<>(2);
        assertThrows(NullPointerException.class, () -> tree.insert(null));
        assertThrows(NullPointerException.class, () -> tree.search(null));
    }
}
