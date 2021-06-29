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
 *
 * The bonus point is to use only one RandomizedQueue object of maximum size at most k.
 *
 * The Reservoir sampling algorithms could solve the problem:
 *
 * Reservoir sampling is a family of randomized algorithms for randomly choosing a
 * sample of k items from a list S containing n items, where n is either a very
 * large or unknown number. Typically n is large enough that the list doesn't fit
 * into main memory .
 *
 * Keep the first k items in memory.
 * When the i-th item arrives (for i > k):
 *     - with probability k/i, keep the new item (discard an old one, selecting which to replace at random, each with chance 1/k)
 *     - with probability 1 - k/i, keep the old items (ignore the new one)
 *
 * Algos - 1 : Week 2
 *
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        int read = 0;
        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            read++;
            // reservoir sampling
            if (read > k && StdRandom.uniform(read) < k) { // if total read > required (k), with probability k/i remove old, add new item
                randomQueue.dequeue();
            } else if (read > k) { continue; } // ignores new input if total read strings is greater than k (required length)
            randomQueue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(randomQueue.dequeue());
        }
    }
}
