package algs.days.day07;

import edu.princeton.cs.algs4.StdOut;

// My strawman algorithm which wastes space (as I mention in the beginning of Lecture 7).
// Demonstrate output of function calls.
public class AnnotatedWastedSpaceMerge {
    static int extraSpace;
    
	public static void sort(Comparable[] a) {
    	sort (a, 0, a.length-1,"");
    }
    
    // recursive helper function
    static void sort (Comparable[] a, int lo, int hi, String indent) {
    	StdOut.printf("%ssort(%d,%d)\n", indent, lo, hi);
    	if (hi <= lo) return;
    	
    	int mid = lo + (hi - lo)/2;
    	
    	sort(a, lo, mid, indent + "  1. ");
    	sort(a, mid+1, hi, indent + "  2. ");
    	StdOut.printf("%s  3. merge(%d,%d)\n", indent, lo, hi);
    	merge(a, lo, mid, hi);
    }
    
    // merge sorted results a[lo..mid] with a[mid+1..hi] back into a
    static void merge (Comparable[] a, int lo, int mid, int hi) {
    	int left = lo;     // starting index into left sorted sub-array
    	int right = mid+1;  // starting index into right sorted sub-array
    	
    	// merge a[lo..mid] with a[mid+1..hi] into new storage
    	Comparable[] aux = new Comparable[hi-lo+1];
    	extraSpace += aux.length;
    	
    	// copy a[lo..hi] into aux[lo..hi]
    	for (int k = lo; k <= hi; k++) {
    		aux[k-lo] = a[k];
    	}
    	
    	// now comes the merge. Something you might simulate with flashcards
    	// drawn from two stack piles. This is the heart of mergesort. 
    	for (int k = lo; k <= hi; k++) {
    		if       (left > mid)                         { a[k] = aux[right-lo]; right++; }
    		else if  (right > hi)                         { a[k] = aux[left-lo];  left++;  }
    		else if  (less(aux[right-lo], aux[left-lo]))  { a[k] = aux[right-lo]; right++; }
    		else                                          { a[k] = aux[left-lo];  left++;  }
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
            StdOut.println (a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; selection sorts them; 
     * and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
    	String[] vals = new String [] {"E", "M", "R", "G", "E", "S", "O", "R"};
        	
    	AnnotatedWastedSpaceMerge.sort(vals);
    	show(vals, 0, vals.length-1);
    }
}
