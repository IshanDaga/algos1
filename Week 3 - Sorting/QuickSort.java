/*
* Name : QuickSort
* @author: Ishan Daga
* Date : 21/7/21
* Description : Implementation of Quick sort.
*
* Quick sort is based on partitioning the array around a randomly chosen pivot point.
* The values around this pivot follow the following schema in each iteration :
* 1) values to the left of the pivot are smaller than it
* 2) values to the right of the pivot are larger
* 3) the pivot is already in the correct position.
*
* Also includes a commented out version of the 3-way partitioning quicksort
* */

import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi+1;
        while (true) {
            while (less(a[++i], a[lo])) // find item on left to swap
                if (i == hi) break; // ensure we don't run off right end of array

            while (less(a[lo], a[--j]))
                if (j == lo) break; // ensure we don't run off left end of array

            if (i >= j) break; // check if pointers cross
            exch(a, i, j); // swap
        }
        exch(a, lo, j); // swap with partitioning item
        return j; // return index of item known to be in correct position
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a); // shuffle is needed for performance guarantee
        sort(a, 0, a.length-1);
    }
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return; // single element [do nothing for sub-array of size 1]
        int j = partition(a, lo, hi); //  pointer to in position element
        sort(a, lo, j-1); // left half done first
        sort(a, j+1, hi); // right half
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean less (Comparable a, Comparable b) {
        return (a.compareTo(b) <= 0);
    }

    /*
    // dijkstra's 3 way partitioning quicksort
    // keeps elements of equal keys together during partitions
    // partitions are -> a[lo] - a[lt-1], a[lt] - a[gt],  a[gt+1] - a[hi]
    // lt to gt is the equal partition. lo to ly-1 is the lesser than, gt+1 to hi is greater than


    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        Comparable v = a[lo]; // pivot element
        int i = lo;
        while (i <= gt) {
            int cmp = a[i].compareTo(v); // -ve if lesser than v, +ve if greater than v
            if (cmp < 0) exch(a, lt++, i++); // increment lt & i
            else if (cmp > 0) exch(a, i, gt--); // decrement gt
            else i++;
        }
        sort(a, lo, lt-1);
        sort(a, gt+1, hi)
    }


     */

}