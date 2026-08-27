package gh.ug.smartservice.engine;

import gh.ug.smartservice.algorithms.optimisation.GreedyAssignment;
import gh.ug.smartservice.algorithms.optimisation.KnapsackDP;
import gh.ug.smartservice.datastructures.hash.CustomMap;
import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.db.StaffDAO;
import gh.ug.smartservice.model.BookRequest;
import gh.ug.smartservice.model.Staff;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OptimisationEngine {

    /** Derived from team member index numbers (see project plan). */
    private static final int PRIORITY_WEIGHT_MULTIPLIER = 7;
    private static final int BUDGET_CONSTRAINT = 680;
    private static final int BASE_HANDLING_COST = 10;

    private final BookRequestDAO bookRequestDAO;
    private final StaffDAO staffDAO;

    public OptimisationEngine(BookRequestDAO bookRequestDAO, StaffDAO staffDAO) {
        this.bookRequestDAO = bookRequestDAO;
        this.staffDAO = staffDAO;
    }

    /** Greedily assigns pending requests (highest priority first) to available staff. */
    public List<GreedyAssignment.Assignment> assignPendingRequestsToStaff() {
        List<BookRequest> pending = pendingRequestsByPriority();
        List<Staff> staff = staffDAO.findAll();

        CustomMap<String, BookRequest> requestsById = new CustomMap<>();
        List<String> taskIds = new ArrayList<>();
        for (BookRequest request : pending) {
            String id = String.valueOf(request.getRequestId());
            requestsById.put(id, request);
            taskIds.add(id);
        }

        CustomMap<String, Staff> staffById = new CustomMap<>();
        List<String> workerIds = new ArrayList<>();
        for (Staff member : staff) {
            String id = String.valueOf(member.getStaffId());
            staffById.put(id, member);
            workerIds.add(id);
        }

        return GreedyAssignment.assign(taskIds, workerIds, (taskId, workerId) -> {
            BookRequest request = requestsById.get(taskId);
            Staff member = staffById.get(workerId);
            if (member.getCapacity() <= 0) {
                return 0;
            }
            int proximityPenalty = Math.abs(member.getHomeShelfId() - request.getSourceShelfId());
            return request.getUrgency() * PRIORITY_WEIGHT_MULTIPLIER - proximityPenalty;
        });
    }

    /** Selects the highest-value subset of pending requests that fits the derived staff-time budget. */
    public List<BookRequest> selectRequestsWithinBudget() {
        List<BookRequest> pending = pendingRequestsByPriority();
        int n = pending.size();
        int[] weights = new int[n];
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            BookRequest request = pending.get(i);
            weights[i] = BASE_HANDLING_COST + request.getUrgency() * 2;
            values[i] = request.getUrgency() * PRIORITY_WEIGHT_MULTIPLIER;
        }

        KnapsackDP.Result result = KnapsackDP.solve(weights, values, BUDGET_CONSTRAINT);

        List<BookRequest> selected = new ArrayList<>();
        for (int index : result.selectedIndices()) {
            selected.add(pending.get(index));
        }
        return selected;
    }

    private List<BookRequest> pendingRequestsByPriority() {
        List<BookRequest> pending = new ArrayList<>();
        for (BookRequest request : bookRequestDAO.findAll()) {
            if (!"COMPLETED".equalsIgnoreCase(request.getStatus())
                    && !"CANCELLED".equalsIgnoreCase(request.getStatus())) {
                pending.add(request);
            }
        }
        pending.sort(Comparator.comparingInt(BookRequest::getUrgency).reversed());
        return pending;
    }
}
