package gh.ug.smartservice.experiments;

import gh.ug.smartservice.datastructures.hash.CustomHashTable;
import gh.ug.smartservice.model.AlgorithmRun;
import gh.ug.smartservice.util.Stopwatch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class HashExperiment {

    private HashExperiment() {
    }

    public static List<AlgorithmRun> run(int[] inputSizes, int trialsPerSize) {
        List<AlgorithmRun> results = new ArrayList<>();
        Random random = new Random(42);

        for (int size : inputSizes) {
            for (int trial = 0; trial < trialsPerSize; trial++) {
                Integer[] keys = new Integer[size];
                for (int i = 0; i < size; i++) {
                    keys[i] = random.nextInt(size * 10 + 1);
                }

                CustomHashTable<Integer, Integer> table = new CustomHashTable<>();
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.start();
                for (Integer key : keys) {
                    table.put(key, key);
                }
                long putNs = stopwatch.stop();
                results.add(new AlgorithmRun(0, "CustomHashTable.put", size, putNs, 0, LocalDateTime.now()));

                stopwatch.start();
                for (Integer key : keys) {
                    table.get(key);
                }
                long getNs = stopwatch.stop();
                results.add(new AlgorithmRun(0, "CustomHashTable.get", size, getNs, 0, LocalDateTime.now()));
            }
        }
        return results;
    }
}
