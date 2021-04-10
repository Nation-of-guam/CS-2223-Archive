package algs.hw1.solution;

import algs.hw1.Heisenberg;
import algs.hw1.api.IHeisenbergFinder;

public class HeisenbergFinder implements IHeisenbergFinder {
	
	public int find(Heisenberg h, int target) {
		int lo = 0;
		int hi = h.N - 1;
		
		// Maintains the offset we have to remember
		int diff = 0;
		while (lo <= hi) {
			int mid = (lo + hi) / 2;
			
			int val = h.inspect(mid);
			if (val == target + diff) { return mid; }
			if (val < target + diff) {
				lo = mid+1;
				diff += 1;    // search upper half? All numbers had been increased by one 
			} else {
				hi = mid-1;
				diff -= 1;    // search lower half? All numbers had been increased by one
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		for (int i = 1; i < 20; i++) {
			Heisenberg h = new Heisenberg(i, 99);
			int numProbes = h.solver(new HeisenbergFinder());
			System.out.println(i + "\t" + numProbes);
		}
		System.out.println();
		
		for (int i = 3; i < 257; i=2*i+1) {
			Heisenberg h = new Heisenberg(i, 99);
			int numProbes = h.solver(new HeisenbergFinder());
			System.out.println(i + "\t" + numProbes);
		}
		
		// To invoke the validation code, you can do something like this:
		new HeisenbergFinder().validate();
	}
	
	/**
	 * The following code should pass without any changes.
	 */
	public void validate() {
		int[] hits = { 4, 10, 17, 24, 28, 37, 45 };
		int[] misses = { 3, 9, 16, 23, 27, 36, 44 };

		// all TRUE
		for (int target : hits) {
			Heisenberg h = new Heisenberg(7, 99);
			System.out.println(find(h, target));
		}
		
		// all FALSE
		for (int target : misses) {
			Heisenberg h = new Heisenberg(7, 99);
			System.out.println(find(h, target));
		}
	}
}
