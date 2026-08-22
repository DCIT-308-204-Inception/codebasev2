package gh.ug.smartservice.datastructures.api;

public interface SimpleStack<T> {
    void push(T item);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
}
