public class Tests {
    static int passed = 0;
    static int failed = 0;
    public static void main(String[] args) {
        testArray();
        testList();
        testHeap();
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }
    static void testArray() {
        DynamicArray<Integer> a = new DynamicArray<>();
        check(a.size() == 0);
        a.add(10);
        check(a.get(0) == 10);
        a.add(20);
        a.add(30);
        check(a.size() == 3);
        a.add(1, 15);
        check(a.get(1) == 15);
        check(a.contains(20));
        a.add(20);
        check(a.contains(20));
        check(a.remove(0) == 10);
        check(a.size() == 4);
        expectError(() -> a.get(-1));
        expectError(() -> a.get(a.size()));
        DynamicArray<Integer> large = new DynamicArray<>();
        for (int i = 0; i < 100000; i++) large.add(i);
        check(large.size() == 100000);
        check(large.get(99999) == 99999);
    }
    static void testList() {
        LinkedList<Integer> l = new LinkedList<>();
        check(l.size() == 0);
        l.add(10);
        check(l.get(0) == 10);
        l.add(20);
        l.add(30);
        check(l.size() == 3);
        l.add(1, 15);
        check(l.get(1) == 15);
        check(l.contains(20));
        l.add(20);
        check(l.contains(20));
        check(l.remove(0) == 10);
        check(l.size() == 4);
        expectError(() -> l.get(-1));
        expectError(() -> l.get(l.size()));
        LinkedList<Integer> large = new LinkedList<>();
        for (int i = 0; i < 100000; i++) large.add(i);
        check(large.size() == 100000);
        check(large.get(99999) == 99999);
    }
    static void testHeap() {
        MinHeap h = new MinHeap();
        check(h.isEmpty());
        expectHeapError(() -> h.peekMin());
        expectHeapError(() -> h.extractMin());
        h.insert(5);
        check(h.peekMin() == 5);
        h.insert(3);
        h.insert(8);
        h.insert(1);
        h.insert(3);
        check(h.peekMin() == 1);
        check(h.isValidHeap());
        int previous = Integer.MIN_VALUE;
        while (!h.isEmpty()) {
            int x = h.extractMin();
            check(x >= previous);
            previous = x;
            check(h.isValidHeap());
        }
        MinHeap large = new MinHeap();
        for (int i = 100000; i >= 1; i--) large.insert(i);
        check(large.peekMin() == 1);
        check(large.isValidHeap());
    }
    static void check(boolean condition) {
        if (condition) passed++;
        else failed++;
    }
    static void expectError(Runnable r) {
        try {
            r.run();
            failed++;
        } catch (IndexOutOfBoundsException e) {
            passed++;
        }
    }
    static void expectHeapError(Runnable r) {
        try {
            r.run();
            failed++;
        } catch (IllegalStateException e) {
            passed++;
        }
    }
}