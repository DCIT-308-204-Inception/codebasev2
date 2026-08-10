package com.library.datastructures;

public class HashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    Entry<K, V>[] table;
    private int size;
    private final float loadFactor;
    private int threshold;

    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity, float loadFactor) {
        this.loadFactor = loadFactor;
        this.table = (Entry<K, V>[]) new Entry[initialCapacity];
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    @SuppressWarnings("unchecked")
    public HashMap(int initialCapacity) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.table = (Entry<K, V>[]) new Entry[initialCapacity];
        this.threshold = (int) (initialCapacity * loadFactor);
    }

    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.table = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        this.threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR);
    }

    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (key == e.key || (key != null && key.equals(e.key)))) {
                return e.setValue(value);
            }
        }

        addEntry(hash, key, value, index);
        return null;
    }

    @Override
    public V get(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (key == e.key || (key != null && key.equals(e.key)))) {
                return e.value;
            }
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        Entry<K, V> prev = table[index];
        Entry<K, V> e = prev;

        while (e != null) {
            Entry<K, V> next = e.next;
            if (e.hash == hash && (key == e.key || (key != null && key.equals(e.key)))) {
                size--;
                if (prev == e) {
                    table[index] = next;
                } else {
                    prev.next = next;
                }
                return e.value;
            }
            prev = e;
            e = next;
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);

        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && (key == e.key || (key != null && key.equals(e.key)))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> e = table[i]; e != null; e = e.next) {
                if (value == e.value || (value != null && value.equals(e.value))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> other) {
        for (Entry<? extends K, ? extends V> e : other.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> e = table[i]; e != null; e = e.next) {
                keys.add(e.key);
            }
        }
        return keys;
    }

    @Override
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> e = table[i]; e != null; e = e.next) {
                values.add(e.value);
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        HashSet<Entry<K, V>> entries = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> e = table[i]; e != null; e = e.next) {
                entries.add(e);
            }
        }
        return entries;
    }

    @Override
    public String toString() {
        if (size == 0) return "{}";
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (int i = 0; i < table.length; i++) {
            for (Entry<K, V> e = table[i]; e != null; e = e.next) {
                if (!first) sb.append(", ");
                sb.append(e.key).append("=").append(e.value);
                first = false;
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private static int hash(Object key) {
        if (key == null) return 0;
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    private static int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    private void addEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K, V> entry = new Entry<>(key, value, hash, table[bucketIndex]);
        table[bucketIndex] = entry;
        if (size++ >= threshold) {
            resize(2 * table.length);
        }
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] oldTable = table;
        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newCapacity];
        threshold = (int) (newCapacity * loadFactor);

        for (int i = 0; i < oldTable.length; i++) {
            Entry<K, V> e = oldTable[i];
            while (e != null) {
                Entry<K, V> next = e.next;
                int index = indexFor(e.hash, newCapacity);
                e.next = newTable[index];
                newTable[index] = e;
                e = next;
            }
        }
        table = newTable;
    }
}
