public class ArrayBackedList<T> {

    private Object[] data; // internal storage array
    private int size; // number of elements currently stored
    private int capacity; // total slots available

    private static final int DEFAULT_CAPACITY = 10;
    private static final double GROWTH_FACTOR = 2.0;

    public ArrayBackedList() {
        this.capacity = DEFAULT_CAPACITY;
        this.data = new Object[capacity];
        this.size = 0;
    }

    public ArrayBackedList(int initialCapacity) {
        if (initialCapacity <= 0)
            throw new IllegalArgumentException("Capacity must be positive.");
        this.capacity = initialCapacity;
        this.data = new Object[capacity];
        this.size = 0;
    }

    public void insert(T element) {
        if (size == capacity)
            resize(); // grow before overflow
        data[size] = element;
        size++;
    }

    public void insertAt(int index, T element) {
        checkIndexForInsert(index);
        if (size == capacity)
            resize();
        // shift everything from index onward one step right
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public void set(int index, T element) {
        checkIndex(index);
        data[index] = element;
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T removed = (T) data[index];
        // shift everything after index one step left
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null; // clear last slot to help GC
        size--;
        return removed;
    }

    @SuppressWarnings("unchecked")
    public T removeLast() {
        if (isEmpty())
            throw new RuntimeException("List is empty.");
        T removed = (T) data[size - 1];
        data[size - 1] = null;
        size--;
        return removed;
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(element))
                return true;
        }
        return false;
    }

    private void resize() {
        int newCapacity = (int) (capacity * GROWTH_FACTOR);
        Object[] newData = new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i]; // copy all elements
        }
        data = newData; // point to new array
        capacity = newCapacity;
        System.out.println("[RESIZE] capacity: " + (newCapacity / 2)
                + " → " + newCapacity);
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++)
            data[i] = null;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(
                    "Index " + index + " out of bounds for size " + size);
    }

    private void checkIndexForInsert(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException(
                    "Insert index " + index + " out of bounds for size " + size);
    }
}