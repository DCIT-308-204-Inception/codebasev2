package gh.ug.smartservice.datastructures.api;

import java.util.Iterator;

/**
 * Contract every list-like structure (array list, linked list) must satisfy.
 * Algorithm/engine authors should code against THIS interface, not against
 * DynamicArrayList or DoublyLinkedList directly. That way, swapping the
 * implementation or fixing a bug inside it never breaks calling code,
 * and two people can work on "the list" and "the code that uses a list"
 * at the same time without touching the same file.
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
