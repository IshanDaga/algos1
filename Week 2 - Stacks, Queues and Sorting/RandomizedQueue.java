/********************************************
 * @author: Ishan Daga 27/6/21
 *
 * Array Implementation of Randomized Queue
 *
 * A randomized queue is similar to a stack or queue, 
 * except that the item removed is chosen uniformly at 
 * random among items in the data structure.
 * 
 * Algos - 1 : Week 2
 * 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;
    private int dataSz;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 1;
        dataSz = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return (dataSz == 0);
    }

    // return the number of items on the randomized queue
    public int size() {
        return dataSz;
    }

    private void resize(int newSz) {
        Item[] newItems = (Item[]) new Object[newSz];
        for (int i = 0; i < dataSz; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
        size = newSz;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Bad data, cannot insert null type");
        }
        if (dataSz == size) {
            resize(dataSz*2);
        }
        items[dataSz++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (dataSz == 0) {
            throw new NoSuchElementException();
        }
        int rnd = StdRandom.uniform(dataSz);
        Item toRet = items[rnd];
        items[rnd] = items[dataSz-1];
        items[dataSz-1] = null;
        dataSz--;
        if (dataSz <= size/4 && dataSz != 0) {
            resize(dataSz);
        }
        return toRet;
    }   

    private class RandIt implements Iterator<Item> {

        private Item[] copyItems;
        private int itSize;
        RandIt() {
            itSize = dataSz;
            copyItems = (Item[]) new Object[itSize];
            for (int i = 0; i < dataSz; i++) {
                copyItems[i] = items[i];
            }
        }
        public boolean hasNext() {
            return (itSize > 0);
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (itSize == 0) {
               throw new NoSuchElementException();
            }
            int rnd = StdRandom.uniform(itSize);
            Item toRet = copyItems[rnd];
            copyItems[rnd] = copyItems[itSize-1];
            copyItems[itSize-1] = null;
            itSize--;
            return toRet;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (dataSz == 0) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(dataSz)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new RandIt(); }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randQ = new RandomizedQueue<>();
        randQ.enqueue("Hello");
        randQ.enqueue("world");
        randQ.enqueue("What");
        randQ.enqueue("a");
        randQ.enqueue("time");
        randQ.enqueue("to");
        randQ.enqueue("be");
        randQ.enqueue("alive");
        randQ.enqueue("!");
        for (String a: randQ) {
            System.out.print(a+" ");
        }
        for (int i = 0; i < randQ.size(); i++) {
            System.out.println(randQ.dequeue());
        }
    }

}