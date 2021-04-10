package algs.days.day08.instrumented;

import edu.princeton.cs.algs4.StdIn;

public class Merge extends SortAlgorithm {
	
	public Merge(int[] a) {
		super(a);
	}
    
	int aux[];
	
    public void sort() {
    	lessCount = arrayUpdateCount = 0;
    	aux = new int[a.length];
    	sort (a, 0, a.length-1);
    }
    
    // recursive helper function
    void sort (int[] a, int lo, int hi) {
    	if (hi <= lo) return;
    	
    	int mid = lo + (hi - lo)/2;
    	
    	sort(a, lo, mid);
    	sort(a, mid+1, hi);
    	merge(a, lo, mid, hi);
    }
    
    // merge sorted results a[lo..mid] with a[mid+1..hi] back into a
    void merge (int[] a, int lo, int mid, int hi) {
    	int i = lo;     // starting index into left sorted sub-array
    	int j = mid+1;  // starting index into right sorted sub-array
    	
    	// copy a[lo..hi] into aux[lo..hi]
    	for (int k = lo; k <= hi; k++) {
    		moved[a[k]]++; aux[k] = a[k]; arrayUpdateCount++;
    	}
    	
    	// now comes the merge. Something you might simulate with flashcards
    	// drawn from two stack piles. This is the heart of mergesort. 
    	for (int k = lo; k <= hi; k++) {
    		arrayUpdateCount++;
    		if       (i > mid)               { moved[aux[j]]++; a[k] = aux[j++]; }
    		else if  (j > hi)                { moved[aux[i]]++; a[k] = aux[i++]; }
    		else if  (less(aux[j], aux[i]))  { moved[aux[j]]++; a[k] = aux[j++]; }
    		else                             { moved[aux[i]]++; a[k] = aux[i++]; }
    	}
    }
    

    /**
     * Reads in a sequence of strings from standard input; selection sorts them; 
     * and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
        int[] a = StdIn.readAllInts();
        Merge ms = new Merge(a);
        ms.sort();
        ms.show();
    }

}