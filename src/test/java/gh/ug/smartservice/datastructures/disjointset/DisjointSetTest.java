package gh.ug.smartservice.datastructures.disjointset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisjointSetTest {

    @Test
    void unionConnectsComponents() {
        DisjointSet ds = new DisjointSet(5);
        assertFalse(ds.connected(0, 1));
        ds.union(0, 1);
        assertTrue(ds.connected(0, 1));
    }

    @Test
    void unionOfAlreadyConnectedReturnsFalse() {
        DisjointSet ds = new DisjointSet(3);
        assertTrue(ds.union(0, 1));
        assertFalse(ds.union(0, 1));
    }

    @Test
    void singleElementIsSelfConnected() {
        DisjointSet ds = new DisjointSet(1);
        assertTrue(ds.connected(0, 0));
    }

    @Test
    void outOfRangeIndexThrows() {
        DisjointSet ds = new DisjointSet(2);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> ds.find(5));
    }
}
