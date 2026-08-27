package gh.ug.smartservice.algorithms.optimisation;

import java.util.ArrayList;
import java.util.List;

/**
 * Greedily assigns each task (in the given priority order) to its
 * highest-value still-available worker. This is NOT guaranteed to find the
 * assignment with the highest total value: locking in the best worker for
 * an early task can force a much worse pairing for a later task. See
 * GreedyAssignmentTest for a worked counterexample.
 */
public final class GreedyAssignment {

    private GreedyAssignment() {
    }

    public record Assignment(String taskId, String workerId, int value) {
    }

    public interface ValueFunction {
        int valueOf(String taskId, String workerId);
    }

    public static List<Assignment> assign(List<String> taskIdsInPriorityOrder, List<String> workerIds,
                                           ValueFunction valueFunction) {
        List<String> available = new ArrayList<>(workerIds);
        List<Assignment> result = new ArrayList<>();

        for (String taskId : taskIdsInPriorityOrder) {
            String bestWorker = null;
            int bestValue = Integer.MIN_VALUE;
            for (String workerId : available) {
                int value = valueFunction.valueOf(taskId, workerId);
                if (value > bestValue) {
                    bestValue = value;
                    bestWorker = workerId;
                }
            }
            if (bestWorker != null) {
                available.remove(bestWorker);
                result.add(new Assignment(taskId, bestWorker, bestValue));
            }
        }
        return result;
    }

    public static int totalValue(List<Assignment> assignments) {
        int total = 0;
        for (Assignment assignment : assignments) {
            total += assignment.value();
        }
        return total;
    }
}
