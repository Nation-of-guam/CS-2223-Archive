package algs.days.day03;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class CompareAlgorithms {
	
	public static int[] findLargestAndSecondLargest(int[] A) {
		int largest = A[0];
		int next = A[1];
		if (next > largest) {
			int tmp = largest;
			largest = next;
			next = tmp;
		}
		
		for (int i = 2; i < A.length; i++) {
			if (A[i] > largest) {
				next = largest;
				largest = A[i];
			} else if (A[i] > next) {
				next = A[i];
			}
		}

		return new int[] { largest, next}; 
	}
	
	public static void main(String[] args) {
		System.out.println("N\tTournT\tNaiveT");
		for (int n = 1024; n <= 4194304; n *= 2) {
			int[] A = StdRandom.permutation(n);
			Stopwatch tr = new Stopwatch();
			int tournamentResult[] = null;
			for (int i = 0; i < 100; i++) {
				tournamentResult = FindMaxSecondLargestTournament.tournament(A);
			}
			double tr_time = tr.elapsedTime();
			
			Stopwatch naive = new Stopwatch();
			int regularResult[] = null;
			for (int i = 0; i < 100; i++) {
				regularResult = findLargestAndSecondLargest(A);
			}
			double naive_time = naive.elapsedTime();
			
			if ((tournamentResult[0] == regularResult[0]) &&
				(tournamentResult[1] == regularResult[1])) {
				System.out.println(String.format("%d\t%.3f\t%.3f", n, tr_time, naive_time));
			} else {
				System.err.println("DO NOT MATCH!");
			}
		}
	}
}
