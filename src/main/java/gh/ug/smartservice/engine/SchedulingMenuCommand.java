package gh.ug.smartservice.engine;

import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.model.BookRequest;
import gh.ug.smartservice.ui.MenuCommand;

import java.util.List;

public class SchedulingMenuCommand implements MenuCommand {

    private static final int SHIFT_CAPACITY = 10;
    private static final int URGENT_THRESHOLD = 8;

    @Override
    public String label() {
        return "Dispatch pending book requests (FIFO/urgency/shift/interrupt)";
    }

    @Override
    public void execute() {
        SchedulingEngine engine = new SchedulingEngine(new BookRequestDAO());

        printTop("FIFO order", engine.dispatchFifo());
        printTop("Urgency-weighted order", engine.dispatchByUrgency());
        printTop("Shift-capacity order (capacity " + SHIFT_CAPACITY + ")", engine.dispatchByShiftCapacity(SHIFT_CAPACITY));
        printTop("Urgent-interrupt order (threshold " + URGENT_THRESHOLD + ")",
            engine.dispatchWithUrgentInterrupts(URGENT_THRESHOLD));
    }

    private void printTop(String title, List<BookRequest> order) {
        System.out.println("\n--- " + title + " (" + order.size() + " requests) ---");
        int shown = Math.min(5, order.size());
        for (int i = 0; i < shown; i++) {
            BookRequest request = order.get(i);
            System.out.printf("  %d. request %d | urgency %d | submitted %s%n",
                i + 1, request.getRequestId(), request.getUrgency(), request.getTimeSubmitted());
        }
    }
}
