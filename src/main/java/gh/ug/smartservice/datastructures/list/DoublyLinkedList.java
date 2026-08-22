package gh.ug.smartservice.datastructures.list;

import gh.ug.smartservice.datastructures.api.SimpleList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<T> implements SimpleList<T> {

    private static final class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T item) {
        Node<T> node = new Node<>(item);
        if (head == null) {
            head = tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public void insertAt(int index, T item) {
        checkIndexForInsert(index);
        if (index == size) {
            add(item);
            return;
        }
        Node<T> node = new Node<>(item);
        if (index == 0) {
            node.next = head;
            head.prev = node;
            head = node;
        } else {
            Node<T> after = nodeAt(index);
            Node<T> before = after.prev;
            node.prev = before;
            node.next = after;
            before.next = node;
            after.prev = node;
        }
        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return nodeAt(index).data;
    }

    @Override
    public T set(int index, T item) {
        checkIndex(index);
        Node<T> node = nodeAt(index);
        T old = node.data;
        node.data = item;
        return old;
    }

    @Override
    public T removeAt(int index) {
        checkIndex(index);
        Node<T> node = nodeAt(index);
        Node<T> before = node.prev;
        Node<T> after = node.next;
        if (before == null) {
            head = after;
        } else {
            before.next = after;
        }
        if (after == null) {
            tail = before;
        } else {
            after.prev = before;
        }
        size--;
        return node.data;
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
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    private Node<T> nodeAt(int index) {
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
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
