/********************************************
 * @author: Ishan Daga 29/6/21
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
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k;
        int n = 0;
        try {
            k = Integer.parseInt(args[0]); // required number of inputs
            RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();

            while (!StdIn.isEmpty()) { // read all inputs
                n++;
                String in = StdIn.readString();
                if (n > k && StdRandom.uniform(n) < k)  { // with probability k/i take new item and remove random (1/k) old item
                    randomQueue.dequeue(); // remove old item
                } else if (n > k) { continue; } // with probability 1 - k/i, ignore new item
                randomQueue.enqueue(in); // add new item
            }
            for (int i = 0; i < k; i++) {
                System.out.println(randomQueue.dequeue());
            }
        } catch (NumberFormatException nfe) {
            System.out.println("The argument must be an integer.");
        }
    }
}
