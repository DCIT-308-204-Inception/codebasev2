package gh.ug.smartservice.datastructures.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayListTest {

    @Test
    void addGrowsBeyondDefaultCapacity() {
        DynamicArrayList<Integer> list = new DynamicArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        assertEquals(50, list.size());
        assertEquals(0, list.get(0));
        assertEquals(49, list.get(49));
    }

    @Test
    void setAndRemoveShiftElements() {
        DynamicArrayList<String> list = new DynamicArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.set(1, "x");
        assertEquals("x", list.get(1));
        assertEquals("x", list.removeAt(1));
        assertEquals("c", list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void emptyListBoundary() {
        DynamicArrayList<Integer> list = new DynamicArrayList<>();
        assertTrue(list.isEmpty());
        assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(0));
    }

    @Test
    void invalidIndexThrows() {
        DynamicArrayList<Integer> list = new DynamicArrayList<>();
        list.add(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.insertAt(2, 9));
    }
}
