import java.util.Arrays;

/**
 Assigned Structure: Priority Queue / Heap
 * Local Context: Ghana Library / Records System (Balme Library / Archives)
 */
public class BrightMoore_PriorityQueue {

    // 1. DOMAIN ENTITY: ServiceRequest (Library Book/Record Retrieval Request)

    public static class ServiceRequest {
        private int requestId;
        private String source;
        private String destination;
        private String category;
        private int urgency;          // 1 (Low routine borrow) to 10 (Critical archival lookup)
        private long timeSubmitted;   // Timestamp or tick
        private long deadline;        // Earliest deadline = highest urgency tie-breaker
        private String status;        // e.g., "PENDING", "DISPATCHED"

        public ServiceRequest(int requestId, String source, String destination,
                              String category, int urgency, long timeSubmitted,
                              long deadline, String status) {
            this.requestId = requestId;
            this.source = source;
            this.destination = destination;
            this.category = category;
            this.urgency = urgency;
            this.timeSubmitted = timeSubmitted;
            this.deadline = deadline;
            this.status = status;
        }

        public int getRequestId() { return requestId; }
        public int getUrgency() { return urgency; }
        public long getDeadline() { return deadline; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        @Override
        public String toString() {
            return String.format("REQ-#%03d [%-17s | Urgency: %2d | Deadline: %3d | Status: %s]",
                    requestId, category, urgency, deadline, status);
        }
    }

    // 2. PRIORITY QUEUE / HEAP DATA STRUCTURE IMPLEMENTATION

    private ServiceRequest[] heap;
    private int size;
    private int capacity;
    private boolean isMaxHeap; // true = prioritize highest urgency; false = prioritize earliest deadline

    public BrightMoore_PriorityQueue(int initialCapacity, boolean isMaxHeap) {
        this.capacity = initialCapacity;
        this.size = 0;
        this.isMaxHeap = isMaxHeap;
        this.heap = new ServiceRequest[capacity];
    }

    private int parent(int i) { return (i - 1) / 2; }
    private int leftChild(int i) { return 2 * i + 1; }
    private int rightChild(int i) { return 2 * i + 2; }

    private void swap(int i, int j) {
        ServiceRequest temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    // Priority comparison: Max-Heap prioritizes highest urgency (breaks ties via earlier deadline)
    private boolean hasHigherPriority(ServiceRequest a, ServiceRequest b) {
        if (isMaxHeap) {
            if (a.getUrgency() == b.getUrgency()) {
                return a.getDeadline() < b.getDeadline();
            }
            return a.getUrgency() > b.getUrgency();
        } else {
            if (a.getDeadline() == b.getDeadline()) {
                return a.getUrgency() > b.getUrgency();
            }
            return a.getDeadline() < b.getDeadline();
        }
    }

    // --- CORE OPERATION 1: INSERT O(log n) ---
    public void insert(ServiceRequest request) {
        if (size == capacity) {
            capacity *= 2;
            heap = Arrays.copyOf(heap, capacity);
        }

        int current = size;
        heap[size++] = request;

        // "Bubble Up" / Up-Heap until priority order is restored
        while (current > 0 && hasHigherPriority(heap[current], heap[parent(current)])) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    // --- CORE OPERATION 2: EXTRACT TOP O(log n) ---
    public ServiceRequest extractTop() {
        if (size == 0) {
            throw new IllegalStateException("Cannot extract: Library Priority Queue is empty!");
        }

        ServiceRequest topRequest = heap[0];
        topRequest.setStatus("DISPATCHED");

        heap[0] = heap[size - 1];
        heap[size - 1] = null; // Clear reference
        size--;

        if (size > 0) {
            heapify(0);
        }

        return topRequest;
    }

    // --- CORE OPERATION 3: HEAPIFY (Bubble Down) O(log n) ---
    public void heapify(int i) {
        int highestPriorityIndex = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && hasHigherPriority(heap[left], heap[highestPriorityIndex])) {
            highestPriorityIndex = left;
        }

        if (right < size && hasHigherPriority(heap[right], heap[highestPriorityIndex])) {
            highestPriorityIndex = right;
        }

        if (highestPriorityIndex != i) {
            swap(i, highestPriorityIndex);
            heapify(highestPriorityIndex);
        }
    }

    public ServiceRequest peek() {
        if (size == 0) throw new IllegalStateException("Priority Queue is empty!");
        return heap[0];
    }

    public boolean isEmpty() { return size == 0; }
    public int getSize() { return size; }

    // --- TRACE TABLE GENERATOR (Mandatory Correctness Evidence) ---
    public void printHeapTrace(String actionDescription) {
        System.out.println("\n--- TRACE TABLE: " + actionDescription + " ---");
        System.out.printf("%-8s | %-10s | %-18s | %-8s | %-8s%n",
                "Index", "Req ID", "Category", "Urgency", "Deadline");
        System.out.println("-------------------------------------------------------------------");
        for (int i = 0; i < size; i++) {
            System.out.printf("Index %-2d | REQ-#%-5d | %-18s | %-8d | %-8d%n",
                    i, heap[i].getRequestId(), heap[i].category, heap[i].getUrgency(), heap[i].getDeadline());
        }
        if (size == 0) {
            System.out.println(" [Priority Queue is currently empty]");
        }
        System.out.println("-------------------------------------------------------------------");
    }


    // 3. MAIN METHOD: BUILT-IN TEST SUITE & ORAL DEFENSE DEMO

    public static void main(String[] args) {
        System.out.println("===================================================================");
        System.out.println("   SMART LIBRARY RECORDS SYSTEM - PRIORITY QUEUE / HEAP      ");
        System.out.println("===================================================================");

        // Initialize Library Priority Queue (Max-Heap mode: Urgency first)
        BrightMoore_PriorityQueue libraryQueue = new BrightMoore_PriorityQueue(10, true);

        // 1. NORMAL CASE TEST: Adding various library record requests
        System.out.println("\n[TEST 1] Inserting Library Requests into Priority Queue...");
        libraryQueue.insert(new ServiceRequest(101, "Main Shelves", "Student Reading Room",
                "BOOK_BORROW", 3, 100, 300, "PENDING"));
        libraryQueue.insert(new ServiceRequest(102, "Balme Reserve", "Law Faculty Hall",
                "FACULTY_RESERVE", 9, 105, 140, "PENDING"));
        libraryQueue.insert(new ServiceRequest(103, "Special Vault", "Research Archives Desk",
                "ARCHIVE_RETRIEVAL", 10, 110, 130, "PENDING"));
        libraryQueue.insert(new ServiceRequest(104, "African Studies", "Main Reference Desk",
                "INTER_LIBRARY", 6, 115, 200, "PENDING"));

        // Evidence: Show how the array arranges highest urgency at Index 0
        libraryQueue.printHeapTrace("After Inserting 4 Library Requests");

        // 2. DISPATCHING TOP PRIORITY REQUESTS
        System.out.println("\n-> Dispatching Most Urgent Request: ");
        System.out.println("   SERVED: " + libraryQueue.extractTop());
        libraryQueue.printHeapTrace("After Extracting REQ-#103 (Urgency 10 - Archive Vault)");

        System.out.println("\n-> Dispatching Next Most Urgent Request: ");
        System.out.println("   SERVED: " + libraryQueue.extractTop());
        libraryQueue.printHeapTrace("After Extracting REQ-#102 (Urgency 9 - Faculty Reserve)");

        // 3. BOUNDARY CASE: TIE-BREAKING BY DEADLINE
        System.out.println("\n[TEST 2] Boundary Case: Tie-Breaking Duplicate Urgency Levels");
        libraryQueue.insert(new ServiceRequest(105, "Law Library", "Graduate Seminar Room",
                "BOOK_BORROW", 6, 120, 150, "PENDING"));
        // Both REQ-104 and REQ-105 have Urgency 6, but REQ-105 has an earlier deadline (150 vs 200)
        libraryQueue.printHeapTrace("REQ-104 & REQ-105 Tied on Urgency (Level 6)");
        System.out.println("Top Request Ready for Dispatch (Earlier deadline wins tie):");
        System.out.println(" -> " + libraryQueue.peek());

        // 4. INVALID INPUT CASE: EXTRACTING FROM EMPTY HEAP
        System.out.println("\n[TEST 3] Invalid Case Handling: Extracting from Empty Queue");
        BrightMoore_PriorityQueue emptyQueue = new BrightMoore_PriorityQueue(2, true);
        try {
            emptyQueue.extractTop();
        } catch (IllegalStateException e) {
            System.out.println("SUCCESS: Safely caught expected error -> \"" + e.getMessage() + "\"");
        }
    }
}