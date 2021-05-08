package algs.days.day01;

import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.*;

public class TimingComparison {

	/** Values to be searched. Will be in sorted order and contain unique values. */
	public static int vals[];

	/** Values to be searched. WIll also be in sorted order and contain unique values. */
	public static int targets[];

	/**
	 * Returns a unique, sorted array 
	 * 
	 * @param number -- number of unique values to be present
	 * @param range  -- +/- range is the range of numbers to generate 
	 * @return
	 */
	static int[] randomUniqueArray(int total, int range) {
		// Range of numbers to choose from [-range, range]
		if (2*range + 1 < total) {
			throw new IllegalArgumentException ("Range is too small given the required total: " + total);
		}

		ArrayList<Integer> uniq = new ArrayList<>();
		while (uniq.size() < total) {
			int val = StdRandom.uniform(-range, range);
			if (!uniq.contains(val)) {
				uniq.add(val);
			}
		}
		Collections.sort(uniq); // sorts properly
		int[] values = new int[uniq.size()];
		for (int i = 0; i < values.length; i++) { values[i] = uniq.get(i); }

		return values;
	}

	/** Execute BinaryArraySearch using DIV for midpoint, and compare LT, GT, then EQ. */
	boolean contains_lt_gt_eq_div(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc < 0) {
				low = mid+1;
			} else if (rc > 0) {
				high = mid-1;
			} else {
				return true;
			}
		}
		return false;
	}

	/** Execute BinaryArraySearch using DIV for midpoint, and compare LT, GT, then EQ. */
	boolean contains_gt_lt_eq_div(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc > 0) {
				high = mid-1;
			} else if (rc < 0) {
				low = mid+1;
			} else  {
				return true;
			}
		}
		return false;
	}

	/** Execute BinaryArraySearch using DIV for midpoint, and compare LT, EQ, then GT. */
	boolean contains_lt_eq_gt_div(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc < 0) {
				low = mid+1;
			} else if (rc == 0) {
				return true;
			} else {
				high = mid-1;
			} 
		}
		return false;
	}

	/** Execute BinaryArraySearch using DIV for midpoint, and compare GT, EQ, then LT. */
	boolean contains_gt_eq_lt_div(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc > 0) {
				high = mid-1;
			} else if (rc == 0) {
				return true;
			} else {
				low = mid+1;
			} 
		}
		return false;
	}
	
	/** Execute BinaryArraySearch using DIV for midpoint, and compare EQ, GT, then LT. */
	boolean contains_eq_gt_lt_div(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc == 0) {
				return true;
			} else if (rc > 0) {
				high = mid-1;
			} else  {
				low = mid+1;
			} 
		}
		return false;
	}
	
	/** Execute BinaryArraySearch using DIV for midpoint, and compare EQ, LT, then GT. */
	boolean contains_eq_lt_gt_div(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc == 0) {
				return true;
			} else if (rc < 0) {
				low = mid+1;
			} else {
				high = mid-1;
			} 
		}
		return false;
	}


	/** Execute BinaryArraySearch using SHIFT for midpoint, and compare LT, GT, then EQ. */
	boolean contains_lt_gt_eq_shift(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high) >> 1;

		int rc = collection[mid] - target;
		if (rc < 0) {
			low = mid+1;
		} else if (rc > 0) {
			high = mid-1;
		} else {
			return true;
		}
		}
		return false;
	}

	/** Execute BinaryArraySearch using SHIFT for midpoint, and compare EQ, LT, then GT. */
	boolean contains_eq_lt_gt_shift(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high) >> 1;

		int rc = collection[mid] - target;
		if (rc == 0) {
			return true;
		} else if (rc < 0) {
			low = mid+1;
		} else {
			high = mid-1;
		} 
		}
		return false;
	}

	/** Compute timing results of DIV/LT/GT/EQ. */
	double timing_lt_gt_eq_div(int numRuns) {
		System.gc();
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_lt_gt_eq_div(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}

	/** Compute timing results of DIV/LT/EQ/GT. */
	double timing_lt_eq_gt_div(int numRuns) {
		System.gc();
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_lt_eq_gt_div(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}
	
	/** Compute timing results of DIV/EQ/LT/GT. */
	double timing_eq_lt_gt_div(int numRuns) {
		// time a base run
		System.gc();
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_eq_lt_gt_div(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}
	
	/** Compute timing results of DIV/EQ/GT/LT. */
	double timing_eq_gt_lt_div(int numRuns) {
		// time a base run
		System.gc();
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_eq_gt_lt_div(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}

	/** Compute timing results of DIV/GT/LT/EQ. */
	double timing_gt_lt_eq_div(int numRuns) {
		System.gc();
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_gt_lt_eq_div(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}

	/** Compute timing results of DIV/GT/EQ/LT. */
	double timing_gt_eq_lt_div(int numRuns) {
		System.gc();
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_gt_eq_lt_div(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}
	
	/** Compute timing results of SHIFT/LT/GT/EQ. */
	public double timing_lt_gt_eq_shift(int numRuns) {
		// time a base run
		System.gc();
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_lt_gt_eq_shift(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}

	public double timing_eq_lt_gt_shift(int numRuns) {
		// time a base run
		System.gc();
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_eq_lt_gt_shift(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}
	/** Launch everything. */
	public static void main(String[] args) {

		System.out.println("Generating values. This may take a few seconds...");
		// range of numbers is +/- 2^24 or [-16777216, 16777216]. Generate a total of 2^16 or 65,536
		vals = randomUniqueArray((int) Math.pow(2, 16), (int) Math.pow(2, 24));

		// These are the items to be searched: 2^16 or 65,536 within range +/- 2^25 of [-33554432, 33554432]
		// to ensure there are some numbers that are not found.
		targets = randomUniqueArray((int) Math.pow(2, 16), (int) Math.pow(2, 25));

		// quick test
		TimingComparison tc = new TimingComparison();
		for (int t : targets) {
			boolean found1 = tc.contains_eq_lt_gt_div(vals, t);
			boolean found2 = tc.contains_lt_gt_eq_shift(vals, t);
			boolean found3 = tc.contains_lt_gt_eq_div(vals, t);
			boolean found4 = tc.contains_eq_gt_lt_div(vals, t);
			boolean found5 = tc.contains_lt_eq_gt_div(vals, t);
			boolean found6 = tc.contains_gt_eq_lt_div(vals, t);
			boolean found7 = tc.contains_gt_lt_eq_div(vals, t);

			if (found1 == found2 && found2 == found3 && found3 == found4 && found4 == found5 && found5 == found6 && found6 == found7) {
				// good
			} else {
				System.err.println("Defect found on " + t + ", " + found1 + "," + found2 + "," + found3);
				System.exit(-1);
			}
		}


		// how many times is a value actually found?
		new TimingComparison().investigate(512);


		System.out.println("----\nlt_gt_eq_div\tlt_gt_eq_shift");
		for (int i = 0; i < 5; i++) {
			System.out.printf("%.3f\t\t%.3f\n", new TimingComparison().timing_lt_gt_eq_div(512), new TimingComparison().timing_lt_gt_eq_shift(512));
		}
		System.out.println("----\nlt_eq_gt_div");
		for (int i = 0; i < 5; i++) {
			System.out.printf("%.3f%n", new TimingComparison().timing_lt_eq_gt_div(512));
		}
		System.out.println("----\neq_lt_gt_div\teq_lt_gt_shift");
		for (int i = 0; i < 5; i++) {
			System.out.printf("%.3f\t\t%.3f\n", new TimingComparison().timing_eq_lt_gt_div(512), new TimingComparison().timing_eq_lt_gt_shift(512));
		}
		System.out.println("----\neq_gt_lt_div");
		for (int i = 0; i < 5; i++) {
			System.out.printf("%.3f%n", new TimingComparison().timing_eq_gt_lt_div(512));
		}
		System.out.println("----\ngt_lt_eq_div");
		for (int i = 0; i < 5; i++) {
			System.out.printf("%.3f%n", new TimingComparison().timing_gt_lt_eq_div(512));
		}
		System.out.println("----\ngt_lt_eq_div");
		for (int i = 0; i < 5; i++) {
			System.out.printf("%.3f%n", new TimingComparison().timing_gt_lt_eq_div(512));
		}
	}

	/** Can you compute (in advance) how many will be found? */
	void investigate(int numRuns) {
		long numFound = 0, numMissed = 0;
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				if (contains_eq_lt_gt_div(vals, targets[i])) {
					numFound++;
				} else {
					numMissed++;
				}
			}
		}

		System.out.println("Out of " + (numMissed + numFound) + " there were " + numFound + " found.");
	}
}
