package algs.days.day13;

import algs.days.day08.Quick;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// This copy of QuickSort is placed here so we have access to partition

/**
 *  The <tt>Quick</tt> class provides static methods for sorting an
 *  array and selecting the ith smallest element in an array using quicksort.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class QuickSelect {

	static int numLess;
	static int numExch;
	
	/**
	 * Return the item which is ranked 'rank' in the array.
	 * 
	 * 0 would be smallest
	 * n-1 would be max
	 * n/2 would be median for an odd-numbered list.
	 * 
	 * Note: Array may be modified to become a permutation of original contents.
	 * @param    a   Array of values, not necessarily sorted
	 * @param    nth the desired rank (0 to a.length-1)
	 */
	static Comparable quickSelect(Comparable[]a, int nth) {
		numLess = numExch = 0;
		return quickSelect(a, nth, 0, a.length-1);
	}

	/**
	 * Return the nth value in the a[lo..hi] range using partition.
	 * 
	 * @param a         Array of values, not necessarily sorted
	 * @param nth       the desired rank (0 to a.length-1)
	 * @param lo        a[lo..] lo range
	 * @param hi        a[..hi] hi range
	 * @return the value which is nth in the original collect a[lo..hi] in sorted order.
	 */
	static Comparable quickSelect(Comparable[]a, int nth, int lo, int hi) {
		if (lo == hi) { return a[lo]; }  // if only one, return this one as only choice.
		int j = partition (a, lo, hi);

		if (j == nth) {
			return a[j];    // our partition was lucky and selected the nth one. Done!
		} else if (j < nth) {
			return quickSelect(a, nth, j+1, hi);   // go high
		} else {
			return quickSelect(a, nth, lo, j-1);   // go to left
		}
	}

	// partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
	// and return the index j.
	static int partition(Comparable[] a, int lo, int hi) {
		int i = lo;
		int j = hi + 1;
		Comparable v = a[lo];
		while (true) { 

			// find item on lo to swap
			while (less(a[++i], v))
				if (i == hi) break;

			// find item on hi to swap
			while (less(v, a[--j]))
				if (j == lo) break;      // redundant since a[lo] acts as sentinel

			// check if pointers cross
			if (i >= j) break;

			exch(a, i, j);
		}

		// put partitioning item v at a[j]
		exch(a, lo, j);

		// now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
		return j;
	}



	/***************************************************************************
	 *  Helper sorting functions.
	 ***************************************************************************/

	// is v < w ?
	static boolean less(Comparable v, Comparable w) {
		numLess++;
		return v.compareTo(w) < 0;
	}

	// exchange a[i] and a[j]
	static void exch(Object[] a, int i, int j) {
		numExch++;
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/**
	 * Reads in a sequence of strings from standard input; quicksorts them; 
	 * and prints them to standard output in ascending order. 
	 * Shuffles the array and then prints the strings again to
	 * standard output, but this time, using the select method.
	 */
	public static void main(String[] args) {
		for (int n = 128; n <= 1048576; n*= 2) {
			Integer[] a = new Integer[n];
			Integer[] copy = new Integer[n];
			for (int i = 0; i < copy.length; i++) { 
				a[i] = copy[i] = StdRandom.uniform(0, n);  // up to but not including n
			}
			Quick.sort(copy);

			// run number of trials and make sure works for all ranks
			int max = 0;
			for (int t = 0; t < 200; t++) {
				int rank = StdRandom.uniform(0, a.length);
				if (!QuickSelect.quickSelect(a, rank).equals(copy[rank])) {
					System.err.println("MISSED!");
					System.exit(1);
				}
				if (numExch + numLess > max) {
					max = numExch + numLess;
				}
					
				
				StdRandom.shuffle(a);
			}
			
			StdOut.printf("%d\t%d\t%.2f\n", n, max, (1.0*max/n));
		}
	}
}

