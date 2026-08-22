package gh.ug.smartservice.datastructures.hash;

import gh.ug.smartservice.datastructures.api.SimpleSet;

public class CustomSet<T> implements SimpleSet<T> {

    private static final Object PRESENT = new Object();

    private final CustomHashTable<T, Object> table = new CustomHashTable<>();

    @Override
    public boolean add(T item) {
        return table.put(item, PRESENT) == null;
    }

    @Override
    public boolean remove(T item) {
        return table.remove(item) != null;
    }

    @Override
    public boolean contains(T item) {
        return table.containsKey(item);
    }

    @Override
    public int size() {
        return table.size();
    }
}
