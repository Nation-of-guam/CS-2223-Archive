package algs.hw1.solution;

import algs.hw1.Coordinate;
import algs.hw1.FuzzySquare;
import algs.hw1.api.IFuzzySquareFinder;

/**
 * Reacts to ABOVE and BELOW by using binary array search.
 * 
 * When M1 or M2, then slide to the right using binary array search.
 */
public class PoorSearchFuzzyFinder implements IFuzzySquareFinder {

	/**
	 * Once we have a 3x3, find the actual spot. YOU KNOW target is in here somewhere... 
	 * 
	 * Break into stages. rows/cols must all be VALID
	 * 
	 *   ABC
	 *   DEF
	 *   GHI
	 *   
	 * search on A
	 * 
	 */
	Coordinate extract(FuzzySquare fs, int lo_row, int hi_row, int lo_col, int hi_col, int target) {
		int result = fs.probe3x3(lo_row, lo_col, target);
		if (result == FuzzySquare.OUT_OF_BOUNDS) {
			// only the rightmost column has anything
			int result3 = fs.probe3x3(lo_row, lo_col+1, target);
			if (result3 != FuzzySquare.FOUND) {
				return new Coordinate(hi_row, hi_col);   // lower corner
			} else {
				int result4 = fs.probe3x3(lo_row-1, lo_col+1, target);
				if (result4 != FuzzySquare.FOUND) {
					return new Coordinate(hi_row-1, hi_col);
				} else {
					return new Coordinate(hi_row-2, hi_col);
				}
			}
		}
		
		if (result == FuzzySquare.FOUND) {   // in [0][0], [0][1], [1][0], [1][1]
			int result2 = fs.probe3x3(lo_row, lo_col-1, target);
			if (result2 == FuzzySquare.FOUND) {   // in [0][0] or [1][0]
				if (fs.probe3x3(lo_row-1, lo_col-1, target) == FuzzySquare.FOUND) {   
					return new Coordinate(lo_row, lo_col);              // in [0][0]
				} else {
					return new Coordinate(lo_row+1, lo_col);            // in [1][0]
				}
			} else if (result2 == FuzzySquare.M2) {                     // in [0][1]
				return new Coordinate(lo_row, lo_col+1);
			} else if (result2 == FuzzySquare.BELOW) {                  // in [1][1]
				return new Coordinate(lo_row+1, lo_col+1);
			} else if (result2 == FuzzySquare.OUT_OF_BOUNDS) {
				return new Coordinate(lo_row+1, lo_col+1);
			}
		} else if (result == FuzzySquare.M2) {  //was M1 GTH
			return new Coordinate(lo_row, lo_col+2);                    // in [0][2]
		} else {
			int result2 = fs.probe3x3(lo_row+2, lo_col-1, target);
			if (result2 == FuzzySquare.FOUND) {
				return new Coordinate(lo_row+2, lo_col);                // in [2][0]
			} else if (result2 == FuzzySquare.M1) {  
				return new Coordinate(lo_row+1, lo_col+2);              // in [1][2]
			} else if (result2 == FuzzySquare.M2) {                     // must be in [2][1] or [2][2]
				//if (fs.probe3x3(lo_row+1, lo_col+1, target) == FuzzySquare.FOUND) {
				if (fs.probe3x3(lo_row+2, lo_col, target) == FuzzySquare.FOUND) {
					return new Coordinate(lo_row+2, lo_col+1);          // in [2][1]
				} else {
					return new Coordinate(lo_row+2, lo_col+2);          // in [2][2]
				}
			}
		} 
		
		throw new IllegalStateException("Shouldn't happen in extract.");
	}
	
	/**
	 * Return the Coordinate(r,c) where target exists. If it is not in 
	 * the 2D array, return null.
	 */
	public Coordinate find(FuzzySquare fs, int target) {

		// Start in middle and try to find M1 or M2, which are the sweet spot.
		
		int lo = 0;
		int hi = fs.N-1;
		int mid = 0;
		
		int response = 0;
		while (lo <= hi) {
			mid = (lo+hi)/2;
			response = fs.probe3x3(mid, fs.N/2, target);
			
			if (response == FuzzySquare.ABOVE) {
				hi = mid-2;
			} else if (response == FuzzySquare.BELOW) {
				lo = mid+2;
			} else if (response == FuzzySquare.FOUND) {
				return extract(fs, mid-1, mid+1, fs.N/2-1, fs.N/2+1, target);
			} else if (response == FuzzySquare.M1) {
				Coordinate c = extractSliver(fs, mid-1, fs.N/2+1, fs.N-1, target, true);
				if (c != null) { return c; }
				return extractSliver(fs, mid, 0, fs.N/2-1, target, false);
			} else if (response == FuzzySquare.M2) {
				Coordinate c = extractSliver(fs, mid, fs.N/2+1, fs.N-1, target, true);
				if (c != null) { return c; }
				return extractSliver(fs, mid+1, 0, fs.N/2-1, target, false);
			} else {
				return null;
			}
		}
		
		// one last check?
		response = fs.probe3x3(hi, fs.N/2, target);
		
		if (response == FuzzySquare.BELOW) {
			return null;
		} else if (response == FuzzySquare.FOUND) {
			return extract(fs, hi-1, hi+1, fs.N/2-1, fs.N/2+1, target);
		} else if (response == FuzzySquare.M1) {
			Coordinate c = extractSliver(fs, mid-1, fs.N/2+1, fs.N-1, target, true);
			if (c != null) { return c; }
			return extractSliver(fs, mid, 0, fs.N/2-1, target, false);
		} else if (response == FuzzySquare.M2) {
			Coordinate c = extractSliver(fs, hi, fs.N/2+1, fs.N-1, target, true);
			if (c != null) { return c; }
			return extractSliver(fs, hi+1, 0, fs.N/2-1, target, false);
		} else {
			return null;
		}
	}

	private Coordinate extractSliver(FuzzySquare fs, int focus_row, int lo_col, int hi_col, int target, boolean right) {
		if (focus_row < 0 || focus_row > fs.N-1) { return null; }
		if (lo_col > hi_col) { return null; } // terminate the recursion!
		
		// we go up a row, to be able to differentiate M1/M2
		if (right) {
			int mid = (lo_col+hi_col)/2;
			int result = fs.probe3x3(focus_row, mid, target);
			if (result == FuzzySquare.NOT_PRESENT) { return null; }
			
			if (result == FuzzySquare.FOUND) {
				return extract(fs, focus_row-1, focus_row+1, mid-1, mid+1, target);
			}
			
			if (result == FuzzySquare.M1) {
				Coordinate c = extractSliver(fs, focus_row-1, mid+1, hi_col, target, true);
				if (c != null) { return c; }
				return extractSliver(fs, focus_row, 0, mid-1, target, false);
			}
			
			if (result == FuzzySquare.M2) {
				Coordinate c = extractSliver(fs, focus_row, mid+1, hi_col, target, true);
				if (c != null) { return c; }
				return extractSliver(fs, focus_row+1, 0, mid-1, target, false);
			}
		} else {
			int mid = (lo_col+hi_col)/2;
			int result = fs.probe3x3(focus_row, mid, target);
			if (result == FuzzySquare.NOT_PRESENT) { return null; }
			
			if (result == FuzzySquare.FOUND) {
				return extract(fs, focus_row-1, focus_row+1, mid-1, mid+1, target);
			}
			
			if (result == FuzzySquare.M1) {
				Coordinate c = extractSliver(fs, focus_row-1, mid+1, hi_col, target, true);
				if (c != null) { return c; }
				return extractSliver(fs, focus_row, 0, mid-1, target, false);
			}
			
			if (result == FuzzySquare.M2) {
				Coordinate c = extractSliver(fs, focus_row, mid+1, hi_col, target, true);
				if (c != null) { return c; }
				return extractSliver(fs, focus_row+1, 0, mid-1, target, false);
			}
		}
		
		// nothing?
		return null;
	}

	public static void main(String[] args) {
		FuzzySquare afs = new FuzzySquare(5, 99);
		Coordinate found = new PoorSearchFuzzyFinder().find(afs, 85);
		System.out.println(afs.getNumProbes());
		for (int i = 5; i < 40; i++) {
			FuzzySquare fs = new FuzzySquare(i, 99);
			fs.solver(new PoorSearchFuzzyFinder());
			System.out.println(i + "\t" + fs.getNumProbes());
		}
	}
}

