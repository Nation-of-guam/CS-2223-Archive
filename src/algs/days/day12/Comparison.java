package algs.days.day12;

import edu.princeton.cs.algs4.StdOut;

public class Comparison {
	public static void main(String[] args) {
		int max = 524288;
		
		HeapSort.verbose = false;
		HeapSortTopDown.verbose = false;
		StdOut.println("      N          BuildLHS	Less HS         Exch HS      BuildLHS-TD	Less HS-TD      Exch HS-TD");
		for (int n = 4; n <= max; n *= 2) {
			Double[] d1 = new Double[n];
			Double[] d2 = new Double[n];
			for (int i = 0; i < n; i++) {
				d1[i] = d2[i] = Math.random()*n;
			}
			
			HeapSort.sort(d1);
			HeapSortTopDown.sort(d2);
			
			for (int i = 0; i < n; i++) {
				if (d1[i] != d2[i]) {
					System.err.println("NOT SAME RESULT");
				}
			}
			
			StdOut.printf("%8d\t%8d\t%8d\t%8d\t%8d\t%8d\t%8d\t\n", n, HeapSort.numBuildLess, HeapSort.numLess, HeapSort.numExch, HeapSortTopDown.numBuildLess, HeapSortTopDown.numLess, HeapSortTopDown.numExch);
		}
		
	}
}
