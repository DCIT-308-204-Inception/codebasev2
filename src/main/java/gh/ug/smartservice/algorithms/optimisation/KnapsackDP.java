package gh.ug.smartservice.algorithms.optimisation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 0/1 knapsack via bottom-up tabulation, with reconstruction of the chosen items. */
public final class KnapsackDP {

    private KnapsackDP() {
    }

    public record Result(List<Integer> selectedIndices, int totalValue) {
    }

    public static Result solve(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] table = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                table[i][w] = table[i - 1][w];
                if (weights[i - 1] <= w) {
                    int candidate = table[i - 1][w - weights[i - 1]] + values[i - 1];
                    if (candidate > table[i][w]) {
                        table[i][w] = candidate;
                    }
                }
            }
        }

        List<Integer> selected = new ArrayList<>();
        int remainingCapacity = capacity;
        for (int i = n; i > 0; i--) {
            if (table[i][remainingCapacity] != table[i - 1][remainingCapacity]) {
                selected.add(i - 1);
                remainingCapacity -= weights[i - 1];
            }
        }
        Collections.reverse(selected);

        return new Result(selected, table[n][capacity]);
    }
}
