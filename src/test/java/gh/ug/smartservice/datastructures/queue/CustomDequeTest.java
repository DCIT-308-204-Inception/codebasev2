package gh.ug.smartservice.datastructures.queue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CustomDequeTest {

    @Test
    void addFrontAndRearOrder() {
        CustomDeque<Integer> deque = new CustomDeque<>();
        deque.addRear(2);
        deque.addFront(1);
        deque.addRear(3);
        assertEquals(1, deque.peekFront());
        assertEquals(3, deque.peekRear());
        assertEquals(3, deque.size());
    }

    @Test
    void removeFromBothEnds() {
        CustomDeque<Integer> deque = new CustomDeque<>();
        deque.addRear(1);
        deque.addRear(2);
        deque.addRear(3);
        assertEquals(1, deque.removeFront());
        assertEquals(3, deque.removeRear());
        assertEquals(1, deque.size());
    }

    @Test
    void singleElementBoundary() {
        CustomDeque<Integer> deque = new CustomDeque<>();
        deque.addFront(5);
        assertEquals(5, deque.removeRear());
        assertTrue(deque.isEmpty());
    }

    @Test
    void emptyDequeThrows() {
        CustomDeque<Integer> deque = new CustomDeque<>();
        assertThrows(NoSuchElementException.class, deque::removeFront);
        assertThrows(NoSuchElementException.class, deque::removeRear);
    }
}
