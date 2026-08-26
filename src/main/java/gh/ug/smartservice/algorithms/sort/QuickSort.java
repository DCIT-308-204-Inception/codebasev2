package gh.ug.smartservice.algorithms.sort;

public final class QuickSort {

    private QuickSort() {
    }

    public static <T extends Comparable<T>> void sort(T[] array) {
        if (array.length < 2) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    private static <T extends Comparable<T>> void sort(T[] array, int low, int high) {
        if (low >= high) {
            return;
        }
        int pivotIndex = partition(array, low, high);
        sort(array, low, pivotIndex - 1);
        sort(array, pivotIndex + 1, high);
    }

    private static <T extends Comparable<T>> int partition(T[] array, int low, int high) {
        T pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        T temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }
}
