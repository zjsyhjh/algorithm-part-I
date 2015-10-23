package algorithm.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by iHge2k on 15/10/21.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] que;
    private int size;

    public RandomizedQueue() {
        size = 0;
        que = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    private void resize(int N) {
        Item[] tmp = (Item[]) new Object[N];
        for (int i = 0; i < size; ++i) {
            tmp[i] = que[i];
        }
        que = tmp;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (size == que.length) {
            resize(2 * que.length);
        }
        que[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        Item item = que[index];
        if (index != size - 1) que[index] = que[size - 1];
        que[size - 1] = null;
        size--;
        if (size > 0 && size == que.length / 4) {
            resize(que.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int index = StdRandom.uniform(size);
        return que[index];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements  Iterator<Item> {

        private Item[] tmp = (Item[]) new Object[que.length];
        private int tmpSize = size;

        public ArrayIterator() {
            for (int i = 0; i < size; ++i) {
                tmp[i] = que[i];
            }
        }

        @Override
        public boolean hasNext() {
            return (tmpSize != 0);
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
            int index = StdRandom.uniform(tmpSize);
            Item item = tmp[index];
            if (index != tmpSize - 1) {
                tmp[index] = tmp[tmpSize - 1];
            }
            tmp[tmpSize - 1] = null;
            tmpSize--;
            return item;
        }
    }

    public static void main(String[] args) {

    }
}
