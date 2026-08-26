package gh.ug.smartservice.algorithms.search;

public final class LinearSearch {

    private LinearSearch() {
    }

    public static <T extends Comparable<T>> int search(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].compareTo(target) == 0) {
                return i;
            }
        }
        return -1;
    }
}
