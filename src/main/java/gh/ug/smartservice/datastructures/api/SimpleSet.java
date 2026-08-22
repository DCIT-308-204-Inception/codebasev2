package gh.ug.smartservice.datastructures.api;

public interface SimpleSet<T> {
    boolean add(T item);
    boolean remove(T item);
    boolean contains(T item);
    int size();
}
