package algs.days.day12;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;

/**
 * This is a smaller copy of the actual MaxPQ code in the edu.princeton.cs.algs package. Done
 * this way to make it easier to present in class.
 * 
 * REPEATER Experiment. 
 */
public class MaxPQ<Key> {
	private Key[] pq;                    // store items at indices 1 to N (pq[0] is unused)
	private int N;                       // number of items on priority queue

	public MaxPQ(int initCapacity) {
		pq = (Key[]) new Object[initCapacity + 1];
		N = 0;
	}

	public boolean isEmpty() { return N == 0;  }
	public int size() { return N; }

	public void insert(Key x) {
		pq[++N] = x;
		swim(N);
	}
	
	public String toString() {  
		return Arrays.toString(pq);
	}

	public Key delMax() {
		Key max = pq[1];
		exch(1, N--);
		pq[N+1] = null;     // to avoid loitering and help with garbage collection
		sink(1);
		return max;
	}

	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/
	private void swim(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}

	private void sink(int k) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(j, j+1)) j++;
			if (!less(k, j)) break;
			exch(k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for compares and swaps.
	 ***************************************************************************/
	private boolean less(int i, int j) {
		return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
	}

	private void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	// is pq[1..N] a max heap?
	private boolean isMaxHeap() {
		return isMaxHeap(1);
	}

	// is subtree of pq[1..N] rooted at k a max heap?
	private boolean isMaxHeap(int k) {
		if (k > N) return true;
		int left = 2*k, right = 2*k + 1;
		if (left  <= N && less(k, left))  return false;
		if (right <= N && less(k, right)) return false;
		return isMaxHeap(left) && isMaxHeap(right);
	}
	
	/****************************
	 *  Sample main
	 ****************************/
	public static void main(String[] args) {
		for (int n = 8; n <= 256; n++) {
			
			// pick 500 as the value that "repeats". Does it matter what its value is?
			int repeater = 500;
			int[] vals = new int[n];
			
			int delta = 1;
			for (int i = 0; i < n/2 + delta; i++) {
				vals[i] = repeater;
			}
			
			for (int i = n/2+delta; i < n; i++) {  // random values.
				int rnd = (int) (1000*(Math.random()));
				if (rnd == repeater) { rnd++; } // hack to avoid repeater...
				vals[i] = rnd;
			}
			
			// how many times repeater appeared in a leaf position.
			
			for (int t = 0; t < 50; t++) {
				int leafCount = 0;
				// create MPQ from these integers that are shuffled
				MaxPQ<Integer> mpq = new MaxPQ<Integer>(n);
				StdRandom.shuffle(vals);
				for (int v : vals) {
					mpq.insert(v);
				}
				
				Object[] oo = mpq.pq;
				for (int i = n/2+1; i <= n; i++) {
					Integer iv = (Integer) oo[i];
					if (iv.intValue() == repeater) {
						leafCount++;
					}
				}
				if (leafCount == 0) {
					System.out.println(n + "\t" + t + "\t" + leafCount);
				}
			}
		}
	}
}
