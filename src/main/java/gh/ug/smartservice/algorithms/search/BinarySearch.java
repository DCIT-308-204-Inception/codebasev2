package gh.ug.smartservice.algorithms.search;

public final class BinarySearch {

    private BinarySearch() {
    }

    /** array must already be sorted in ascending order. */
    public static <T extends Comparable<T>> int search(T[] array, T target) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = array[mid].compareTo(target);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
