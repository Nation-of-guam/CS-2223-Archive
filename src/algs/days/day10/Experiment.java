package algs.days.day10;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// Proper MergeSort from Sedewick, 4ed with annotations showing sub-tasks
public class Experiment {

	static int numComparisons = 0;
	static Comparable aux[];

	public static void sort(Comparable[] a) {
		numComparisons = 0;
		aux = new Comparable[a.length];
		sort (a, 0, a.length-1);
	}

	// recursive helper function
	static void sort (Comparable[] a, int lo, int hi) {
		if (hi <= lo) return;

		int mid = lo + (hi - lo)/2;

		sort(a, lo, mid);

		sort(a, mid+1, hi);
		merge(a, lo, mid, hi);
	}

	// merge sorted results a[lo..mid] with a[mid+1..hi] back into a
	static void merge (Comparable[] a, int lo, int mid, int hi) {
		int left = lo;     // starting index into left sorted sub-array
		int right = mid+1;  // starting index into right sorted sub-array

		// copy a[lo..hi] into aux[lo..hi]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}

		// now comes the merge. Something you might simulate with flashcards
		// drawn from two stack piles. This is the heart of mergesort. 
		for (int k = lo; k <= hi; k++) {
			if       (left > mid)                   { a[k] = aux[right++]; }
			else if  (right > hi)                   { a[k] = aux[left++];  }
			else if  (less(aux[right], aux[left]))  { a[k] = aux[right++]; numComparisons++; }
			else                                    { a[k] = aux[left++];  numComparisons++; }
		}
	}


	/***************************************************************************
	 *  Helper sorting functions.
	 ***************************************************************************/

	// is v < w ?
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	// print array to standard output a[lo..hi]
	private static void show(Comparable[] a, int lo, int hi) {
		for (int i = lo; i <= hi; i++) {
			StdOut.print (a[i] + " ");
		}
	}

	
	static int[] bestMin = new int[]{0, 0, 1, 2, 4, 5, 7, 9, 12, 13, 15, 17, 20, 23, 27, 29, 33, 0, 0, 0, };

	// https://oeis.org/A001855
	static int[] bestMax = new int[]{0, 0, 1, 3, 5, 8, 11, 14, 17, 21, 25, 29, 33, 37, 41, 45, 49, 0, 0, 0, };

	/**
	 * Reads in a sequence of strings from standard input; selection sorts them; 
	 * and prints them to standard output in ascending order. 
	 */
	public static void main(String[] args) {
		System.out.println("N\tMIN\tMAX");
		for (int n = 1; n < 17; n++) {
			Integer[] A = new Integer[n];
			for (int i = 0; i < n; i++) { A[i] = i; }
			int min, max;
			bestMin[n] = min = 99999;
			bestMax[n] = max = 0;
			for (int t = 0; t < 5000000; t++) {
				StdRandom.shuffle(A);
				sort(A);
				if (numComparisons > max) { max = numComparisons; }
				if (numComparisons < min) { min = numComparisons; }
			}

			System.out.println(n + "\t" + min + "\t" + max);
			if (min < bestMin[n]) { bestMin[n] = min; }
			if (max > bestMax[n]) { bestMax[n] = max; }
		}
		
		System.out.print("static int[] bestMin = new int[]{");
		for (int b : bestMin) { System.out.print(b + ", "); }
		System.out.println("};");

		System.out.print("static int[] bestMax = new int[]{");
		for (int b : bestMax) { System.out.print(b + ", "); }
		System.out.println("};");

		
	}
}