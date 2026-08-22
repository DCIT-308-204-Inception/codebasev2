package gh.ug.smartservice.datastructures.hash;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomMapTest {

    @Test
    void putGetAndOverwrite() {
        CustomMap<String, Integer> map = new CustomMap<>();
        assertNull(map.put("a", 1));
        assertEquals(1, map.put("a", 2));
        assertEquals(2, map.get("a"));
        assertEquals(1, map.size());
    }

    @Test
    void resizesBeyondDefaultCapacity() {
        CustomMap<Integer, Integer> map = new CustomMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i * i);
        }
        assertEquals(100, map.size());
        assertEquals(2401, map.get(49));
    }

    @Test
    void missingKeyBoundary() {
        CustomMap<String, Integer> map = new CustomMap<>();
        assertFalse(map.containsKey("missing"));
        assertNull(map.get("missing"));
        assertNull(map.remove("missing"));
    }

    @Test
    void nullKeyIsSupported() {
        CustomMap<String, Integer> map = new CustomMap<>();
        map.put(null, 42);
        assertTrue(map.containsKey(null));
        assertEquals(42, map.get(null));
    }
}
