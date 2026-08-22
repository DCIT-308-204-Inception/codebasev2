package gh.ug.smartservice.datastructures.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    @Test
    void insertAndContains() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        int[] values = {10, 20, 30, 15, 5, 25, 1};
        for (int v : values) {
            tree.insert(v);
        }
        for (int v : values) {
            assertTrue(tree.contains(v));
        }
        assertEquals(values.length, tree.size());
    }

    @Test
    void ascendingInsertsStayBalanced() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        for (int i = 0; i < 1000; i++) {
            tree.insert(i);
        }
        assertEquals(1000, tree.size());
        assertTrue(tree.contains(999));
        assertTrue(tree.contains(0));
    }

    @Test
    void emptyTreeBoundary() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        assertTrue(tree.isEmpty());
        assertFalse(tree.contains(1));
    }

    @Test
    void nullValueThrows() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        assertThrows(NullPointerException.class, () -> tree.insert(null));
        assertThrows(NullPointerException.class, () -> tree.contains(null));
    }
}
