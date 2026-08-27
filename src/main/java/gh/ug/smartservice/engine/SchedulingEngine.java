package gh.ug.smartservice.engine;

import gh.ug.smartservice.datastructures.heap.BinaryHeapPriorityQueue;
import gh.ug.smartservice.datastructures.queue.CircularQueue;
import gh.ug.smartservice.datastructures.queue.CustomDeque;
import gh.ug.smartservice.datastructures.queue.LinkedQueue;
import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.model.BookRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SchedulingEngine {

    /** Derived from team member index numbers (see project plan). */
    private static final int PRIORITY_WEIGHT_MULTIPLIER = 7;

    private final BookRequestDAO bookRequestDAO;

    public SchedulingEngine(BookRequestDAO bookRequestDAO) {
        this.bookRequestDAO = bookRequestDAO;
    }

    /** FIFO: oldest submitted request handled first. */
    public List<BookRequest> dispatchFifo() {
        List<BookRequest> pending = pendingRequestsSortedBySubmission();
        LinkedQueue<BookRequest> queue = new LinkedQueue<>();
        for (BookRequest request : pending) {
            queue.enqueue(request);
        }
        List<BookRequest> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            order.add(queue.dequeue());
        }
        return order;
    }

    /** Urgency-weighted priority dispatch: highest urgency (x weight) handled first. */
    public List<BookRequest> dispatchByUrgency() {
        BinaryHeapPriorityQueue<BookRequest> heap = new BinaryHeapPriorityQueue<>();
        for (BookRequest request : pendingRequestsSortedBySubmission()) {
            int priority = -(request.getUrgency() * PRIORITY_WEIGHT_MULTIPLIER);
            heap.insert(request, priority);
        }
        List<BookRequest> order = new ArrayList<>();
        while (!heap.isEmpty()) {
            order.add(heap.extractMin());
        }
        return order;
    }

    /** Fixed-capacity shift dispatch: only `capacity` requests are handled per shift, FIFO within a shift. */
    public List<BookRequest> dispatchByShiftCapacity(int capacity) {
        List<BookRequest> pending = pendingRequestsSortedBySubmission();
        CircularQueue<BookRequest> shiftQueue = new CircularQueue<>(capacity);
        List<BookRequest> order = new ArrayList<>();
        for (BookRequest request : pending) {
            if (shiftQueue.isFull()) {
                order.add(shiftQueue.dequeue());
            }
            shiftQueue.enqueue(request);
        }
        while (!shiftQueue.isEmpty()) {
            order.add(shiftQueue.dequeue());
        }
        return order;
    }

    /** Urgent walk-ins jump to the front; routine requests join the back (FIFO otherwise). */
    public List<BookRequest> dispatchWithUrgentInterrupts(int urgentThreshold) {
        List<BookRequest> pending = pendingRequestsSortedBySubmission();
        CustomDeque<BookRequest> deque = new CustomDeque<>();
        for (BookRequest request : pending) {
            if (request.getUrgency() >= urgentThreshold) {
                deque.addFront(request);
            } else {
                deque.addRear(request);
            }
        }
        List<BookRequest> order = new ArrayList<>();
        while (!deque.isEmpty()) {
            order.add(deque.removeFront());
        }
        return order;
    }

    private List<BookRequest> pendingRequestsSortedBySubmission() {
        List<BookRequest> pending = new ArrayList<>();
        for (BookRequest request : bookRequestDAO.findAll()) {
            if (!"COMPLETED".equalsIgnoreCase(request.getStatus())
                    && !"CANCELLED".equalsIgnoreCase(request.getStatus())) {
                pending.add(request);
            }
        }
        pending.sort(Comparator.comparing(BookRequest::getTimeSubmitted));
        return pending;
    }
}
