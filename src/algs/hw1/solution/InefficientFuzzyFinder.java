package algs.hw1.solution;

import algs.hw1.Coordinate;
import algs.hw1.FuzzySquare;
import algs.hw1.api.IFuzzySquareFinder;

/**
 * Scans rows 1..N-1 and columns 1..N-1 until a hit is found;
 * 
 * then zooms in and tries up to eight other probes to isolate.
 */
public class InefficientFuzzyFinder implements IFuzzySquareFinder {

	/**
	 * Return the Coordinate(r,c) where target exists. If it is not in 
	 * the 2D array, return null.
	 */
	public Coordinate find(FuzzySquare fs, int target) {
		// Try to find a spot that CONTAINS target
		boolean found = false;
		int r = 0;
		int c = 0;
		for (r = 1; r < fs.N - 1; r++) {
			for (c = 1; c < fs.N - 1; c++) {
				int response = fs.probe3x3(r, c, target);
				if (response == FuzzySquare.FOUND) {
					found = true;
					break;
				}
			}
			if (found) { break; }
		}
		
		if (!found) { return null; }
		
		// go back (2,2) and try each until it is found
		for (int dr = -2; dr <= 0; dr++) {
			for (int dc = -2; dc <= 0; dc++) {
				int response = fs.probe3x3(r+dr, c+dc, target);
				if (response == FuzzySquare.FOUND) {
					return new Coordinate(r+dr+1, c+dc+1);
				}
			}
		}
		
		// should never get here...
		return null;
	}

	public static void main(String[] args) {
		for (int i = 3; i < 40; i++) {
			FuzzySquare fs = new FuzzySquare(i, 99);
			fs.solver(new InefficientFuzzyFinder());
			System.out.println(i + "\t" + fs.getNumProbes());
		}
	}
}

