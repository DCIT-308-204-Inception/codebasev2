package gh.ug.smartservice.datastructures.queue;

import gh.ug.smartservice.datastructures.api.SimpleQueue;

import java.util.NoSuchElementException;

public class CircularQueue<T> implements SimpleQueue<T> {

    private final Object[] data;
    private int front;
    private int rear;
    private int size;

    public CircularQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity must be at least 1");
        }
        data = new Object[capacity];
        front = 0;
        rear = -1;
    }

    @Override
    public void enqueue(T item) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % data.length;
        data[rear] = item;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        T item = (T) data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return item;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T peekFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return (T) data[front];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean isFull() {
        return size == data.length;
    }

    @Override
    public int size() {
        return size;
    }
}
