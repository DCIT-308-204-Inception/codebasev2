package gh.ug.smartservice.datastructures.heap;

import gh.ug.smartservice.datastructures.api.SimplePriorityQueue;

import java.util.NoSuchElementException;

public class BinaryHeapPriorityQueue<T> implements SimplePriorityQueue<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROWTH_FACTOR = 2;

    private Object[] items;
    private int[] priorities;
    private int size;

    public BinaryHeapPriorityQueue() {
        items = new Object[DEFAULT_CAPACITY];
        priorities = new int[DEFAULT_CAPACITY];
    }

    @Override
    public void insert(T item, int priority) {
        ensureCapacity(size + 1);
        items[size] = item;
        priorities[size] = priority;
        bubbleUp(size);
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        T min = (T) items[0];
        size--;
        items[0] = items[size];
        priorities[0] = priorities[size];
        items[size] = null;
        if (size > 0) {
            bubbleDown(0);
        }
        return min;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return (T) items[0];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (priorities[index] >= priorities[parent]) {
                break;
            }
            swap(index, parent);
            index = parent;
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;
            if (left < size && priorities[left] < priorities[smallest]) {
                smallest = left;
            }
            if (right < size && priorities[right] < priorities[smallest]) {
                smallest = right;
            }
            if (smallest == index) {
                break;
            }
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        Object tempItem = items[i];
        items[i] = items[j];
        items[j] = tempItem;
        int tempPriority = priorities[i];
        priorities[i] = priorities[j];
        priorities[j] = tempPriority;
    }

    private void ensureCapacity(int required) {
        if (required <= items.length) {
            return;
        }
        int newCapacity = Math.max(required, items.length * GROWTH_FACTOR);
        Object[] newItems = new Object[newCapacity];
        int[] newPriorities = new int[newCapacity];
        System.arraycopy(items, 0, newItems, 0, size);
        System.arraycopy(priorities, 0, newPriorities, 0, size);
        items = newItems;
        priorities = newPriorities;
    }
}
