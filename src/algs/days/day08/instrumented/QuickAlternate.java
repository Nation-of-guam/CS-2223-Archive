package algs.days.day08.instrumented;

import edu.princeton.cs.algs4.StdIn;


/**
 *  The <tt>Quick</tt> class provides static methods for sorting an
 *  array and selecting the ith smallest element in an array using quicksort.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  Provides an alternate partition method to use. 
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class QuickAlternate extends QuickOriginal {
	
	public QuickAlternate(int[] a) {
		super(a);
	}

	// partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
	// and return the index j. Alternate partitioning method.
	@Override
	protected int partition(int[] a, int lo, int hi) {
		// choose right-most element as the 'pivot'

		// all values <= pivot are moved to the front of array and pivot inserted
		// just after them.
		int store = lo;
		for (int i = lo; i <= hi; i++) {
			if (less(a[i], a[hi])) {
				exch(store, i);
				store++;
			}
		}
		
		exch(store, hi);
		return store;
	}


	/**
	 * Reads in a sequence of strings from standard input; quicksorts them; 
	 * and prints them to standard output in ascending order. 
	 * Shuffles the array and then prints the strings again to
	 * standard output, but this time, using the select method.
	 */
	public static void main(String[] args) {
		int[] a = StdIn.readAllInts();
		QuickAlternate qs = new QuickAlternate(a);
		qs.sort();
		qs.show();
	}

}

