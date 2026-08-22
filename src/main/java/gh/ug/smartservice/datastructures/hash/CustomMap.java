package gh.ug.smartservice.datastructures.hash;

import gh.ug.smartservice.datastructures.api.SimpleMap;

public class CustomMap<K, V> implements SimpleMap<K, V> {

    private final CustomHashTable<K, V> table = new CustomHashTable<>();

    @Override
    public V put(K key, V value) {
        return table.put(key, value);
    }

    @Override
    public V get(K key) {
        return table.get(key);
    }

    @Override
    public V remove(K key) {
        return table.remove(key);
    }

    @Override
    public boolean containsKey(K key) {
        return table.containsKey(key);
    }

    @Override
    public int size() {
        return table.size();
    }
}
