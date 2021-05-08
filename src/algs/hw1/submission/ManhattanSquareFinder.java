package algs.hw1.submission;

import algs.hw1.*;
import algs.hw1.api.*;

public class ManhattanSquareFinder implements IManhattanSquareFinder {

	/** 
	 * Return the Coordinate of location in ManhattanSquare containing target.
	 * 
	 * You can inspect the contents of the array for ms using the distance() method.
	 */
	public Coordinate find(ManhattanSquare ms, int target) {
		throw new RuntimeException("to be completed by student.");
	}	

	// You do not need to modify below this line.
	// ------------------------------------------
	public static void main(String[] args) {
		for (int n = 1; n < 10; n++) {
			ManhattanSquare ms = new ManhattanSquare(n, 99);
			int numProbes = ms.solver(new ManhattanSquareFinder());
			System.out.println(n + "\t" + numProbes);
		}
	}
}
