package aiburns.hw3;

import algs.hw3.CountedItem;

/**
 * Simplified implementation of TimSort. Use this class as is. DO NOT COPY INTO YOUR PACKAGE.
 * 
 * Note: this implementation is significantly simplified and doesn't take
 * advantage of the true power of TimSort. Still, it has some performance
 * benefits in some specific instances, as you will see on the homework.
 * 
 * Is this a stable sort? Your homework will confirm.
 * 
 * Check out paper that talks about worst cast complexity:
 * https://www.researchgate.net/publication/325311200_On_the_Worst-Case_Complexity_of_TimSort
 */
public class PrimitiveTimSort {

	public static void sort(Comparable[] A) {
		int N = A.length;
		if (N < 64) {
			insertionSort(A, 0, N-1);
		}

		// Insertion sort in strips of 'size'
		int size = minRun(N);
		for (int lo = 0; lo < N; lo += size) {
			insertionSort(A, lo, Math.min(lo+size-1, N-1));
		}
		
		Comparable[] aux = new Comparable[N];
		while (size < N) {
			// Merge all doubled ranges, taking care with last one
			for (int lo = 0; lo < N; lo += 2*size) {
				int mid = Math.min(lo + size - 1, N-1);
				int hi  = Math.min(lo + 2*size - 1, N-1);
				merge(A, aux, lo, mid, hi);
			}
			
			size = 2 * size;
		}
	}

	public static boolean isSortedArrayStable(Comparable[] A) {
		for (int i = 1; i < A.length; i++) {
			if (((algs.hw3.CountedItem)A[i-1]).equals((algs.hw3.CountedItem)A[i]) && !((algs.hw3.CountedItem)A[i-1]).earlier((CountedItem)A[i])) {
				return false; 
			}
		}
		
		return true;
	}
	
	/**
	 *  https://hg.python.org/cpython/file/tip/Objects/listsort.txt
	 *  
	 *  Instead we pick a minrun in range(32, 65) such that N/minrun is exactly a
	 *  power of 2, or if that isn't possible, is close to, but strictly less than,
	 *  a power of 2.  This is easier to do than it may sound:  take the first 6
	 *  bits of N, and add 1 if any of the remaining bits are set.  In fact, that
	 *  rule covers every case in this section, including small N and exact powers
	 *  of 2;
	 *  
	 *  You do not need to understand this function.
	 */
	public static int minRun(int n) {
		int r = 0;
		while (n >= 64) {
			r |= n & 1;
			n >>= 1;
		}

		return n + r;
	}

	/** Sort A[lo .. hi] using Insertion Sort. */
	static void insertionSort(Comparable[] A, int lo, int hi) {
		for (int i = lo+1; i <= hi; i++) {
			for (int j = i; j > lo; j--) {
				if (A[j-1].compareTo(A[j]) <= 0) break;
				Comparable tmp = A[j];
				A[j] = A[j-1];
				A[j-1] = tmp;
			}
		}
	}
	
	/** 
	 * Adapted from MergeSort implementation. You do not need to modify.
	 * 
	 * Assumes that the auxiliary array is sufficiently large to support the merge request.
	 */
	public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
		// copy to aux[]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k]; 
		}

		// merge back to a[]
		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++) {
			if      (i > mid)                        a[k] = aux[j++];
			else if (j > hi)                         a[k] = aux[i++];
			else if (aux[j].compareTo(aux[i]) < 0)   a[k] = aux[j++];
			else                                     a[k] = aux[i++];
		}
	}

}
