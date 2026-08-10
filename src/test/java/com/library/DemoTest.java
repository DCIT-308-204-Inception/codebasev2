package com.library;

import com.library.datastructures.*;
import java.util.Iterator;

public class DemoTest {
    public static void main(String[] args) {
        testHashMap();
        System.out.println();
        testHashSet();
    }

    static void testHashMap() {
        System.out.println("=== HashMap Tests ===");

        Map<String, Integer> map = new HashMap<>();
        System.out.println("Empty: " + map.isEmpty() + " | Size: " + map.size());

        map.put("Alice", 25);
        map.put("Bob", 30);
        map.put("Charlie", 35);
        map.put(null, 99);
        System.out.println("After puts: " + map);
        System.out.println("Size: " + map.size());

        System.out.println("get('Bob'): " + map.get("Bob"));
        System.out.println("get(null): " + map.get(null));
        System.out.println("get('Dave'): " + map.get("Dave"));

        System.out.println("containsKey('Alice'): " + map.containsKey("Alice"));
        System.out.println("containsKey('Eve'): " + map.containsKey("Eve"));
        System.out.println("containsValue(30): " + map.containsValue(30));
        System.out.println("containsValue(100): " + map.containsValue(100));

        map.put("Alice", 26);
        System.out.println("After update Alice: " + map);

        System.out.println("Removed 'Charlie': " + map.remove("Charlie"));
        System.out.println("After remove: " + map);

        System.out.println("keySet: " + map.keySet());
        System.out.println("values: " + map.values());

        map.put("Dave", 30);
        System.out.println("After adding Dave=30 (duplicate value): values=" + map.values() + " | size=" + map.size());
        System.out.println("values() preserves duplicates: " + (map.values().size() == 4));

        Set<Entry<String, Integer>> entries = map.entrySet();
        System.out.println("entrySet: " + entries);

        Map<String, Integer> other = new HashMap<>();
        other.put("Eve", 40);
        other.put("Frank", 45);
        map.putAll(other);
        System.out.println("After putAll: " + map);

        map.clear();
        System.out.println("After clear - Empty: " + map.isEmpty());
    }

    static void testHashSet() {
        System.out.println("=== HashSet Tests ===");

        Set<String> set = new HashSet<>();
        System.out.println("Empty: " + set.isEmpty() + " | Size: " + set.size());

        set.add("apple");
        set.add("banana");
        set.add("cherry");
        set.add(null);
        System.out.println("After adds: " + set);
        System.out.println("Size: " + set.size());

        System.out.println("contains('banana'): " + set.contains("banana"));
        System.out.println("contains('grape'): " + set.contains("grape"));
        System.out.println("contains(null): " + set.contains(null));

        System.out.println("add('apple') again: " + set.add("apple"));

        System.out.println("remove('cherry'): " + set.remove("cherry"));
        System.out.println("After remove: " + set);

        System.out.print("Iterating: ");
        for (String s : set) {
            System.out.print(s + " ");
        }
        System.out.println();

        Iterator<String> it = set.iterator();
        if (it.hasNext()) {
            it.next();
            it.remove();
            System.out.println("After iterator remove: " + set);
        }

        Set<String> other = new HashSet<>();
        other.add("banana");
        other.add("date");
        other.add("elderberry");
        System.out.println("Other set: " + other);

        set.addAll(other);
        System.out.println("After addAll: " + set);

        set.retainAll(other);
        System.out.println("After retainAll(other): " + set);

        set.clear();
        System.out.println("After clear - Empty: " + set.isEmpty());
    }
}
