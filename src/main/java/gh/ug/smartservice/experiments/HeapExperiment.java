package gh.ug.smartservice.experiments;

import gh.ug.smartservice.datastructures.heap.BinaryHeapPriorityQueue;
import gh.ug.smartservice.model.AlgorithmRun;
import gh.ug.smartservice.util.Stopwatch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class HeapExperiment {

    private HeapExperiment() {
    }

    public static List<AlgorithmRun> run(int[] inputSizes, int trialsPerSize) {
        List<AlgorithmRun> results = new ArrayList<>();
        Random random = new Random(9808);

        for (int size : inputSizes) {
            for (int trial = 0; trial < trialsPerSize; trial++) {
                int[] priorities = new int[size];
                for (int i = 0; i < size; i++) {
                    priorities[i] = random.nextInt(size * 10 + 1);
                }

                BinaryHeapPriorityQueue<Integer> heap = new BinaryHeapPriorityQueue<>();
                Stopwatch stopwatch = new Stopwatch();
                stopwatch.start();
                for (int priority : priorities) {
                    heap.insert(priority, priority);
                }
                long insertNs = stopwatch.stop();
                results.add(new AlgorithmRun(0, "BinaryHeapPriorityQueue.insert", size, insertNs, 0, LocalDateTime.now()));

                stopwatch.start();
                while (!heap.isEmpty()) {
                    heap.extractMin();
                }
                long extractNs = stopwatch.stop();
                results.add(new AlgorithmRun(0, "BinaryHeapPriorityQueue.extractMin", size, extractNs, 0, LocalDateTime.now()));
            }
        }
        return results;
    }
}
