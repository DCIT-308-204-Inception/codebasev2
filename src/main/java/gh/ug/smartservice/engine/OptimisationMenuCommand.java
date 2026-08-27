package gh.ug.smartservice.engine;

import gh.ug.smartservice.algorithms.optimisation.GreedyAssignment;
import gh.ug.smartservice.db.BookRequestDAO;
import gh.ug.smartservice.db.StaffDAO;
import gh.ug.smartservice.model.BookRequest;
import gh.ug.smartservice.ui.MenuCommand;

import java.util.List;

public class OptimisationMenuCommand implements MenuCommand {

    @Override
    public String label() {
        return "Optimise staff assignment and request selection (greedy/DP)";
    }

    @Override
    public void execute() {
        OptimisationEngine engine = new OptimisationEngine(new BookRequestDAO(), new StaffDAO());

        List<GreedyAssignment.Assignment> assignments = engine.assignPendingRequestsToStaff();
        System.out.println("Greedy assignments: " + assignments.size()
            + ", total value " + GreedyAssignment.totalValue(assignments));
        assignments.stream().limit(5).forEach(a ->
            System.out.printf("  request %s -> staff %s (value %d)%n", a.taskId(), a.workerId(), a.value()));

        List<BookRequest> selected = engine.selectRequestsWithinBudget();
        System.out.println("\nRequests selected within budget: " + selected.size());
        selected.stream().limit(5).forEach(r ->
            System.out.printf("  request %d | urgency %d%n", r.getRequestId(), r.getUrgency()));
    }
}
