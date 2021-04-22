package algs.days.day02;

import edu.princeton.cs.algs4.StdRandom;

public class FiniteOrInfinite {
	
	static long trial (long v) {
		long numLoops = 0;
		while (v != 1) {
			numLoops++;
			if (v % 2 == 0) {
				v = v / 2;
			} else {
				v = 3*v + 1;
			}
		}
		
		return numLoops;
	}
	
	public static void main(String[] args) {
		
		long max = Integer.MIN_VALUE;
		
		for (int t = 0; t < 10000000; t++) { 
			long v = StdRandom.uniform(Integer.MAX_VALUE);
			long numLoops = trial(v);
			if (numLoops > max) {
				max = numLoops;
				System.out.println(v + " needs " + max + " loops.");
			}
		}
	}
}
