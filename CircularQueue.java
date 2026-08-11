public class CircularQueue {

    private ServiceRequest[] requests;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public CircularQueue(int capacity) {

        this.capacity = capacity;
        requests = new ServiceRequest[capacity];

        front = 0;
        rear = -1;
        size = 0;
    }

    // Add request
    public void enqueue(ServiceRequest request) {

        if (isFull()) {
            System.out.println("Circular queue is full.");
            return;
        }

        rear = (rear + 1) % capacity;

        requests[rear] = request;
        size++;

        System.out.println("Request " +
                request.getRequestId() +
                " added to circular queue.");
    }

    // Remove request
    public ServiceRequest dequeue() {

        if (isEmpty()) {
            System.out.println("Circular queue is empty.");
            return null;
        }

        ServiceRequest request = requests[front];

        requests[front] = null;

        front = (front + 1) % capacity;
        size--;

        return request;
    }

    // View first request
    public ServiceRequest peek() {

        if (isEmpty()) {
            return null;
        }

        return requests[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    public void display() {

        if (isEmpty()) {
            System.out.println("Circular queue is empty.");
            return;
        }

        System.out.println("\n--- Circular Queue ---");

        for (int i = 0; i < size; i++) {

            int index = (front + i) % capacity;

            System.out.println(
                    "Request ID: " +
                    requests[index].getRequestId() +
                    " | Category: " +
                    requests[index].getCategory() +
                    " | Urgency: " +
                    requests[index].getUrgency() +
                    " | Status: " +
                    requests[index].getStatus()
            );
        }
    }
}