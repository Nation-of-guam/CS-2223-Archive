package algs.days.day07;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Proper MergeSort from Sedewick, 4ed with annotations showing sub-tasks
public class MergeAnnotated {
    
	static Comparable aux[];
	static int numComparisons = 0;
	
    public static void sort(Comparable[] a) {
    	numComparisons = 0;
    	aux = new Comparable[a.length];
    	sort (a, 0, a.length-1, "");
    	System.out.println();
    }
    
    // recursive helper function
    static void sort (Comparable[] a, int lo, int hi, String indent) {
    	if (hi <= lo) return;
    	
    	int mid = lo + (hi - lo)/2;
    	
    	sort(a, lo, mid, indent + "  ");
    	
    	sort(a, mid+1, hi, indent + "  ");
    	merge(a, lo, mid, hi, indent);
    	
    }
    
    // merge sorted results a[lo..mid] with a[mid+1..hi] back into a
    static void merge (Comparable[] a, int lo, int mid, int hi, String indent) {
    	StdOut.print(indent + "merge a[" + lo + ".." + mid + "] with a[" + (mid+1) + ".." + hi + "]     [");
    	show(a, lo,mid);
    	show(a, mid+1,hi); 
    	
    	int i = lo;     // starting index into left sorted sub-array
    	int j = mid+1;  // starting index into right sorted sub-array
    	
    	// copy a[lo..hi] into aux[lo..hi]
    	for (int k = lo; k <= hi; k++) {
    		aux[k] = a[k];
    	}
    	
    	// now comes the merge. Something you might simulate with flashcards
    	// drawn from two stack piles. This is the heart of mergesort. 
    	for (int k = lo; k <= hi; k++) {
    		if       (i > mid)               { a[k] = aux[j++]; }
    		else if  (j > hi)                { a[k] = aux[i++]; }
    		else if  (less(aux[j], aux[i]))  { a[k] = aux[j++]; numComparisons++; }
    		else                             { a[k] = aux[i++]; numComparisons++; }
    	}
    	StdOut.print ("]  ==> [");
    	show(a,lo,hi);
    	StdOut.println("]");
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

    /**
     * Reads in a sequence of strings from standard input; selection sorts them; 
     * and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        MergeAnnotated.sort(a);
        show(a, 0, a.length-1);
        System.out.println();
        System.out.println(a.length + " requires " + numComparisons + " comparisons.");
    }
}