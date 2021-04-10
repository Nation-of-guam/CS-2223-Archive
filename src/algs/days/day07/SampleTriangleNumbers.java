package algs.days.day07;

import edu.princeton.cs.algs4.StdOut;

public class SampleTriangleNumbers {
	public static void main(String[] args) {
		int N = 5;
		
		StdOut.println("Structure of Selection Sort");
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				StdOut.print("*");
			}
			StdOut.println();
		}
		StdOut.println("There are " + N*(N-1)/2 + " Stars or n*(n-1)/2 but FIVE lines of output.");
		StdOut.println();
		StdOut.println("Structure of Insertion Sort");
		for (int i = 0; i < N; i++) {
		    for (int j = i; j > 0 && true; j--) {
		    	StdOut.print("*");
		    }
		    StdOut.println();
		}
		StdOut.println("There are " + N*(N-1)/2 + " Stars or n*(n-1)/2 with FOUR lines of output.");
	}
}
