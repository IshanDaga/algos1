/********************************************
 * @author: Ishan Daga 27/6/21
 * 
 * Doubly linked list implementation of a Deque
 *
 * A double-ended queue or deque (pronounced “deck”) is a
 * generalization of a stack and a queue that supports
 * adding and removing items from either the front or the
 * back of the data structure.
 *
 * Algos - 1 : Week 2
 * 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("No null values allowed in deque");
        }
        Node newDat = new Node();
        newDat.item = item;
        newDat.next = first;
        newDat.prev = null;
        if (isEmpty()) {
            last = newDat;
        } else {
            first.prev = newDat;
        }
        first = newDat;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("No null values allowed in deque");
        }
        Node newDat = new Node();
        newDat.item = item;
        newDat.next = null;
        newDat.prev = last;
        if (isEmpty()) {
            first = newDat;
        } else {
            last.next = newDat;
        }
        last = newDat;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item fr = first.item;
        first = first.next;
        size--;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        return fr;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item removed = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return removed;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current;
        private ListIterator(Node start) { current = start; }
        public boolean hasNext() {
            return (current != null);
        }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item toRet = current.item;
            current = current.next;
            return toRet;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new ListIterator(first); }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("Hello");
        deque.addLast("World");
        deque.addLast("How");
        deque.addLast("goes");
        deque.addLast("It");
        for (String a: deque) {
            System.out.print(a+"  ");
        }
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
    }

}