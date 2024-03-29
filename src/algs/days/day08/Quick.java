package algs.days.day08;

import edu.princeton.cs.algs4.StdIn;
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
public class Quick {

    // This class should not be instantiated.
    private Quick() { }

    /** Copied from StdRandom.shuffle(). Bringing here so you can see the exchanges... */
    public static void shuffle(Object[] a) {
        if (a == null) throw new NullPointerException("argument array is null");
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + StdRandom.uniform(N-i);     // between i and N-1
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        //shuffle(a);
        sort(a, 0, a.length - 1);
    }

    // quicksort the subarray from a[lo] to a[hi]
    static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        int loc = partition(a, lo, hi);
        sort(a, lo, loc-1);
        sort(a, loc+1, hi);
    }

    // partition the subarray a[lo..hi] so that a[lo..right-1] <= a[right] <= a[right+1..hi]
    // and return the index right.
    static int partition(Comparable[] a, int lo, int hi) {
        int left = lo;
        int right = hi + 1;
        Comparable v = a[lo];  
        while (true) { 

            // find item on lo to swap
            while (less(a[++left], v))
                if (left == hi) break;

            // find item on hi to swap
            while (less(v, a[--right]))
                if (right == lo) break;     

            // check if pointers cross
            if (left >= right) break;

            exch(a, left, right);
        }

        // put partitioning item v at a[right]
        exch(a, lo, right);

        // now, a[lo .. right-1] <= a[right] <= a[right+1 .. hi]
        return right;
    }



   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
    
    // is v < w ?
    static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
        
    // exchange a[i] and a[j]
    static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


   /***************************************************************************
    *  Check if array is sorted - useful for debugging.
    ***************************************************************************/
    static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }


    // print array to standard output
    static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; quicksorts them; 
     * and prints them to standard output in ascending order. 
     * Shuffles the array and then prints the strings again to
     * standard output, but this time, using the select method.
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        Quick.sort(a);
        show(a);
    }

}

