package gh.ug.smartservice.datastructures.api;

public interface SimpleQueue<T> {
    void enqueue(T item);
    T dequeue();
    T peekFront();
    boolean isEmpty();
    boolean isFull();
    int size();
}
