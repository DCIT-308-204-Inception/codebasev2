package gh.ug.smartservice.algorithms.sort;

public final class MergeSort {

    private MergeSort() {
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
        int mid = low + (high - low) / 2;
        sort(array, low, mid);
        sort(array, mid + 1, high);
        merge(array, low, mid, high);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void merge(T[] array, int low, int mid, int high) {
        Object[] left = new Object[mid - low + 1];
        Object[] right = new Object[high - mid];
        System.arraycopy(array, low, left, 0, left.length);
        System.arraycopy(array, mid + 1, right, 0, right.length);

        int i = 0, j = 0, k = low;
        while (i < left.length && j < right.length) {
            if (((T) left[i]).compareTo((T) right[j]) <= 0) {
                array[k++] = (T) left[i++];
            } else {
                array[k++] = (T) right[j++];
            }
        }
        while (i < left.length) {
            array[k++] = (T) left[i++];
        }
        while (j < right.length) {
            array[k++] = (T) right[j++];
        }
    }
}
