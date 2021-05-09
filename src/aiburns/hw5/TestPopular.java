package aiburns.hw5;

import algs.iterator.ArrayIterator;
import edu.princeton.cs.algs4.StopwatchCPU;

import java.util.Iterator;

/**
 * Copy this file into your SUBMISSION area and execute it to ensure all test cases pass, including
 * the stress test and worst case situations.
 */
public class TestPopular {
	static void test(boolean b) {
		if (!b) { throw new RuntimeException("Fails Test case."); }
	}
	
	/** Just count the number of entries in this Iterable. */
	static int count(Iterable<Integer> result) {
		int count = 0;
		for (int i:result) { count++; }
		return count;
	}
	
	/** Ensure entries in this Iterable are in ascending order. */
	static boolean ascending(Iterable<Integer> result) {
		boolean first = true;
		int last = 0;
		for (int i:result) { 
			if (first) {
				last = i;
				first = false;
			} else {
				if (i < last) { return false; }
				last = i;
			}
		}
		return true;
	}
	
	/** 
	 * Determine whether there is an exact match between the values in 
	 * the expected Iterator, and the values that were returned as an 
	 * Iterable by the 'reverseMatch' query.
	 */
	static void equal (Iterator<Integer> expected, Iterable<Integer> actual) {
		Iterator<Integer> it2 = actual.iterator();
		while (expected.hasNext() && it2.hasNext()) {
			Integer one = expected.next();
			Integer two = it2.next();
			if (!one.equals(two)) {
				throw new RuntimeException(String.format("Fails equal on %d and %d", one, two));
			}
		}
		if (expected.hasNext()) { throw new RuntimeException("Expected has more values."); }
		if (it2.hasNext()) { throw new RuntimeException("Actual has more values."); }
	}
	
	static void output(Iterable<Integer> result) {
		for (int i : result) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	/**
	 * Stress test.
	 * Create a PST with N keys, whose values are Floor(Log(N)). As you can see, this will
	 * space out the mappings. With greater N, there are greater chances for repetition.
	 * 
	 * But each value, v, there are exactly 2^v keys that match it. 
	 */
	static void stressTest(int max) {
		System.out.println("\nIn the following table:\n-----------------------");
		System.out.println("BuildT is the build time, which should be O(log N)");
		System.out.println("SearchS should be essentially 0 since it does reverseMatch when K is small");
		System.out.println("SearchL should be O(N) since it does reverseMatch when K is N/2");
		
		System.out.println("N\tBuildT\tSearchS\tSearchL");
		for (int N = 2048; N <= max; N *= 2) {
			PopularSymbolTable pst = null;
			StopwatchCPU cpu = new StopwatchCPU();
			for (int k = 0; k < 100; k++) {
				pst = new PopularSymbolTable();
				// build times: NOTE LOG is in base e, so divide by log 2 to normalize
				for (int n = 1; n < N; n++) {
					pst.put(n, (int)Math.floor(Math.log(n)/Math.log(2)));
				}
			}			
			double buildTime = cpu.elapsedTime();
			System.gc();
			
			int most = (int) (Math.log(N)/Math.log(2)) - 1;
			// now do searches (smallest one and largest one)
			cpu = new StopwatchCPU();
			for (int k = 0; k < 1000; k++) {
				test(2 == count(pst.reverseMatch(1)));
			}
			double searchSmall = cpu.elapsedTime();
			for (int k = 0; k < 1000; k++) {
				test(N/2 == count(pst.reverseMatch(most)));
			}
			double searchLarge = cpu.elapsedTime() - searchSmall;
			ascending(pst.reverseMatch(most));
			
			System.out.println(String.format("%d\t%.3f\t%.3f\t%.3f", N, buildTime, searchSmall, searchLarge));
		}
	}
	
	/**
	 * Worst Case
	 * Create a PST with N keys where all keys match to 1, the same value.
	 */
	static void worstCase(int max) {
		System.out.println("\nIn the following table:\n-----------------------");
		System.out.println("BuildT is the build time, which should be O(log N)");
		System.out.println("SearchT should be O(N) since reverseMatch returns K=N values");
		
		System.out.println("N\tBuildT\tSearchT");
		for (int N = 2048; N <= max; N *= 2) {
			PopularSymbolTable pst = null;
			StopwatchCPU cpu = new StopwatchCPU();
			for (int k = 0; k < 100; k++) {
				pst = new PopularSymbolTable();
				for (int n = 0; n < N; n++) {
					pst.put(n, 1);
				}
			}			
			double buildTime = cpu.elapsedTime();
			System.gc();
			
			// now do searches (smallest one and largest one)
			cpu = new StopwatchCPU();
			for (int k = 0; k < 1000; k++) {
				test(N == count(pst.reverseMatch(1)));
			}
			double searchLarge = cpu.elapsedTime();
			ascending(pst.reverseMatch(1));
			
			System.out.println(String.format("%d\t%.3f\t%.3f", N, buildTime,searchLarge));
		}
	}
	
	public static void main(String[] args) {
		// basic tests.
		PopularSymbolTable pst = new PopularSymbolTable();
		
		for (int i = 0; i < 1000; i++) {
			pst.put(i, i*i);
		}
		test(pst.size() == 1000);
		for (int i = -1; i >= -1000; i--) {
			pst.put(i,  i*i);
		}
		test(pst.size() == 2000);
		equal(new ArrayIterator<Integer>(new Integer[] { }), pst.reverseMatch(9999));    // no match! Make sure empty is returned not null
		equal(new ArrayIterator<Integer>(new Integer[] { -3, 3 }), pst.reverseMatch(9));
		pst.put(0, 9);
		equal(new ArrayIterator<Integer>(new Integer[] { -3, 0, 3 }), pst.reverseMatch(9));
		test(pst.get(0).equals(9));
		
		for (int i = -1; i >= -1000; i--) {
			pst.remove(i);
		}
		
		equal(new ArrayIterator<Integer>(new Integer[] { 0, 3 }), pst.reverseMatch(9));
		
		test(pst.remove(98765) == false);
		
		// covers tricky case when the value associated with a key changes. Make sure that 
		// we don't forget about this in the reverseMatch response.
		test(pst.put(98765, -33));
		test(pst.put(98764, -33));
		test(pst.put(-98765, -33));
		test(pst.put(98765, 33) == false);  // already there
		equal(new ArrayIterator<Integer>(new Integer[] { -98765, 98764 }), pst.reverseMatch(-33));
		
		// If your computer is not as fast, then just pass in 525288 or 262144 and generate the same table.
		stressTest(262144); // 1048576
		
		// validate worst case for everyone
		worstCase(65536);
	}
}
