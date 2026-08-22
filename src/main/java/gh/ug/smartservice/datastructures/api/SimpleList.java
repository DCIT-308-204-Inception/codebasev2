package gh.ug.smartservice.datastructures.api;

import java.util.Iterator;

/**
 * Contract every list-like structure (array list, linked list) must satisfy.
 * Algorithm/engine code should depend on this interface, not on
 * DynamicArrayList or DoublyLinkedList directly, so swapping the
 * implementation never breaks callers.
 */
public interface SimpleList<T> extends Iterable<T> {
    void add(T item);
    void insertAt(int index, T item);
    T get(int index);
    T set(int index, T item);
    T removeAt(int index);
    boolean isEmpty();
    int size();
    Iterator<T> iterator();
}
