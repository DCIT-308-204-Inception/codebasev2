package gh.ug.smartservice.datastructures.api;

public interface SimplePriorityQueue<T> {
    void insert(T item, int priority);
    T extractMin();
    T peekMin();
    boolean isEmpty();
    int size();
}
