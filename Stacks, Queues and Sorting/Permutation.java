/********************************************
 * @author: Ishan Daga 27/6/21
 *
 * Permutation program
 *
 * takes an integer k as a command-line argument;
 * reads a sequence of strings from standard input
 * using StdIn.readString(); and prints exactly k
 * of them, uniformly at random.
 * Print each item from the sequence at most once
 *
 * Algos - 1 : Week 2
 *
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            randomQueue.enqueue(StdIn.readString());
        }
        for (String a: randomQueue) {
            StdOut.println(a);
        }
    }
}
