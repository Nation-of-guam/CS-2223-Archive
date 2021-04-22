package algs.days.day17;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Challenge here is to design an iterator for the MaxPQ that doesn't destroy the values in the PQ when
 * iterating over its values. This is a nice exercise!
 */
public class MaxPQ<Key extends Comparable<Key>> implements Iterable<Key> {
	private Key[] pq;                    // store items at indices 1 to N (pq[0] is unused)
	private int N;                       // number of items on priority queue

	public MaxPQ(int initCapacity) {
		pq = (Key[]) new Comparable[initCapacity + 1];
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
	
	class HeapIterator implements Iterator<Key> {

		/** If Bag of nodes is empty, we consider the first. Otherwise, contains the keys to inspect. */
		Node firstCandidate = null;
		Node lastCandidate = null;
		
		class Node {
			int index;
			Node next;
			
			Node (int idx) { this.index = idx; }
		}
		
		HeapIterator() {
			firstCandidate = lastCandidate = new Node(1);   // keep track of root
		}
		
		@Override
		public boolean hasNext() {
			return firstCandidate != null;
		}

		@Override
		public Key next() {
			// find max!
			
			int idx = firstCandidate.index;
			int maxIdx = idx;
			Node prevIdx = null;
			Node prev = null;
			Node n = firstCandidate.next;
			while (n != null) {
				if (less(maxIdx, n.index)) {
					maxIdx = n.index;
					prevIdx = prev;  // record the one BEFORE max
				}
				
				prev = n;
				n = n.next;
			}
			
			
			if (lastCandidate == firstCandidate) {
				firstCandidate = lastCandidate = null;  // DONE
			} else {
				
				// when we get here, we can link around maxIdx using prevIdx, except when last
				if (lastCandidate.index == maxIdx) {
					if (prevIdx != null) {
						prevIdx.next = null;
					}
					lastCandidate = prevIdx; 
				} else {
					if (prevIdx == null) {
						// first one being removed!
						firstCandidate = firstCandidate.next;
						if (firstCandidate == null) { lastCandidate = null; }
					} else {
						prevIdx.next = prevIdx.next.next;
					}
					
				}
				
				if (lastCandidate == null) { 
					firstCandidate = null; 
				}
			}
			
			// add children of element being removed (if they exist)
			if (2*maxIdx <= N) { extend(2*maxIdx); }
			if (2*maxIdx+1 <= N+1) { extend(2*maxIdx+1); }
			return pq[maxIdx];
		}
		
		// add candidate to end, updating first if needed.
		void extend(int idx) {
			if (idx > N) { return; }
			
			if (lastCandidate == null) {
				firstCandidate = lastCandidate = new Node(idx);
			} else {
				lastCandidate.next = new Node(idx);
				lastCandidate = lastCandidate.next;
			}
		}
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
		return (pq[i].compareTo(pq[j]) < 0);
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

	@Override
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}
	
	public static void main(String[] args) {
		int size = 7;
		MaxPQ<Integer> mpq = new MaxPQ<Integer>(size);
		
		for (int i : new int[] { 1, 1, 1, 2, 2, 5, 6} ) {
			mpq.insert(i);
		}
		
		for (int val : mpq) {
			System.out.println(val);
		}
		
//		Integer[] vals = new Integer[size];
//		for (int i = 0; i < size; i++) {
//			mpq.insert(vals[i] = StdRandom.uniform(size));
//		}
//		Merge.sort(vals);
//		System.out.println(Arrays.toString(vals));
		
//		Stack<Integer> st = new Stack<>();
//		for (int val : mpq) {
//			System.out.println(val);
//			st.push(val);
//		}
		
//		for (int i = 0; i < size; i++) {
//			if (!vals[i].equals(st.pop())) {
//				System.out.println("BAD");
//			}
//		}
	}
}
