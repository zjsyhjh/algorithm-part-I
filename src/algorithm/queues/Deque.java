package algorithm.queues;

import java.util.Iterator;

/**
 * Created by iHge2k on 15/10/21.
 */

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first;
    private Node last;

    public Deque() {
        this.size = 0;
        this.first = this.last = null;
    }

    public boolean isEmpty() {
        return (first == null && last == null);
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {

        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        if (isEmpty()) {
            Node node = new Node(item, null, null);
            first = last = node;
        } else {
            Node node = new Node(item, null, first);
            first.pre = node;
            first = node;
        }
        size++;
    }

    public void addLast(Item item) {

        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        if (isEmpty()) {
            Node node = new Node(item, null, null);
            first = last = node;
        } else {
            Node node = new Node(item, last, null);
            last.next = node;
            last = node;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node node = first;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.pre = null;
        }
        size--;
        return node.item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node node = last;
        last = last.pre;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
        return node.item;
    }

    private class Node {
        private Item item;
        private Node pre;
        private Node next;

        public Node(Item item, Node pre, Node next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    public static void main(String[] args) {

    }
}
