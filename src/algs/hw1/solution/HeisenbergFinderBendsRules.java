package algs.hw1.solution;

import algs.hw1.Heisenberg;
import algs.hw1.api.IHeisenbergFinder;

/**
 * Each successive value is between 1 and 9 steps away. TAKE ADVANTAGE OF THIS
 * 
 * AV-1: Once N values are found, return -1 for remaining.
 * 
 * AV-3: Same object is repeatedly called.
 * 
 * AV-4: Record highest one and cut off after that number (depends on AV-3)
 */
public class HeisenbergFinderBendsRules implements IHeisenbergFinder {
	Integer maxFind = null;
	Integer minFind = null;
	Integer midpoint = null;
	Integer lowerpoint = null;
	Integer upperpoint = null;
	
	Integer oneLess = null;
	Integer oneMore = null;
	int numFound = 0;
	
	public int find(Heisenberg h, int target) {
		int lo = 0;
		int hi = h.N - 1;
		int left = 0;
		int right = 0;
		
		// AV-1: Max Found
		if (numFound == h.N) { return -1; } 
		
		// AV-3: Multiple usage
		int mid = (lo + hi) / 2;
		if (midpoint != null) {
			if (target == midpoint) { numFound++; return mid; }
			if (target < midpoint) {
				hi = mid-1;
			} else {
				lo = mid+1;
			}
		}
		
		// Maintains the offset we have to remember
		int diff = 0;
		if (maxFind == null) {
			maxFind = h.inspect(h.N-1);
			minFind = h.inspect(0);
			diff += 1;
		} else {
			
			if (target > maxFind) { return -1; }
			if (target < minFind) { return -1; }
			
		
			if (oneMore != null && oneMore == target) { numFound++; return mid+1; }
//			if (oneLess == null) {
//				int mid = (lo + hi) / 2;
//				oneLess = h.inspect(mid-1);
//				left = -1;
//			} else if (oneMore == null) {
//				int mid = (lo + hi) / 2;
//				oneMore = h.inspect(mid+1);
//				diff += 1;
//				right = 1;
//			}
		}
		
		while (lo <= hi) {
			mid = (lo + hi) / 2;
			
			int val = h.inspect(mid);
			if (midpoint == null) {
				midpoint = val;
			}
			if (val == target + diff) { numFound++; return mid; }
			
			// if the target is a certain delta away from this val, we can reduce high always AND
			// increment LOW
			
			if (val < target + diff) {
				if (lowerpoint == null) { lowerpoint = target; }
				
				int numMove = Math.max(1,  (val - target) / 9);
				lo = mid + numMove;
				diff += 1;    // search upper half? All numbers had been increased by one 
			} else {
				if (upperpoint == null) { upperpoint = target; }
				int numMove = Math.max(1,  (val - target) / 9);
				hi = mid - numMove;   // CAN GO EVEN FURTHER SINCE YOU KNOW deltas are [1,9]
				diff -= 1;    // search lower half? All numbers had been increased by one
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		for (int i = 1; i < 20; i++) {
			Heisenberg h = new Heisenberg(i, 99);
			int numProbes = h.solver(new HeisenbergFinderBendsRules());
			System.out.println(i + "\t" + numProbes);
		}
		System.out.println();
		
		for (int i = 3; i < 257; i=2*i+1) {
			Heisenberg h = new Heisenberg(i, 99);
			int numProbes = h.solver(new HeisenbergFinderBendsRules());
			System.out.println(i + "\t" + numProbes);
		}
		
		// To invoke the validation code, you can do something like this:
		new HeisenbergFinderBendsRules().validate();
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

