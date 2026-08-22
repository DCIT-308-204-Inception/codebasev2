package gh.ug.smartservice.datastructures.stack;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStackTest {

    @Test
    void pushPeekPopOrder() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.peek());
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.size());
    }

    @Test
    void growsBeyondDefaultCapacity() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        for (int i = 0; i < 20; i++) {
            stack.push(i);
        }
        assertEquals(20, stack.size());
        assertEquals(19, stack.pop());
    }

    @Test
    void emptyStackIsEmpty() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        assertTrue(stack.isEmpty());
    }

    @Test
    void popOrPeekOnEmptyThrows() {
        ArrayStack<Integer> stack = new ArrayStack<>();
        assertThrows(EmptyStackException.class, stack::pop);
        assertThrows(EmptyStackException.class, stack::peek);
    }
}
