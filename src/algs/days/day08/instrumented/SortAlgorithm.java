package algs.days.day08.instrumented;

import edu.princeton.cs.algs4.StdOut;

/**
 * Instrumented sorting algorithms. DO NOT MODIFY THIS CODE.
 */
public abstract class SortAlgorithm {
	
	/** Array to be sorted. */
	final protected int[] a;
	
	/** Presumes that the values being sorted are all integers in the range 0..N-1. */
	final protected int[] moved;

	public SortAlgorithm(int[] a) {
		this.a = a;
		moved = new int[a.length];
	}

	/** 
	 * Request the sorting algorithm to sort its array.
	 */
	public abstract void sort();

	/** Number of times less(a,b) was invoked. */
	long lessCount = 0; 
	
	/** Retrieve the number of times less(a,b) was invoked since beginning of sort(). */
	public long getLessCount() { return lessCount; }

	/** Number of array updates. */
	long arrayUpdateCount = 0; 
	
	/** Retrieve the number of array updates since beginning of sort(). */
	public long getArrayUpdateCount() { return arrayUpdateCount; }

	/***************************************************************************
	 *  Helper sorting functions.
	 ***************************************************************************/

	/** Determine if v < w. */
	protected boolean less(int v, int w) {
		lessCount++;
		return v < w;
	}

	/** Exchange a[i] and a[j]. */
	protected void exch(int i, int j) {
		arrayUpdateCount += 2;
		int swap = a[i];
		a[i] = a[j];
		a[j] = swap;
		moved[a[i]]++;
		moved[a[j]]++;
	}

	/** 
	 * Returned the move stats for all numbers. The returned array, m[i], contains the number of times
	 * that integer 'i' was moved during the sort.  
	 */
	public int[] getMoveStats() { return moved; }
	
	/** Find the average number of times a number was moved. */
	public float getMoveAverage() {
		float sum = 0;
		for (int m : moved) { sum += m; }
		return sum/moved.length;
	}
	
	/** Find the most number of times a number was moved. */
	public int getMoveMax() {
		int max = 0;
		for (int m : moved) { if (m > max) { max = m; }}
		return max;
	}
	
	/** Sanity check to validate that array is sorted. */
	public boolean isSorted() {
		for (int i = 1; i < a.length; i++) {
			if (less(a[i], a[i-1])) return false;
		}
		return true;
	}

	/** Helper method to simply output values. */
	protected void show() {
		for (int i = 0; i < a.length; i++) {
			StdOut.println(a[i]);
		}
	}
}
