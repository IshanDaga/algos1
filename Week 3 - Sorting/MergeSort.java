/**
 * Name         : MergeSort
 * Date         : 21/7/21
 * @author      : Ishan Daga
 * Description  : Divide array into two halve, recursively sort each half. 
 *                Merge two halves.
 * 
 * Sub problem -> merging : to merge two sorted halves a[lo] to a[mid], a[mid+1] to a[hi]
 * maintain 3 itterators -> i, j, k described below
 * compare j, k values @ index & move element to i (auxillary)
 * increment j or k depending on what was moved. 
 * If one array is exhuasted, increment other pointer.
 * 
 * This implementation has the following tweaks:
 * 1) stop sorting when already sorted 
 * 2) switch role of aux[] and a[] with every call to save time to copy
 * Saves time, not space.
 */

public class MergeSort {   
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // assertions are for testing only. remove on deployment.
        assert isSorted(a, lo, mid); // lower half
        assert isSorted(a, mid+1, hi); // upper half

        // k -> final sorted iterator
        int i = lo, j = mid + 1; // i -> iter 1 for aux[lower half]
        for (int k = lo; k <= hi; k++) { // j -> iter 2 for aux[upper half]
            if (i > mid)                   aux[k] = a[j++]; // if lower half is exhusted 
            else if (j > hi)               aux[k] = a[i++]; // if upper half is exhausted
            else if (less(a[j], a[i]))     aux[k] = a[j++]; // j is lesser than i
            else                           aux[k] = a[i++]; // i is less than j
        }
        assert isSorted(a, lo, hi);
    }
    
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi<=lo) return;
        int mid = lo + (hi-lo)/2;
        sort(aux, a, lo, mid); //  the role of a and aux is switched between this call and the merge call
        sort(aux, a, mid+1, hi);
        if (!less(a[mid+1], a[mid])) return; // is the biggest item in the first half less than the smallest item in the 2nd half -> if yes, then sorted.
        merge(a, aux, lo, mid, hi); // switch occurs here/
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length-1);
    }

    private static boolean isSorted(Comparable[] a, int lower, int upper) {
        for (int i = lower; i < upper; i++) {
            if (a[i].compareTo(a[i+1]) > 0) return false;
        }
        return true;
    }

    private static boolean less(Comparable a, Comparable b) {
        return (a.compareTo(b) <= 0); //  a is less than b
    }

}
