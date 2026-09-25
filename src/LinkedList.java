public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    private long accesses;
    private long comparisons;
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) {
            this.data = data;
        }
    }
    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }
    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == size) {
            add(value);
            return;
        }
        Node<T> node = new Node<>(value);
        if (index == 0) {
            node.next = head;
            head = node;
            size++;
            return;
        }
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            accesses++;
        }
        node.next = current.next;
        current.next = node;
        size++;
    }
    public T remove(int index) {
        checkIndex(index);
        if (index == 0) {
            T value = head.data;
            head = head.next;
            size--;
            if (size == 0) {
                tail = null;
            }
            return value;
        }
        Node<T> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            accesses++;
        }
        Node<T> removed = current.next;
        current.next = removed.next;
        if (removed == tail) {
            tail = current;
        }
        size--;
        return removed.data;
    }
    public T get(int index) {
        checkIndex(index);
        Node<T> current = head;
        accesses++;
        for (int i = 0; i < index; i++) {
            current = current.next;
            accesses++;
        }
        return current.data;
    }
    public boolean contains(T value) {
        Node<T> current = head;
        while (current != null) {
            comparisons++;
            if (value == null) {
                if (current.data == null) {
                    return true;
                }
            } else if (value.equals(current.data)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    public int size() {
        return size;
    }
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
    public void resetMetrics() {
        accesses = 0;
        comparisons = 0;
    }
    public long getAccesses() {
        return accesses;
    }
    public long getComparisons() {
        return comparisons;
    }
}
