package gh.ug.smartservice.algorithms.optimisation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KnapsackDPTest {

    @Test
    void picksTheHighestValueCombinationWithinCapacity() {
        // classic textbook instance: optimal is items at index 1 and 2 (weight 20+30=50, value 100+120=220)
        int[] weights = {10, 20, 30};
        int[] values = {60, 100, 120};

        KnapsackDP.Result result = KnapsackDP.solve(weights, values, 50);

        assertEquals(220, result.totalValue());
        assertEquals(2, result.selectedIndices().size());
        assertTrue(result.selectedIndices().contains(1));
        assertTrue(result.selectedIndices().contains(2));
    }

    @Test
    void zeroCapacitySelectsNothing() {
        int[] weights = {5, 10};
        int[] values = {10, 20};
        KnapsackDP.Result result = KnapsackDP.solve(weights, values, 0);
        assertEquals(0, result.totalValue());
        assertTrue(result.selectedIndices().isEmpty());
    }

    @Test
    void emptyItemListSelectsNothing() {
        KnapsackDP.Result result = KnapsackDP.solve(new int[0], new int[0], 100);
        assertEquals(0, result.totalValue());
        assertTrue(result.selectedIndices().isEmpty());
    }

    @Test
    void itemHeavierThanCapacityIsExcluded() {
        int[] weights = {1000};
        int[] values = {5000};
        KnapsackDP.Result result = KnapsackDP.solve(weights, values, 680);
        assertEquals(0, result.totalValue());
        assertTrue(result.selectedIndices().isEmpty());
    }
}
