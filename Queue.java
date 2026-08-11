public class Queue {

    private ServiceRequest[] requests;
    private int front;
    private int rear;
    private int size;

    public Queue(int capacity) {
        requests = new ServiceRequest[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    // Add a request
    public void enqueue(ServiceRequest request) {

        if (isFull()) {
            System.out.println("Queue is full.");
            return;
        }

        rear++;
        requests[rear] = request;
        size++;

        System.out.println("Request " +
                request.getRequestId() +
                " added to queue.");
    }

    // Remove the oldest request
    public ServiceRequest dequeue() {

        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return null;
        }

        ServiceRequest request = requests[front];

        requests[front] = null;
        front++;
        size--;

        return request;
    }

    // View the first request
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
        return rear == requests.length - 1;
    }

    public int size() {
        return size;
    }

    public void display() {

        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        System.out.println("\n--- Service Request Queue ---");

        for (int i = front; i <= rear; i++) {
            System.out.println(
                    "Request ID: " +
                    requests[i].getRequestId() +
                    " | Category: " +
                    requests[i].getCategory() +
                    " | Urgency: " +
                    requests[i].getUrgency() +
                    " | Status: " +
                    requests[i].getStatus()
            );
        }
    }
}