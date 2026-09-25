import java.util.Random;
public class Benchmark {
    static int[] sizes = {100, 1000, 10000, 100000};
    static Random random = new Random(42);
    public static void main(String[] args) {
        randomAccess();
        search();
        insertionRemoval();
        heapTest();
    }
    static void randomAccess() {
        System.out.println("\n--- Random Access ---");
        for (int n : sizes) {
            long at = 0, lt = 0, aa = 0, la = 0;
            int[] indexes = new int[10000];
            for (int i = 0; i < 10000; i++) indexes[i] = random.nextInt(n);
            for (int r = 0; r < 5; r++) {
                DynamicArray<Integer> a = new DynamicArray<>();
                LinkedList<Integer> l = new LinkedList<>();
                for (int i = 0; i < n; i++) {
                    int x = random.nextInt(100000);
                    a.add(x);
                    l.add(x);
                }
                a.resetMetrics();
                long s = System.nanoTime();
                for (int x : indexes) a.get(x);
                at += System.nanoTime() - s;
                aa += a.getAccesses();
                l.resetMetrics();
                s = System.nanoTime();
                for (int x : indexes) l.get(x);
                lt += System.nanoTime() - s;
                la += l.getAccesses();
            }
            System.out.printf("n=%d Array %.3fms accesses=%d | List %.3fms accesses=%d%n",
                    n, at / 5e6, aa / 5, lt / 5e6, la / 5);
        }
    }
    static void search() {
        System.out.println("\n--- Search ---");
        for (int n : sizes) {
            long at = 0, lt = 0, ac = 0, lc = 0;
            int[] searches = new int[1000];
            for (int i = 0; i < 1000; i++) searches[i] = random.nextInt(100000);
            for (int r = 0; r < 5; r++) {
                DynamicArray<Integer> a = new DynamicArray<>();
                LinkedList<Integer> l = new LinkedList<>();
                for (int i = 0; i < n; i++) {
                    int x = random.nextInt(100000);
                    a.add(x);
                    l.add(x);
                }
                a.resetMetrics();
                long s = System.nanoTime();
                for (int x : searches) a.contains(x);
                at += System.nanoTime() - s;
                ac += a.getComparisons();
                l.resetMetrics();
                s = System.nanoTime();
                for (int x : searches) l.contains(x);
                lt += System.nanoTime() - s;
                lc += l.getComparisons();
            }
            System.out.printf("n=%d Array %.3fms comparisons=%d | List %.3fms comparisons=%d%n",
                    n, at / 5e6, ac / 5, lt / 5e6, lc / 5);
        }
    }
    static void insertionRemoval() {
        System.out.println("\n--- Insertion / Removal ---");
        for (int n : sizes) {
            testPosition(n, false);
            testPosition(n, true);
        }
    }
    static void testPosition(int n, boolean middle) {
        long ai = 0, li = 0, ar = 0, lr = 0;
        long am = 0, ia = 0, rm = 0, ra = 0;
        int index = middle ? n / 2 : 0;
        int removals = Math.min(1000, n);
        for (int r = 0; r < 5; r++) {
            DynamicArray<Integer> a = new DynamicArray<>();
            LinkedList<Integer> l = new LinkedList<>();

            for (int i = 0; i < n; i++) {
                a.add(i);
                l.add(i);
            }
            a.resetMetrics();
            long s = System.nanoTime();
            for (int i = 0; i < 1000; i++) a.add(index, i);
            ai += System.nanoTime() - s;
            am += a.getMovements();
            l.resetMetrics();
            s = System.nanoTime();
            for (int i = 0; i < 1000; i++) l.add(index, i);
            li += System.nanoTime() - s;
            ia += l.getAccesses();
            a = new DynamicArray<>();
            l = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                a.add(i);
                l.add(i);
            }
            a.resetMetrics();
            s = System.nanoTime();
            for (int i = 0; i < removals; i++) {
                int pos = middle ? a.size() / 2 : 0;
                a.remove(pos);
            }
            ar += System.nanoTime() - s;
            rm += a.getMovements();
            l.resetMetrics();
            s = System.nanoTime();
            for (int i = 0; i < removals; i++) {
                int pos = middle ? l.size() / 2 : 0;
                l.remove(pos);
            }
            lr += System.nanoTime() - s;
            ra += l.getAccesses();
        }
        String p = middle ? "middle" : "beginning";
        System.out.printf("n=%d %s insert: Array %.3fms movements=%d | List %.3fms accesses=%d%n",
                n, p, ai / 5e6, am / 5, li / 5e6, ia / 5);
        System.out.printf("n=%d %s remove: Array %.3fms movements=%d | List %.3fms accesses=%d%n",
                n, p, ar / 5e6, rm / 5, lr / 5e6, ra / 5);
    }
    static void heapTest() {
        System.out.println("\n--- Min Heap ---");
        for (int n : sizes) {
            long it = 0, et = 0, ic = 0, ec = 0;
            boolean sorted = true;
            for (int r = 0; r < 5; r++) {
                int[] values = new int[n];
                for (int i = 0; i < n; i++) values[i] = random.nextInt(100000);
                MinHeap h = new MinHeap();
                h.resetMetrics();
                long s = System.nanoTime();
                for (int x : values) h.insert(x);
                it += System.nanoTime() - s;
                ic += h.getComparisons();
                h.resetMetrics();
                int previous = Integer.MIN_VALUE;
                s = System.nanoTime();
                while (!h.isEmpty()) {
                    int x = h.extractMin();
                    if (x < previous) sorted = false;
                    previous = x;
                }
                et += System.nanoTime() - s;
                ec += h.getComparisons();
            }
            System.out.printf("n=%d Insert %.3fms comparisons=%d | Extract %.3fms comparisons=%d | sorted=%b%n",
                    n, it / 5e6, ic / 5, et / 5e6, ec / 5, sorted);
        }
    }
}