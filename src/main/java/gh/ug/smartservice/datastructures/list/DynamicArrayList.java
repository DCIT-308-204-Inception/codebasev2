package gh.ug.smartservice.datastructures.list;

import gh.ug.smartservice.datastructures.api.SimpleList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DynamicArrayList<T> implements SimpleList<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROWTH_FACTOR = 2;

    private Object[] data;
    private int size;

    public DynamicArrayList() {
        data = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(T item) {
        ensureCapacity(size + 1);
        data[size++] = item;
    }

    @Override
    public void insertAt(int index, T item) {
        checkIndexForInsert(index);
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = item;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T set(int index, T item) {
        checkIndex(index);
        T old = (T) data[index];
        data[index] = item;
        return old;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T removeAt(int index) {
        checkIndex(index);
        T removed = (T) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[--size] = null;
        return removed;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) data[index++];
            }
        };
    }

    private void ensureCapacity(int required) {
        if (required <= data.length) {
            return;
        }
        int newCapacity = Math.max(required, data.length * GROWTH_FACTOR);
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
    }

    private void checkIndexForInsert(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Insert index " + index + " out of bounds for size " + size);
        }
    }
}
