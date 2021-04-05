package algs.days.day03;

import java.util.Arrays;
import java.util.Hashtable;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdRandom;

/** 
 * Achieve Optimal number of comparisons, using additional space.
 * 
 * Note: This is getting ahead of ourselves, so don't worry if you have trouble following this code.
 * It uses programming concepts we won't get to until the 11th lecture. However, you should be
 * able to follow the essence of the logic of the algorithm, as outlined in class.
 */
public class FindMaxSecondLargestTournament {
	
	static int numComparisons = 0;
	
	/** For each integer, record those items that lost to it. */
	static Hashtable<Integer,Bag<Integer>> losers;
	
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
	public static int[] tournament(int[] values) {
		if (values.length < 2) {
			throw new IllegalArgumentException ("Not enough values.");
		}

		numComparisons = 0;
		losers = new Hashtable<>();
		
		// populate winners and reuse array to save storage
		int numWinners = (int) Math.ceil(values.length/2.0);
		int[] initial = new int[numWinners];
		
		int numToCheck = values.length;
		int[] winners = getWinners(values, initial, numToCheck, numWinners);
		
		// if more to check, whittle down the remaining candidates for top spot.
		while (numWinners > 1) {
			numToCheck = numWinners;
			numWinners = (int) Math.ceil(numWinners/2.0);
			winners = getWinners(winners, winners, numToCheck, numWinners);
		}
		
		int largest = winners[0];
		int next = 0;
		boolean firstOne = true;
		Bag<Integer> lost = losers.get(largest);
		for (int loser : lost) {
			if (firstOne) {
				next = loser;
				firstOne = false;
			} else {
				numComparisons++;
				if (loser > next) {
					next = loser;
				}
			}
		}
		
		return new int[] { largest, next};
	}
	
	/**
	 * Compute winners[] and update each winner's bag.
	 * 
	 * This reuses the storage of []winners, so only values up to numWinners are valid.
	 */
	static int[] getWinners(int[] values, int[] winners, int numToCheck, int numWinners) {
		int i = 0;
		for (i = 0; i < numToCheck; i += 2) {
			if (i == numToCheck - 1) {
				// ODD one out. Put in winners without any losers
				winners[numWinners-1] = values[i];
			} else {
				numComparisons++;
				int loser;
				int winner;
				if (values[i] > values[i+1]) {
					winner = winners[i/2] = values[i];
					loser = values[i+1];
				} else {
					winner = winners[i/2] = values[i+1];
					loser = values[i];
				}
					
				Bag<Integer> bag;
				if (losers.containsKey(winner)) {
					bag = losers.get(winner);
				} else {
					bag = new Bag<>();
					losers.put(winner, bag);
				}
				bag.add(loser);
			}
		}
		return winners;
	}
	
	public static void main(String[] args) {
		// most comparisons for 10,000 random trials.
		for (int num = 2; num < 20; num++) {
			int max = 0;
			int[] most = null;
			for (int t = 0; t < 10000; t++) {
				int[] sample = StdRandom.permutation(num);
				int check[] = tournament(sample);
				if (check[0] != num-1 && check[1] != num-2) {
					System.err.println("INVALID RESULTS: " + Arrays.toString(most));
				}
				if (numComparisons > max) {
					max = numComparisons;
					most = sample;
				}
			}
			System.out.println(num + "\t" + max + "\t" + Arrays.toString(most));
		}
		
		System.out.println();
	}
}
