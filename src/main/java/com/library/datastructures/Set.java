package com.library.datastructures;

import java.util.Iterator;

public interface Set<E> extends Iterable<E> {
    boolean add(E element);
    boolean remove(Object element);
    boolean contains(Object element);
    int size();
    boolean isEmpty();
    void clear();
    boolean addAll(Set<? extends E> other);
    boolean removeAll(Set<?> other);
    boolean retainAll(Set<?> other);
    Iterator<E> iterator();
}
