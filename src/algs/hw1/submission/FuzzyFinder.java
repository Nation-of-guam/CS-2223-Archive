package algs.hw1.submission;

import algs.hw1.*;
import algs.hw1.api.*;

/**
 * Copy this class into your USERID.hw1 package and complete it.
 */
public class FuzzyFinder implements IFuzzySquareFinder {

	/**
	 * Return the Coordinate(r,c) where target exists. If it is not in 
	 * the 2D array, return null.
	 * 
	 * You can inspect the contents of the array for fs using the probe3x3() method.
	 */
	public Coordinate find(FuzzySquare fs, int target) {
		throw new RuntimeException("to be completed by student.");
	}	

	// You do not need to modify below this line.
	// ------------------------------------------
	public static void main(String[] args) {
		for (int i = 5; i < 40; i++) {
			FuzzySquare fs = new FuzzySquare(i, 99);
			fs.solver(new FuzzyFinder());
			System.out.println(i + "\t" + fs.getNumProbes());
		}
	}
}
