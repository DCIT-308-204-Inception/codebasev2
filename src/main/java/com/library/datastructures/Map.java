package com.library.datastructures;

public interface Map<K, V> {
    V put(K key, V value);
    V get(Object key);
    V remove(Object key);
    boolean containsKey(Object key);
    boolean containsValue(Object value);
    int size();
    boolean isEmpty();
    void clear();
    void putAll(Map<? extends K, ? extends V> other);
    Set<K> keySet();
    ArrayList<V> values();
    Set<Entry<K, V>> entrySet();
}
