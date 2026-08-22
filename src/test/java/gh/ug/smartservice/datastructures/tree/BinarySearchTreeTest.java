package gh.ug.smartservice.datastructures.tree;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    @Test
    void insertAndInorderIsSorted() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        int[] values = {5, 3, 8, 1, 4, 7, 9};
        for (int v : values) {
            bst.insert(v);
        }
        List<Integer> inorder = bst.inorder();
        for (int i = 1; i < inorder.size(); i++) {
            assertTrue(inorder.get(i - 1) < inorder.get(i));
        }
        assertEquals(values.length, bst.size());
    }

    @Test
    void deleteNodeWithTwoChildren() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int v : new int[]{5, 3, 8, 1, 4, 7, 9}) {
            bst.insert(v);
        }
        bst.delete(3);
        assertFalse(bst.contains(3));
        assertTrue(bst.contains(1));
        assertTrue(bst.contains(4));
    }

    @Test
    void emptyTreeBoundary() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertTrue(bst.isEmpty());
        assertFalse(bst.contains(1));
        bst.delete(1);
        assertTrue(bst.isEmpty());
    }

    @Test
    void nullValueThrows() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        assertThrows(NullPointerException.class, () -> bst.insert(null));
        assertThrows(NullPointerException.class, () -> bst.contains(null));
    }
}
