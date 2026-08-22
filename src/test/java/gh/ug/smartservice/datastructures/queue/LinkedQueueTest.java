package gh.ug.smartservice.datastructures.queue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedQueueTest {

    @Test
    void enqueueDequeueFifoOrder() {
        LinkedQueue<String> queue = new LinkedQueue<>();
        queue.enqueue("a");
        queue.enqueue("b");
        assertEquals("a", queue.dequeue());
        assertEquals("b", queue.peekFront());
    }

    @Test
    void neverReportsFull() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        for (int i = 0; i < 100; i++) {
            queue.enqueue(i);
        }
        assertFalse(queue.isFull());
        assertEquals(100, queue.size());
    }

    @Test
    void emptyQueueIsEmpty() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        assertTrue(queue.isEmpty());
    }

    @Test
    void dequeueOrPeekOnEmptyThrows() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        assertThrows(NoSuchElementException.class, queue::dequeue);
        assertThrows(NoSuchElementException.class, queue::peekFront);
    }
}
