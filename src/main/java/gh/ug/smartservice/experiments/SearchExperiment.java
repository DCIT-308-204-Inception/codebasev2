package gh.ug.smartservice.experiments;

import gh.ug.smartservice.algorithms.search.BinarySearch;
import gh.ug.smartservice.algorithms.search.LinearSearch;
import gh.ug.smartservice.algorithms.sort.MergeSort;
import gh.ug.smartservice.model.AlgorithmRun;
import gh.ug.smartservice.util.Stopwatch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SearchExperiment {

    private SearchExperiment() {
    }

    public static List<AlgorithmRun> run(int[] inputSizes, int trialsPerSize) {
        List<AlgorithmRun> results = new ArrayList<>();
        Random random = new Random(42);

        for (int size : inputSizes) {
            for (int trial = 0; trial < trialsPerSize; trial++) {
                Integer[] sorted = randomArray(size, random);
                MergeSort.sort(sorted);
                Integer target = sorted[random.nextInt(size)];

                Stopwatch stopwatch = new Stopwatch();
                stopwatch.start();
                LinearSearch.search(sorted, target);
                long linearNs = stopwatch.stop();
                results.add(new AlgorithmRun(0, "LinearSearch", size, linearNs, 0, LocalDateTime.now()));

                stopwatch.start();
                BinarySearch.search(sorted, target);
                long binaryNs = stopwatch.stop();
                results.add(new AlgorithmRun(0, "BinarySearch", size, binaryNs, 0, LocalDateTime.now()));
            }
        }
        return results;
    }

    private static Integer[] randomArray(int size, Random random) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size * 10 + 1);
        }
        return array;
    }
}
