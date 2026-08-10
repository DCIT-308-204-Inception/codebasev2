import java.util.Iterator;
import java.util.NoSuchElementException;

// 1. The data entity representing a library service request
class LibraryRequest {
    String requestId;
    String bookTitle;
    String urgency;
    String status;

    public LibraryRequest(String requestId, String bookTitle, String urgency, String status) {
        this.requestId = requestId;
        this.bookTitle = bookTitle;
        this.urgency = urgency;
        this.status = status;
    }

    @Override
    public String toString() {
        return "[ID: " + requestId + "] " + bookTitle + " (" + status + ")";
    }
}

// 2. The Custom Singly Linked List
public class RequestHistoryList implements Iterable<LibraryRequest> {

    // Node class to hold the data and the pointer to the next node
    public static class Node {
        LibraryRequest data;
        Node next;

        public Node(LibraryRequest data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public RequestHistoryList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Adds a new library request to the beginning of the history
    public void addFirst(LibraryRequest data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    // Adds a new library request to the end of the history
    public void addLast(LibraryRequest data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Inserts a request immediately after a specific given node
    public void insertAfter(Node prevNode, LibraryRequest data) {
        if (prevNode == null) {
            System.out.println("Error: Previous node cannot be null.");
            return;
        }
        Node newNode = new Node(data);
        newNode.next = prevNode.next;
        prevNode.next = newNode;
        
        // If we inserted at the very end, update the tail
        if (newNode.next == null) {
            tail = newNode;
        }
        size++;
    }

    // Removes a request based on its requestId
    public boolean remove(String targetRequestId) {
        if (head == null) return false;

        // Check if the head node is the one we want to remove
        if (head.data.requestId.equals(targetRequestId)) {
            head = head.next;
            if (head == null) {
                tail = null; // List became empty
            }
            size--;
            return true;
        }

        // Traverse to find the node right before the one we want to remove
        Node current = head;
        while (current.next != null && !current.next.data.requestId.equals(targetRequestId)) {
            current = current.next;
        }

        // If the node was found, remove it by skipping over it
        if (current.next != null) {
            if (current.next == tail) {
                tail = current; // Update tail if the last node is removed
            }
            current.next = current.next.next;
            size--;
            return true;
        }

        return false; // Request ID not found
    }

    // Provides an iterator to traverse the linked list
    @Override
    public Iterator<LibraryRequest> iterator() {
        return new Iterator<LibraryRequest>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public LibraryRequest next() {
                if (!hasNext()) throw new NoSuchElementException();
                LibraryRequest data = current.data;
                current = current.next;
                return data;
            }
         };
     } 
     public int getSize() {
        return size;
    }
}

    // Optional helper method to get the size