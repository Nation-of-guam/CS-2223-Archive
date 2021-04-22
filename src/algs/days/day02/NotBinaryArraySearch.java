package algs.days.day02;

import algs.days.day01.BinaryIntSearch;
import edu.princeton.cs.algs4.StopwatchCPU;

public class NotBinaryArraySearch {
	
	/**
	 * This is not Binary Array Search. Can you see why?
	 */
	static boolean contains(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc < 0) {
				low++;
			} else if (rc > 0) {
				high--;
			} else {
				return true;
			}
		}
		return false;
	}
	
	static void runDay02Trial(int N) {
		int[] vals = new int[N];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = i;
		}
		
		for (int t = 0; t < 100; t++) {
			for (int i = 0; i < vals.length; i++) {
				if (!contains(vals, i)) {
					System.err.println("FAILED on " + i);
				}
			}
		}
	}
	
	static void runDay01Trial(int N) {
		int[] vals = new int[N];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = i;
		}
		BinaryIntSearch bis = new BinaryIntSearch();
		
		for (int t = 0; t < 100; t++) {
			for (int i = 0; i < vals.length; i++) {
				if (!bis.contains(vals, i)) {
					System.err.println("FAILED on " + i);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int N = 2048;
		while (N <= 16384) {
			StopwatchCPU timer = new StopwatchCPU();
			runDay01Trial(N);
			double trial1 = timer.elapsedTime();
			
			double here = timer.elapsedTime();
			runDay02Trial(N);
			double trial2 = timer.elapsedTime() - here;
			
			System.out.println(String.format("%d\t%f\t%f", N, trial1, trial2));
			N *= 2;
		}
	}
}
