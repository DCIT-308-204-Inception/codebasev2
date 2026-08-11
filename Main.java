public class Main {

    public static void main(String[] args) {

        Queue normalQueue = new Queue(5);

        CircularQueue circularQueue = new CircularQueue(5);

        ServiceRequest request1 = new ServiceRequest(
                101,
                "Main Library",
                "Science Library",
                "Book Transfer",
                "High",
                "2026-08-11 08:00",
                "2026-08-11 12:00",
                "Pending"
        );

        ServiceRequest request2 = new ServiceRequest(
                102,
                "Science Library",
                "Main Library",
                "Book Return",
                "Medium",
                "2026-08-11 08:15",
                "2026-08-11 14:00",
                "Pending"
        );

        ServiceRequest request3 = new ServiceRequest(
                103,
                "Law Library",
                "Main Library",
                "Book Request",
                "Low",
                "2026-08-11 08:30",
                "2026-08-11 16:00",
                "Pending"
        );

        System.out.println("===== NORMAL QUEUE =====");

        normalQueue.enqueue(request1);
        normalQueue.enqueue(request2);
        normalQueue.enqueue(request3);

        normalQueue.display();

        System.out.println("\nProcessing request:");

        ServiceRequest processed = normalQueue.dequeue();

        if (processed != null) {
            System.out.println(processed);
        }

        System.out.println("\nQueue after processing:");
        normalQueue.display();


        System.out.println("\n\n===== CIRCULAR QUEUE =====");

        circularQueue.enqueue(request1);
        circularQueue.enqueue(request2);
        circularQueue.enqueue(request3);

        circularQueue.display();

        System.out.println("\nRemoving first request:");

        ServiceRequest removed = circularQueue.dequeue();

        if (removed != null) {
            System.out.println(
                    "Processed Request ID: " +
                    removed.getRequestId()
            );
        }

        System.out.println("\nAdding another request:");

        ServiceRequest request4 = new ServiceRequest(
                104,
                "Business Library",
                "Main Library",
                "Book Search",
                "High",
                "2026-08-11 09:00",
                "2026-08-11 11:00",
                "Pending"
        );

        circularQueue.enqueue(request4);

        circularQueue.display();
    }
}