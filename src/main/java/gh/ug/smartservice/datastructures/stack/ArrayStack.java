package gh.ug.smartservice.datastructures.stack;

import gh.ug.smartservice.datastructures.api.SimpleStack;

import java.util.EmptyStackException;

public class ArrayStack<T> implements SimpleStack<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROWTH_FACTOR = 2;

    private Object[] data;
    private int top;

    public ArrayStack() {
        data = new Object[DEFAULT_CAPACITY];
        top = -1;
    }

    @Override
    public void push(T item) {
        if (top == data.length - 1) {
            Object[] grown = new Object[data.length * GROWTH_FACTOR];
            System.arraycopy(data, 0, grown, 0, data.length);
            data = grown;
        }
        data[++top] = item;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = (T) data[top];
        data[top--] = null;
        return item;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) data[top];
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public int size() {
        return top + 1;
    }
}
