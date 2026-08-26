package gh.ug.smartservice.algorithms.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchAlgorithmsTest {

    @Test
    void linearSearchFindsPresentElement() {
        Integer[] array = {5, 3, 8, 1, 9};
        assertEquals(2, LinearSearch.search(array, 8));
    }

    @Test
    void linearSearchReturnsMinusOneForMissingElement() {
        Integer[] array = {5, 3, 8, 1, 9};
        assertEquals(-1, LinearSearch.search(array, 100));
    }

    @Test
    void linearSearchOnEmptyArray() {
        Integer[] array = {};
        assertEquals(-1, LinearSearch.search(array, 1));
    }

    @Test
    void binarySearchFindsPresentElement() {
        Integer[] sorted = {1, 3, 5, 7, 9, 11};
        assertEquals(3, BinarySearch.search(sorted, 7));
        assertEquals(0, BinarySearch.search(sorted, 1));
        assertEquals(5, BinarySearch.search(sorted, 11));
    }

    @Test
    void binarySearchReturnsMinusOneForMissingElement() {
        Integer[] sorted = {1, 3, 5, 7, 9, 11};
        assertEquals(-1, BinarySearch.search(sorted, 4));
    }

    @Test
    void binarySearchOnEmptyArray() {
        Integer[] sorted = {};
        assertEquals(-1, BinarySearch.search(sorted, 1));
    }
}
