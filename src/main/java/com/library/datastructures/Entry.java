package com.library.datastructures;

public class Entry<K, V> {
    K key;
    V value;
    int hash;
    Entry<K, V> next;

    public Entry(K key, V value, int hash, Entry<K, V> next) {
        this.key = key;
        this.value = value;
        this.hash = hash;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry<?, ?> e = (Entry<?, ?>) o;
        return (key == null ? e.key == null : key.equals(e.key))
            && (value == null ? e.value == null : value.equals(e.value));
    }

    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode())
             ^ (value == null ? 0 : value.hashCode());
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
