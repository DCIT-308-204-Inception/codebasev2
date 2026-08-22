package gh.ug.smartservice.datastructures.api;

public interface SimpleMap<K, V> {
    V put(K key, V value);
    V get(K key);
    V remove(K key);
    boolean containsKey(K key);
    int size();
}
