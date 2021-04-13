package algs.days.day12;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Proper HeapSort from Sedgewick, 4ed
// observe that it doesn't use swim operation -- can you see why?
public class HeapSort {
	static int numExch = 0;
	static int numLess = 0;
	static int numBuildLess = 0;
	static boolean verbose = true;

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * @param a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		int N = a.length;
		numLess = 0;
		numBuildLess = 0;
		numExch = 0;
		
		// create heap
		for (int k = N/2; k >= 1; k--) {
			sink(a, k, N);
			if (verbose) { StdOut.printf("sink %d ", k); show(a); }
		}
		numBuildLess = numLess;
		
		// modify array in place, moving largest item to rightmost spot
		while (N > 1) {
			exch(a, 1, N--);
			sink(a, 1, N);
			if (verbose) { StdOut.printf("sort %d ", N); show(a); }
		}
	}

	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/

	static void sink(Comparable[] a, int k, int N) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(a, j, j+1)) j++;
			if (!less(a, k, j)) break;
			exch(a, k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for comparisons and swaps.
	 * Indices are "off-by-one" to support 1-based indexing.
	 ***************************************************************************/
	static boolean less(Comparable[] a, int i, int j) {
		numLess++;
		return a[i-1].compareTo(a[j-1]) < 0;
	}

	static void exch(Object[] a, int i, int j) {
		numExch++;
		Object swap = a[i-1];
		a[i-1] = a[j-1];
		a[j-1] = swap;
	}

	static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.print(a[i] + " ");
		}
		StdOut.println();
	}

	/**
	 * Reads in a sequence of strings from standard input; heapsorts them; 
	 * and prints them to standard output in ascending order. 
	 */
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		sort(a);
		show(a);
	}
}