package gh.ug.smartservice.datastructures.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomSetTest {

    @Test
    void addRejectsDuplicates() {
        CustomSet<String> set = new CustomSet<>();
        assertTrue(set.add("book-1"));
        assertFalse(set.add("book-1"));
        assertEquals(1, set.size());
    }

    @Test
    void removeAndContains() {
        CustomSet<Integer> set = new CustomSet<>();
        set.add(1);
        set.add(2);
        assertTrue(set.remove(1));
        assertFalse(set.contains(1));
        assertTrue(set.contains(2));
    }

    @Test
    void emptySetBoundary() {
        CustomSet<Integer> set = new CustomSet<>();
        assertEquals(0, set.size());
        assertFalse(set.remove(1));
    }

    @Test
    void nullElementIsSupported() {
        CustomSet<String> set = new CustomSet<>();
        assertTrue(set.add(null));
        assertTrue(set.contains(null));
    }
}
