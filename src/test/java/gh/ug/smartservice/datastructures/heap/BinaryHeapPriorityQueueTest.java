package gh.ug.smartservice.datastructures.heap;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BinaryHeapPriorityQueueTest {

    @Test
    void extractsInPriorityOrder() {
        BinaryHeapPriorityQueue<String> pq = new BinaryHeapPriorityQueue<>();
        pq.insert("low", 5);
        pq.insert("high", 1);
        pq.insert("mid", 3);
        assertEquals("high", pq.extractMin());
        assertEquals("mid", pq.extractMin());
        assertEquals("low", pq.extractMin());
    }

    @Test
    void growsBeyondDefaultCapacity() {
        BinaryHeapPriorityQueue<Integer> pq = new BinaryHeapPriorityQueue<>();
        for (int i = 20; i >= 0; i--) {
            pq.insert(i, i);
        }
        assertEquals(21, pq.size());
        assertEquals(0, pq.extractMin());
    }

    @Test
    void emptyQueueIsEmpty() {
        BinaryHeapPriorityQueue<Integer> pq = new BinaryHeapPriorityQueue<>();
        assertTrue(pq.isEmpty());
    }

    @Test
    void extractOrPeekOnEmptyThrows() {
        BinaryHeapPriorityQueue<Integer> pq = new BinaryHeapPriorityQueue<>();
        assertThrows(NoSuchElementException.class, pq::extractMin);
        assertThrows(NoSuchElementException.class, pq::peekMin);
    }
}
