package gh.ug.smartservice.experiments;

import gh.ug.smartservice.algorithms.sort.InsertionSort;
import gh.ug.smartservice.algorithms.sort.MergeSort;
import gh.ug.smartservice.algorithms.sort.QuickSort;
import gh.ug.smartservice.algorithms.sort.SelectionSort;
import gh.ug.smartservice.model.AlgorithmRun;
import gh.ug.smartservice.util.Stopwatch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class SortExperiment {

    private SortExperiment() {
    }

    public static List<AlgorithmRun> run(int[] inputSizes, int trialsPerSize) {
        List<AlgorithmRun> results = new ArrayList<>();
        Random random = new Random(9808);

        for (int size : inputSizes) {
            for (int trial = 0; trial < trialsPerSize; trial++) {
                Integer[] data = randomArray(size, random);
                results.add(timeSort("SelectionSort", data.clone(), size, SelectionSort::sort));
                results.add(timeSort("InsertionSort", data.clone(), size, InsertionSort::sort));
                results.add(timeSort("MergeSort", data.clone(), size, MergeSort::sort));
                results.add(timeSort("QuickSort", data.clone(), size, QuickSort::sort));
            }
        }
        return results;
    }

    private static AlgorithmRun timeSort(String name, Integer[] data, int size, java.util.function.Consumer<Integer[]> sortFn) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        sortFn.accept(data);
        long elapsedNs = stopwatch.stop();
        return new AlgorithmRun(0, name, size, elapsedNs, 0, LocalDateTime.now());
    }

    private static Integer[] randomArray(int size, Random random) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size * 10 + 1);
        }
        return array;
    }
}
