package gh.ug.smartservice.algorithms.optimisation;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GreedyAssignmentTest {

    @Test
    void assignsEachTaskToItsBestAvailableWorker() {
        Map<String, Integer> values = Map.of(
            "T1-A", 10, "T1-B", 1,
            "T2-A", 1, "T2-B", 10);

        List<GreedyAssignment.Assignment> result = GreedyAssignment.assign(
            List.of("T1", "T2"), List.of("A", "B"),
            (taskId, workerId) -> values.get(taskId + "-" + workerId));

        assertEquals("A", result.get(0).workerId());
        assertEquals("B", result.get(1).workerId());
        assertEquals(20, GreedyAssignment.totalValue(result));
    }

    @Test
    void fewerWorkersThanTasksLeavesSomeTasksUnassigned() {
        List<GreedyAssignment.Assignment> result = GreedyAssignment.assign(
            List.of("T1", "T2"), List.of("A"), (taskId, workerId) -> 5);
        assertEquals(1, result.size());
    }

    @Test
    void noWorkersProducesNoAssignments() {
        List<GreedyAssignment.Assignment> result = GreedyAssignment.assign(
            List.of("T1"), List.of(), (taskId, workerId) -> 5);
        assertTrue(result.isEmpty());
    }

    /**
     * Counterexample: greedy processes T1 first and grabs worker A because
     * 10 > 9 — a marginal +1 gain over choosing B for T1. But that traps T2
     * with worker B, worth only 1 instead of the 10 it would have been
     * worth with A. Greedy total = 11. The optimal assignment (T1->B, T2->A)
     * scores 19 — proving the greedy, priority-order choice is not optimal.
     */
    @Test
    void greedyFailsToFindTheOptimalTotalValue() {
        Map<String, Integer> values = Map.of(
            "T1-A", 10, "T1-B", 9,
            "T2-A", 10, "T2-B", 1);

        List<GreedyAssignment.Assignment> greedyResult = GreedyAssignment.assign(
            List.of("T1", "T2"), List.of("A", "B"),
            (taskId, workerId) -> values.get(taskId + "-" + workerId));

        int greedyTotal = GreedyAssignment.totalValue(greedyResult);
        int optimalTotal = values.get("T1-B") + values.get("T2-A");

        assertEquals(11, greedyTotal);
        assertEquals(19, optimalTotal);
        assertTrue(optimalTotal > greedyTotal, "greedy should be strictly worse than the optimal assignment here");
    }
}
