package algs.hw1.solution;

import algs.hw1.Coordinate;
import algs.hw1.Slicer;

/**
 * Outperform this inefficient Slicer finder.
 */
public class InefficientSlicerFinder {

	/** 
	 * Complete the implementation of this method.
	 */
	public static Coordinate find(Slicer s, int target) {
		int foundR = -1;
		for (int r = s.N-1; r > 0; r--) {
			
			// if in top R rows, but not in top R-1 rows, then it must be in row R
			if (s.inTop(r, target) && !s.inTop(r-1, target)) {
				foundR = r;
				break;
			}
		}
		
		int foundC = -1;
		for (int c = s.N-1; c > 0; c--) {
			// if not in left C columns, but in left C+1 columns, then it must be in row C
			if (s.inLeft(c, target) && s.inLeft(c-1, target)) {
				foundC = c;
				break;
			}
		}
		
		return new Coordinate(foundR, foundC);
	}	

	public static void main(String[] args) {
		for (int i = 2; i < 15; i++) {
			Slicer s = new Slicer(i, 99);
			
			for (int t = 0; t < s.N*s.N; t++) {
				find(s, t);
			}
		
			System.out.println(i + "\t" + s.getNumProbes());
		}
	}
}



