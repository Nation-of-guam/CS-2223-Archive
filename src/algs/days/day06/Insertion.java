package algs.days.day06;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Sedgewick, 4ed
public class Insertion {

	public static long lessCount;
	public static long exchCount;
	
	/**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
	public static void sort(Comparable[] a) {
        int N = a.length;
		// Truth: A is a collection of n values
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);     // HERE: I have found some A[j] which is smaller than A[j-1] so it has to swap
            }
		// Truth: A[0 .. i] is sorted in ascending order
        }
		// Truth: A is ascending order
    }
	

   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
    	lessCount++;
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
    	exchCount++;
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; selection sorts them; 
     * and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Insertion.sort(a);
        show(a);
        System.out.println("INSERTION SORT: #less = " + lessCount + ", #exch = " + exchCount);
    }
}