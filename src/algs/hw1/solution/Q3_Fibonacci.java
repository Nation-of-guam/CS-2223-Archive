package algs.hw1.solution;

import edu.princeton.cs.algs4.*;

/**
 * Copy this file into your USERID.hw1 package and complete the implementation marked below.
 */
public class Q3_Fibonacci {

	/**
	 * For this assignment, you will need to keep track of how many times 
	 * fibonacci_recursive() was invoked.
	 */
	static long numRecursiveCalls = 0;
	
	/**
	 * For this assignment, you will need to keep track of how many times 
	 * fibonacci_improved() and lucas_improved() are invoked.
	 */
	static long numRecursiveImproved = 0;

	/** 
	 * Improved recursive implementation that takes advantage of identity that:
	 * 
	 *     F_(x+y) = 1/2 * (F_x * L_y) + (F_y * L_x)
	 * 
	 * For any n >3, the recursive case should choose x = n/2 (truncated) and then y = n-x.
	 * Make sure you increase count for numRecursiveImproved.
	 */
	static long fibonacci_improved(int n) {
		numRecursiveImproved++;
		// base case. 
		if (n == 0) { return 0; }
		if (n == 1) { return 1; }
		if (n == 2) { return 1; }
		if (n == 3) { return 2; }  // THIS NEEDS TO BE HERE. BONUS QUESTION ASKS WHY!

		
		int left = n/2;
		int right = n - left;
				
		return (fibonacci_improved(left)*lucas_improved(right) + fibonacci_improved(right)*lucas_improved(left))/2; 
	}
	
	/** 
	 * Improved recursive implementation that takes advantage of identify that:
	 * 
	 *     Ln = Fn-1 + Fn+1 for n > 1
	 */
	static long lucas_improved(int n) {
		numRecursiveImproved++;
		if (n == 0) { return 2; }
		if (n == 1) { return 1; }
		
		return fibonacci_improved(n-1) + fibonacci_improved(n+1);
		
	}

	// ---------------------------------------------------------------------------------
	// YOU DO NOT NEED TO MODIFY ANYTHING BELOW THIS LINE UNLESS IT JUST TAKES
	// FAR TOO LONG ON YOUR COMPUTER, THEN LOOK AT THE MAIN METHOD
	// ---------------------------------------------------------------------------------

	/**
	 * Compute ten trials and pick best time. When you are not certain that a reported
	 * empirical run follows a normal distribution, this is perhaps the most accurate
	 * way to benchmark performance.
	 */
	static double time_fibonacci_recursive(int n) {
		double minTime = Integer.MAX_VALUE; 
		for (int trial = 0; trial < 10; trial++) {
			StopwatchCPU base = new StopwatchCPU();
			fibonacci_recursive(n);
			double newTime = base.elapsedTime();
			if (newTime < minTime) { minTime = newTime; }
		}

		return minTime;
	}

	/**
	 * Compute ten trials and pick best time. When you are not certain that a reported
	 * empirical run follows a normal distribution, this is perhaps the most accurate
	 * way to benchmark performance.
	 */
	static double time_fibonacci_improved(int n) {
		double minTime = Integer.MAX_VALUE; 
		for (int trial = 0; trial < 10; trial++) {
			StopwatchCPU base = new StopwatchCPU();
			fibonacci_improved(n);
			double newTime = base.elapsedTime();
			if (newTime < minTime) { minTime = newTime; }
		}

		return minTime;
	}

	/** Standard inefficient recursive implementation. There are many ways to improve this. */
	static long fibonacci_recursive(int n) {
		numRecursiveCalls++;
		// base case
		if (n == 0) { return 0; }
		if (n == 1) { return 1; }

		return fibonacci_recursive(n-1) + fibonacci_recursive(n-2);
	}

	/**
	 * Run trials to confirm Fibonacci and Lucas implementation works.
	 */
	static void fibonacciTrial(int max) {
		System.out.println("N\t\tFn\t\tLn\t\tFrec\t\tFirec");
		for (int n = 0; n < max; n++) {
			// reset counts
			numRecursiveCalls = 0;
			numRecursiveImproved = 0;
			long referenceVal = fibonacci_recursive(n);
			long matchVal = fibonacci_improved(n);
			
			if (matchVal != referenceVal) {
				System.err.println(String.format("Your result of %d doesn't match %d", matchVal, referenceVal));
			}

			// have to make copy here BECAUSE lucas_improved() should update numRecursiveImproved
			long improvedCounts = numRecursiveImproved;
			
			// report results. Last column is time.
			long luc = lucas_improved(n);
			System.out.println(String.format("%2d\t%8d\t%8d\t%8d\t%8d",
					n, matchVal, luc, numRecursiveCalls, improvedCounts));
			// reset counts
			numRecursiveCalls = 0;
			numRecursiveImproved = 0;
		}
	}

	public static void improved_implementation(int max) {
		System.out.println(String.format("N\tFnTime\t\tFinTime\t\t\tFn"));
		for (int n = 0; n < max; n++) {
			double timef = time_fibonacci_recursive(n);
			double timefi = time_fibonacci_improved(n);
			System.out.println(String.format("%2d\t%8f\t%8f\t%8d", n, timef, timefi, fibonacci_improved(n)));
		}
	}

	/**
	 * Run sample trial of performance of code.
	 * 
	 * Run a number of trials and record the minimum performance for each n.
	 * 
	 * 1. What is your estimate for minTime(n+1)/minTime(n) as n increases? 
	 */
	public static void main(String[] args) {
		// NOTE =========================================================================
		// NOTE     If 30 is too high a number for your computer, then make it smaller!
		// NOTE =========================================================================
		fibonacciTrial(30);
		System.out.println();
		
		// NOTE =========================================================================
		// NOTE     If 42 is too high a number for your computer, then make it smaller!
		// NOTE =========================================================================
		improved_implementation(42);
	}
}
