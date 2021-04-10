package algs.days.day08.instrumented;

import edu.princeton.cs.algs4.StdIn;

public class Insertion extends SortAlgorithm {
	
	public Insertion(int[] a) {
		super(a);
        if (a.length >= 65536) {
        	throw new IllegalArgumentException("Insertion Sort will be far too slow on " + a.length + " items.");
        }

	}
	
	@Override
	public float getMoveAverage() {
		float sum = 0;
		for (int m : moved) { sum += m; }
		return sum/moved.length;
	}
	
	/**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
	public void sort() {
        int N = a.length;
		for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(j, j-1);
            }
        }
    }
	
    /**
     * Reads in a sequence of strings from standard input; selection sorts them; 
     * and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
        int[] a = StdIn.readAllInts();
        Insertion is = new Insertion(a);
        is.sort();
        is.show();
    }
}