package algs.days.day03;

import edu.princeton.cs.algs4.StdRandom;

/** 
 * Achieve Optimal number of comparisons, using additional space.
 * 
 * This is different from an earlier implementation, to strip it down to its core essence.
 */
public class FindMaxSecondLargestTournament {

	static int numComparisons = 0;

	/** 
	 * Largest is in [0] and second largest is in [1].
	 * 
	 * Assuming values.length = n and n > 1, then the 
	 * number of comparisons required is n + ceiling(log(n)) - 2.
	 * 
	 * Note that there is extra storage required for this solution.
	 * 
	 *   1. Initial 'winners' array of size Ceiling(n/2)
	 *   2. The losers hashtable, which contains Ceiling(n/2) entries, and the total number
	 *      of losers sums to n-1
	 *      
	 * For this algorithm, there is definitely a trade-off between time and space.
	 */
	public static int[] tournament(int[] A) {
		if (A.length < 2) {
			throw new IllegalArgumentException ("Not enough values.");
		}

		numComparisons = 0;

		// populate winners and reuse array to save storage
		int N = A.length;
		int[] winner = new int[N-1];
		int[] loser = new int[N-1];
		int[] prior = new int[N-1];
		for (int i = 0; i < N-1; i++) { prior[i] = -1; }

		int idx = 0;
		for (int i = 0; i < N-1; i += 2) {
			numComparisons++;
			if (A[i] < A[i+1]) {
				winner[idx] = A[i+1];
				loser[idx] = A[i];
			} else {
				winner[idx] = A[i];
				loser[idx] = A[i+1];
			}

			idx += 1;
		}
		int oddOneOut = 0;
		boolean hasOddOneOut = false;
		if (N % 2 == 1) {
			oddOneOut = A[N-1];
			hasOddOneOut = true;
		}

		// pair up subsequent winners and record priors
		int m = 0;
		while (idx < N-1) {
			numComparisons++;
			if (winner[m] < winner[m+1]) {
				winner[idx] = winner[m+1];
				loser[idx]  = winner[m];
				prior[idx]  = m+1;
			} else {
				winner[idx] = winner[m];
				loser[idx]  = winner[m+1];
				prior[idx]  = m;
			}
			
			m += 2;
			idx++;
		}
		
		// find where the second largest is hiding.
		m = N-2;
		int largest = winner[m];
		int next = loser[m];
		
		if (hasOddOneOut) {
			if (oddOneOut > largest) {
				numComparisons++;
				next = largest;
				largest = oddOneOut;
			} else if (oddOneOut > next) {
				numComparisons += 2;
				next = oddOneOut;
			} else {
				numComparisons += 2; // DON'T FORGET!!!!
			}
		}
		
		m = prior[m];
		while (m >= 0) {
			numComparisons++;
			if (next < loser[m]) {
				next = loser[m];
			}
			m = prior[m];
		}
		
		return new int[] { largest, next};
	}

	public static void main(String[] args) {
		// most comparisons for 10,000 random trials.
		System.out.println("N\tMaxComparisons");
		for (int num = 2; num < 20; num++) {
			int max = 0;
			int[] most = null;
			for (int t = 0; t < 10000; t++) {
				int[] sample = StdRandom.permutation(num);
				int check[] = tournament(sample);
				if (check[0] != num-1 && check[1] != num-2) {
					System.err.println("INVALID RESULTS: ");
				}
				if (numComparisons > max) {
					max = numComparisons;
					most = sample;
				}
			}
			System.out.println(num + "\t" + max);
		}

		System.out.println("\nThe following table shows trends more clearly.\n");
		
		// most comparisons for 1000 random trials.
		System.out.println("N\tMaxComparisons");
		for (int num = 2; num < 4096; num *= 2) {
			int max = 0;
			int[] most = null;
			for (int t = 0; t < 100; t++) {
				int[] sample = StdRandom.permutation(num);
				int check[] = tournament(sample);
				if (check[0] != num-1 && check[1] != num-2) {
					System.err.println("INVALID RESULTS: ");
				}
				if (numComparisons > max) {
					max = numComparisons;
					most = sample;
				}
			}
			System.out.println(num + "\t" + max);
		}
	}
}
