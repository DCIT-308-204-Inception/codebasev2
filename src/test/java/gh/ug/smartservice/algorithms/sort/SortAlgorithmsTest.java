package gh.ug.smartservice.algorithms.sort;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SortAlgorithmsTest {

    private void assertSortsCorrectly(Consumer<Integer[]> sort) {
        Integer[] normal = {5, 3, 8, 1, 4, 7, 9};
        sort.accept(normal);
        assertArrayEquals(new Integer[]{1, 3, 4, 5, 7, 8, 9}, normal);

        Integer[] withDuplicates = {2, 1, 2, 1, 3};
        sort.accept(withDuplicates);
        assertArrayEquals(new Integer[]{1, 1, 2, 2, 3}, withDuplicates);

        Integer[] empty = {};
        assertDoesNotThrow(() -> sort.accept(empty));

        Integer[] single = {42};
        sort.accept(single);
        assertArrayEquals(new Integer[]{42}, single);
    }

    @Test
    void selectionSortSortsCorrectly() {
        assertSortsCorrectly(SelectionSort::sort);
    }

    @Test
    void insertionSortSortsCorrectly() {
        assertSortsCorrectly(InsertionSort::sort);
    }

    @Test
    void mergeSortSortsCorrectly() {
        assertSortsCorrectly(MergeSort::sort);
    }

    @Test
    void quickSortSortsCorrectly() {
        assertSortsCorrectly(QuickSort::sort);
    }
}
