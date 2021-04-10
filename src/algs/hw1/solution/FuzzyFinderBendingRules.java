package algs.hw1.solution;

import algs.hw1.Coordinate;
import algs.hw1.FuzzySquare;
import algs.hw1.api.IFuzzySquareFinder;

/**
 * Finds a way to apply binary array search to the problem.
 * 
 * Work the right edge, ignoring everything else...
 * 
 * BENDS RULES: AV-2: SINCE SEARCH is always increasing, remember LAST position
 * found, and start from there... For example, if your last target was successfully 
 * found in the RIGHTMOST column, then the next target will have to be in the 0th
 * column in the next row...
 */
public class FuzzyFinderBendingRules implements IFuzzySquareFinder {
	int last_row = 0;
	int last_col = 0;
	int num_found = 0;
	
	/**
	 * Return the Coordinate(r,c) where target exists. If it is not in 
	 * the 2D array, return null.
	 * 
	 * Now coming into this, we know location where we last were found, and since
	 * numbers are searched in order....
	 */
	public Coordinate find(FuzzySquare fs, int target) {
		if (num_found == fs.N*fs.N) { return null; }
		
		int result = fs.probe3x3(last_row+1, last_col+1, target);
		if (result != FuzzySquare.FOUND) { return null; }
		
		// FOUND!
		Coordinate found = new Coordinate(last_row, last_col);
		last_col++;
		if (last_col == fs.N) {
			last_row++;
			last_col = 0;
		}
		
		num_found++;
		return found;
	}
	public static void main(String[] args) {
		for (int i = 3; i < 20; i++) {
			FuzzySquare fs = new FuzzySquare(i, 99);
			fs.solver(new FuzzyFinderBendingRules());
			System.out.println(i + "\t" + fs.getNumProbes());
		}
	}
}

