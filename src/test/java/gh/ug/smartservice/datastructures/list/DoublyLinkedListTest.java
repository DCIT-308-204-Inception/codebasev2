package gh.ug.smartservice.datastructures.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoublyLinkedListTest {

    @Test
    void addAndGetInOrder() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("c", list.get(2));
    }

    @Test
    void insertAtAndRemoveAt() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        list.add(3);
        list.insertAt(1, 2);
        assertEquals(2, list.get(1));
        assertEquals(2, list.removeAt(1));
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    void emptyListIsEmpty() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void invalidIndexThrows() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(-1, 9));
    }
}
