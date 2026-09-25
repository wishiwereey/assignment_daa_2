public class MinHeap {
    private int[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private long comparisons;
    public MinHeap() {
        heap = new int[DEFAULT_CAPACITY];
    }
    public void insert(int value) {
        ensureCapacity();
        heap[size] = value;
        int current = size;
        size++;
        while (current > 0) {
            int parent = (current - 1) / 2;
            comparisons++;
            if (heap[parent] <= heap[current]) {
                break;
            }
            swap(parent, current);
            current = parent;
        }
    }
    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[0];
    }
    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return min;
    }
    private void siftDown(int index) {
        int current = index;
        while (true) {
            int left = 2 * current + 1;
            int right = 2 * current + 2;
            int smallest = current;
            if (left < size) {
                comparisons++;
                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }
            if (right < size) {
                comparisons++;
                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }
            if (smallest == current) {
                break;
            }
            swap(current, smallest);
            current = smallest;
        }
    }
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    private void ensureCapacity() {
        if (size == heap.length) {
            int[] newHeap = new int[heap.length * 2];
            for (int i = 0; i < size; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
    }
    public boolean isValidHeap() {
        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < size && heap[i] > heap[left]) {
                return false;
            }
            if (right < size && heap[i] > heap[right]) {
                return false;
            }
        }
        return true;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public void resetMetrics() {
        comparisons = 0;
    }
    public long getComparisons() {
        return comparisons;
    }
}