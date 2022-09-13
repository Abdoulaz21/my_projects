package linkedList;

public class LinkedList<T extends Comparable<T>> {

    private Node head;
    private int size;

    private final class Node {
        T value;
        Node next;

        Node(T data, Node next) {
            this.value = data;
            this.next = next;
        }
    }

    /**
     * Initializes the list
     **/
    public LinkedList() {
        this.size = 0;
        this.head = null;
    }

    /**
     * Insert the specified element into the list.
     * The elements must be sorted by ascending order.
     * null elements should be at the end of the list.
     * @param e Inserted element
     **/
    public void insert(T e) {
        Node current = head;
        if (size == 0) {
            head = new Node(e, null);
            size++;
        }
        else if (current.value.compareTo(e) > 0) {
            head = new Node(e, head);
            size++;
        }
        else {
            while (current.next != null) {
                if (current.next.value.compareTo(e) > 0) {
                    current.next = new Node(e, current.next);
                    size++;
                    return;
                }
                current = current.next;
            }
            current.next = new Node(e, null);
            size++;
        }
    }

    /**
     * Returns the n-th element in this list.
     * @throws IndexOutOfBoundsException if there is no element at this
     * index.
     * @param i Index
     * @return Element at given index
     **/
    public T get(int i) {
        if (size == 0 || size <= i) {
            throw new IndexOutOfBoundsException("index too high");
        }
        var current = head;
        var index = 0;
        while (index < i) {
            current = current.next;
            index++;
        }

        return current.value;
    }

    /**
     * Removes the first occurrence of the specified element in this list.
     * @param e Element to remove
     * @return True if the element has been successfully removed
     **/
    public boolean remove(T e) {
        if (size == 0)
            return false;

        Node current = head;
        if (current.value.compareTo(e) == 0) {
            if (current.next == null) {
                this.clear();
            } else {
                head = current.next;
                this.size -= 1;
            }
            return true;
        }

        Node prev = current;
        while (prev.next != null && prev.next.value.compareTo(e) != 0) {
            prev = prev.next;
        }

        if (prev.next == null) {
            return false;
        }

        prev.next = prev.next.next;
        this.size -= 1;

        return true;
    }

    /**
     * Returns the size of this list.
     * @return Number of elements in the list
     **/
    public int size() {
        return this.size;
    }

    /**
     * Clears the list.
     **/
    public void clear() {
        this.head = null;
        this.size = 0;
    }
}
