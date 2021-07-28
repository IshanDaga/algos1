/**
 * Name: Maximal Priority Queue
 * @author: Ishan Daga
 * Description: A resizable array implementation of a Max ordered priority queue
 *              using a binary heap.
 * 
 * Based on content from Princeton's Algos-1 course on Coursera
 * 
 */ 
public class MaxPQ<Key extends Comparable<Key>>{
    private Key[] pq; // priority queue data, 1-indexed array [0th position ignored]
    private int N;    // current size of queue

    public MaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity+1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key key) {
        pq[N++] = key;
        swim(N);
    }

    public Key delMax() {
        Key max = pq[1]; // store max
        exch(1, N--); // exhange max val and last key, remove max val from end of tree
        sink(1); // sink new top val as it will surely be out of place
        pq[N+1] = null; // prevent loitering of new val
        return max;
    }

    private void swim(int k) { // compare key to parent and move up if parent is smaller
        while (k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) { // compare key to children and move down to larger child if key is smaller
        while(2*k <= N) {
            int j = 2*k; // all children are at 2k, 2k+1
            if (j<N && less(j, j+1)) j++; // is current child lesser than other child : if yes go to other child
            if (!less(k, j)) break; // if not lesser, then stop
            exch(k, j); // change postions
            k = j; // change to new key index
        }
    }

    private boolean less(int i, int j) { // true if i is less than j
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) { // exchange the values at specified indecies
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
}