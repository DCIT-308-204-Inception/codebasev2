package gh.ug.smartservice.datastructures.queue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CircularQueueTest {

    @Test
    void enqueueDequeueFifoOrder() {
        CircularQueue<Integer> queue = new CircularQueue<>(3);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        assertTrue(queue.isFull());
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.peekFront());
    }

    @Test
    void wrapsAroundAfterDequeue() {
        CircularQueue<Integer> queue = new CircularQueue<>(2);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.dequeue();
        queue.enqueue(3);
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());
    }

    @Test
    void emptyQueueIsEmpty() {
        CircularQueue<Integer> queue = new CircularQueue<>(1);
        assertTrue(queue.isEmpty());
    }

    @Test
    void fullQueueRejectsEnqueueAndEmptyRejectsDequeue() {
        CircularQueue<Integer> queue = new CircularQueue<>(1);
        queue.enqueue(1);
        assertThrows(IllegalStateException.class, () -> queue.enqueue(2));
        queue.dequeue();
        assertThrows(NoSuchElementException.class, queue::dequeue);
    }
}
