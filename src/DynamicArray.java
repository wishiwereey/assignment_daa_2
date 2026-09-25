public class DynamicArray<T> {
    private Object[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private long accesses;
    private long comparisons;
    private long movements;
    public DynamicArray() {
        data = new Object[DEFAULT_CAPACITY];
    }
    public void add(T value) {
        ensureCapacity();
        data[size++] = value;
    }
    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        ensureCapacity();
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            movements++;
        }
        data[index] = value;
        size++;
    }
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        checkIndex(index);
        T removed = (T) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            movements++;
        }
        data[size - 1] = null;
        size--;
        return removed;
    }
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        accesses++;
        return (T) data[index];
    }
    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            comparisons++;
            if (value == null) {
                if (data[i] == null) {
                    return true;
                }
            } else if (value.equals(data[i])) {
                return true;
            }
        }
        return false;
    }
    public int size() {
        return size;
    }
    private void ensureCapacity() {
        if (size == data.length) {
            Object[] newData = new Object[data.length * 2];
            for (int i = 0; i < size; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
    public void resetMetrics() {
        accesses = 0;
        comparisons = 0;
        movements = 0;
    }
    public long getAccesses() {
        return accesses;
    }
    public long getComparisons() {
        return comparisons;
    }
    public long getMovements() {
        return movements;
    }
}
