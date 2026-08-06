public class ArrayBackedListTest {

    // ── NORMAL CASES ──────────────────────────────────

    // Test 1: Insert and get
    public static void test_insertAndGet() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("Balme Library Book");
        assert list.get(0).equals("Balme Library Book") : "FAIL test_insertAndGet";
        System.out.println("PASS test_insertAndGet");
    }

    // Test 2: Insert multiple and check size
    public static void test_sizeAfterInserts() {
        ArrayBackedList<Integer> list = new ArrayBackedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        assert list.size() == 3 : "FAIL test_sizeAfterInserts";
        System.out.println("PASS test_sizeAfterInserts");
    }

    // Test 3: Set replaces value
    public static void test_set() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("Old Title");
        list.set(0, "New Title");
        assert list.get(0).equals("New Title") : "FAIL test_set";
        System.out.println("PASS test_set");
    }

    // Test 4: Remove returns correct element
    public static void test_removeReturnsElement() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("Book A");
        list.insert("Book B");
        list.insert("Book C");
        String removed = list.remove(1);
        assert removed.equals("Book B") : "FAIL test_removeReturnsElement";
        System.out.println("PASS test_removeReturnsElement");
    }

    // Test 5: Remove shifts elements left correctly
    public static void test_removeShiftsLeft() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("A");
        list.insert("B");
        list.insert("C");
        list.remove(0);
        assert list.get(0).equals("B") : "FAIL test_removeShiftsLeft";
        assert list.get(1).equals("C") : "FAIL test_removeShiftsLeft";
        System.out.println("PASS test_removeShiftsLeft");
    }

    // Test 6: Size decreases after remove
    public static void test_sizeAfterRemove() {
        ArrayBackedList<Integer> list = new ArrayBackedList<>();
        list.insert(10);
        list.insert(20);
        list.remove(0);
        assert list.size() == 1 : "FAIL test_sizeAfterRemove";
        System.out.println("PASS test_sizeAfterRemove");
    }

    // Test 7: InsertAt shifts elements right
    public static void test_insertAt() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("A");
        list.insert("C");
        list.insertAt(1, "B");
        assert list.get(1).equals("B") : "FAIL test_insertAt";
        assert list.get(2).equals("C") : "FAIL test_insertAt";
        System.out.println("PASS test_insertAt");
    }

    // Test 8: Contains returns true for existing element
    public static void test_containsTrue() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("DSA Cormen");
        assert list.contains("DSA Cormen") : "FAIL test_containsTrue";
        System.out.println("PASS test_containsTrue");
    }

    // Test 9: Contains returns false for missing element
    public static void test_containsFalse() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("DSA Cormen");
        assert !list.contains("Missing Book") : "FAIL test_containsFalse";
        System.out.println("PASS test_containsFalse");
    }

    // Test 10: toString format correct
    public static void test_toString() {
        ArrayBackedList<Integer> list = new ArrayBackedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        assert list.toString().equals("[1, 2, 3]") : "FAIL test_toString";
        System.out.println("PASS test_toString");
    }

    // ── BOUNDARY CASES ─────────────────────────────────

    // Test 11: Insert exactly fills capacity, then resize
    public static void test_resizeTriggered() {
        ArrayBackedList<Integer> list = new ArrayBackedList<>(4);
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.insert(4);
        assert list.capacity() == 4 : "FAIL test_resizeTriggered (before)";
        list.insert(5); // triggers resize
        assert list.capacity() == 8 : "FAIL test_resizeTriggered (after)";
        System.out.println("PASS test_resizeTriggered");
    }

    // Test 12: Data preserved after resize
    public static void test_dataPreservedAfterResize() {
        ArrayBackedList<Integer> list = new ArrayBackedList<>(2);
        list.insert(10);
        list.insert(20);
        list.insert(30); // triggers resize
        assert list.get(0) == 10 : "FAIL test_dataPreservedAfterResize";
        assert list.get(1) == 20 : "FAIL test_dataPreservedAfterResize";
        assert list.get(2) == 30 : "FAIL test_dataPreservedAfterResize";
        System.out.println("PASS test_dataPreservedAfterResize");
    }

    // Test 13: Insert at index 0 (front)
    public static void test_insertAtFront() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("B");
        list.insert("C");
        list.insertAt(0, "A");
        assert list.get(0).equals("A") : "FAIL test_insertAtFront";
        System.out.println("PASS test_insertAtFront");
    }

    // Test 14: Remove last element
    public static void test_removeLastElement() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("Only Book");
        list.remove(0);
        assert list.isEmpty() : "FAIL test_removeLastElement";
        System.out.println("PASS test_removeLastElement");
    }

    // Test 15: Get from single-element list
    public static void test_getSingleElement() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("Solo Book");
        assert list.get(0).equals("Solo Book") : "FAIL test_getSingleElement";
        System.out.println("PASS test_getSingleElement");
    }

    // Test 16: isEmpty on empty list
    public static void test_isEmptyTrue() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        assert list.isEmpty() : "FAIL test_isEmptyTrue";
        System.out.println("PASS test_isEmptyTrue");
    }

    // Test 17: isEmpty after insert
    public static void test_isEmptyFalse() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("Book");
        assert !list.isEmpty() : "FAIL test_isEmptyFalse";
        System.out.println("PASS test_isEmptyFalse");
    }

    // Test 18: clear empties list
    public static void test_clear() {
        ArrayBackedList<Integer> list = new ArrayBackedList<>();
        list.insert(1);
        list.insert(2);
        list.insert(3);
        list.clear();
        assert list.size() == 0 : "FAIL test_clear";
        assert list.isEmpty() : "FAIL test_clear";
        System.out.println("PASS test_clear");
    }

    // ── INVALID INPUT CASES ────────────────────────────

    // Test 19: get with negative index throws exception
    public static void test_getNegativeIndex() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("Book");
        try {
            list.get(-1);
            System.out.println("FAIL test_getNegativeIndex (no exception)");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS test_getNegativeIndex");
        }
    }

    // Test 20: get with index >= size throws exception
    public static void test_getOutOfBounds() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        list.insert("Book");
        try {
            list.get(5);
            System.out.println("FAIL test_getOutOfBounds (no exception)");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS test_getOutOfBounds");
        }
    }

    // Test 21: remove from empty list throws exception
    public static void test_removeFromEmpty() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        try {
            list.remove(0);
            System.out.println("FAIL test_removeFromEmpty (no exception)");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS test_removeFromEmpty");
        }
    }

    // Test 22: set on invalid index throws exception
    public static void test_setInvalidIndex() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        try {
            list.set(0, "Book");
            System.out.println("FAIL test_setInvalidIndex (no exception)");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS test_setInvalidIndex");
        }
    }

    // Test 23: constructor with zero capacity throws exception
    public static void test_constructorZeroCapacity() {
        try {
            new ArrayBackedList<>(0);
            System.out.println("FAIL test_constructorZeroCapacity (no exception)");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS test_constructorZeroCapacity");
        }
    }

    // Test 24: insertAt out of bounds throws exception
    public static void test_insertAtOutOfBounds() {
        ArrayBackedList<String> list = new ArrayBackedList<>();
        try {
            list.insertAt(5, "Book");
            System.out.println("FAIL test_insertAtOutOfBounds (no exception)");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS test_insertAtOutOfBounds");
        }
    }

    // ── RUN ALL ───────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("=== ArrayBackedList Unit Tests ===\n");
        test_insertAndGet();
        test_sizeAfterInserts();
        test_set();
        test_removeReturnsElement();
        test_removeShiftsLeft();
        test_sizeAfterRemove();
        test_insertAt();
        test_containsTrue();
        test_containsFalse();
        test_toString();
        test_resizeTriggered();
        test_dataPreservedAfterResize();
        test_insertAtFront();
        test_removeLastElement();
        test_getSingleElement();
        test_isEmptyTrue();
        test_isEmptyFalse();
        test_clear();
        test_getNegativeIndex();
        test_getOutOfBounds();
        test_removeFromEmpty();
        test_setInvalidIndex();
        test_constructorZeroCapacity();
        test_insertAtOutOfBounds();
        System.out.println("\n=== All tests complete ===");
    }
}