package com.library.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSet<E> implements Set<E> {

    private static final Object PRESENT = new Object();
    private final HashMap<E, Object> map;

    public HashSet(int initialCapacity) {
        this.map = new HashMap<>(initialCapacity);
    }

    public HashSet() {
        this.map = new HashMap<>();
    }

    @Override
    public boolean add(E element) {
        return map.put(element, PRESENT) == null;
    }

    @Override
    public boolean remove(Object element) {
        return map.remove(element) != null;
    }

    @Override
    public boolean contains(Object element) {
        return map.containsKey(element);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean addAll(Set<? extends E> other) {
        boolean modified = false;
        for (E e : other) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Set<?> other) {
        boolean modified = false;
        for (Object e : other) {
            if (remove(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Set<?> other) {
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!other.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentBucket = 0;
            private Entry<E, Object> currentEntry = null;
            private Entry<E, Object> nextEntry = null;
            private Entry<E, Object> lastReturned = null;
            private boolean canRemove = false;

            {
                advance();
            }

            @Override
            public boolean hasNext() {
                return nextEntry != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lastReturned = nextEntry;
                currentEntry = nextEntry;
                E key = nextEntry.key;
                advance();
                canRemove = true;
                return key;
            }

            @Override
            public void remove() {
                if (!canRemove) {
                    throw new IllegalStateException();
                }
                map.remove(lastReturned.key);
                lastReturned = null;
                canRemove = false;
            }

            private void advance() {
                if (currentEntry != null) {
                    nextEntry = currentEntry.next;
                    if (nextEntry != null) return;
                }

                while (currentBucket < map.table.length) {
                    nextEntry = map.table[currentBucket];
                    currentBucket++;
                    if (nextEntry != null) {
                        currentEntry = nextEntry;
                        return;
                    }
                }
                nextEntry = null;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        Iterator<E> it = iterator();
        boolean first = true;
        while (it.hasNext()) {
            if (!first) sb.append(", ");
            sb.append(it.next());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }
}
