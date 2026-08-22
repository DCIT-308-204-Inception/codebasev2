package gh.ug.smartservice.datastructures.api;

public interface SimpleDeque<T> {
    void addFront(T item);
    void addRear(T item);
    T removeFront();
    T removeRear();
    T peekFront();
    T peekRear();
    boolean isEmpty();
    int size();
}
