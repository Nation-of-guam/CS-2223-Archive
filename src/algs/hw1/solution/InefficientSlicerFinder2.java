package algs.hw1.solution;

import algs.hw1.Coordinate;
import algs.hw1.Slicer;
import algs.hw1.api.ISlicerFinder;

/**
 * Outperform this inefficient Slicer finder.
 */
public class InefficientSlicerFinder2 implements ISlicerFinder {

	/** 
	 * Can you outperform this implementation?
	 */
	public Coordinate find(Slicer s, int target) {
		int foundR = -1;
		for (int r = 0; r < s.N; r++) { 
			
			// if in top R rows, but not in top R-1 rows, then it must be in row R
			if (s.inTop(r, target)) {
				foundR = r;
				break;
			}
		}
		if (foundR < 0) { return null; }
		
		int foundC = -1;
		for (int c = 0; c < s.N; c++) {
			// if not in left C columns, but in left C+1 columns, then it must be in row C
			if (s.inLeft(c, target)) {
				foundC = c;
				break;
			}
		}

		if (foundC < 0) { return null; }
		
		return new Coordinate(foundR, foundC);
	}	

	public static void main(String[] args) {
		for (int i = 2; i < 15; i++) {
			Slicer s = new Slicer(i, 99);
			s.solver(new InefficientSlicerFinder2());		
			System.out.println(i + "\t" + s.getNumProbes());
		}
	}
}
