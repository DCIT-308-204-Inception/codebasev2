package gh.ug.smartservice.datastructures.hash;

import gh.ug.smartservice.datastructures.api.SimpleMap;

public class CustomHashTable<K, V> implements SimpleMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private static final class Entry<K, V> {
        final K key;
        V value;
        final int hash;
        Entry<K, V> next;

        Entry(K key, V value, int hash, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private int threshold;

    @SuppressWarnings("unchecked")
    public CustomHashTable(int initialCapacity) {
        table = (Entry<K, V>[]) new Entry[initialCapacity];
        threshold = (int) (initialCapacity * LOAD_FACTOR);
    }

    public CustomHashTable() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && keysEqual(key, e.key)) {
                V old = e.value;
                e.value = value;
                return old;
            }
        }
        table[index] = new Entry<>(key, value, hash, table[index]);
        if (++size >= threshold) {
            resize(table.length * 2);
        }
        return null;
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && keysEqual(key, e.key)) {
                return e.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        Entry<K, V> prev = null;
        Entry<K, V> e = table[index];
        while (e != null) {
            if (e.hash == hash && keysEqual(key, e.key)) {
                if (prev == null) {
                    table[index] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return e.value;
            }
            prev = e;
            e = e.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (Entry<K, V> e = table[index]; e != null; e = e.next) {
            if (e.hash == hash && keysEqual(key, e.key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    private static boolean keysEqual(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    private static int hash(Object key) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    private static int indexFor(int hash, int length) {
        return (hash & 0x7fffffff) % length;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newCapacity];
        for (Entry<K, V> head : table) {
            Entry<K, V> e = head;
            while (e != null) {
                Entry<K, V> next = e.next;
                int index = indexFor(e.hash, newCapacity);
                e.next = newTable[index];
                newTable[index] = e;
                e = next;
            }
        }
        table = newTable;
        threshold = (int) (newCapacity * LOAD_FACTOR);
    }
}
