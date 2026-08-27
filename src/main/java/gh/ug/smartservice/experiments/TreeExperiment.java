package gh.ug.smartservice.experiments;

import gh.ug.smartservice.datastructures.tree.BTree;
import gh.ug.smartservice.datastructures.tree.BinarySearchTree;
import gh.ug.smartservice.datastructures.tree.RedBlackTree;
import gh.ug.smartservice.model.AlgorithmRun;
import gh.ug.smartservice.util.Stopwatch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class TreeExperiment {

    private static final int B_TREE_MIN_DEGREE = 3;

    private TreeExperiment() {
    }

    public static List<AlgorithmRun> run(int[] inputSizes, int trialsPerSize) {
        List<AlgorithmRun> results = new ArrayList<>();
        Random random = new Random(9808);

        for (int size : inputSizes) {
            for (int trial = 0; trial < trialsPerSize; trial++) {
                Integer[] keys = new Integer[size];
                for (int i = 0; i < size; i++) {
                    keys[i] = random.nextInt(size * 10 + 1);
                }

                BinarySearchTree<Integer> bst = new BinarySearchTree<>();
                results.add(timeTree("BinarySearchTree", size, keys, bst::insert, bst::contains));

                RedBlackTree<Integer> rbt = new RedBlackTree<>();
                results.add(timeTree("RedBlackTree", size, keys, rbt::insert, rbt::contains));

                BTree<Integer> btree = new BTree<>(B_TREE_MIN_DEGREE);
                results.add(timeTree("BTree", size, keys, btree::insert, btree::search));
            }
        }
        return results;
    }

    private static AlgorithmRun timeTree(String name, int size, Integer[] keys,
                                          java.util.function.Consumer<Integer> insert,
                                          java.util.function.Predicate<Integer> search) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        for (Integer key : keys) {
            insert.accept(key);
        }
        for (Integer key : keys) {
            search.test(key);
        }
        long elapsedNs = stopwatch.stop();
        return new AlgorithmRun(0, name, size, elapsedNs, 0, LocalDateTime.now());
    }
}
