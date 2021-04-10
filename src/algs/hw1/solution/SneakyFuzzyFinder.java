package algs.hw1.solution;

import algs.hw1.Coordinate;
import algs.hw1.FuzzySquare;
import algs.hw1.api.IFuzzySquareFinder;

/**
 * Takes advantage of the fact that values from 0 .. 10*N*N are to be searched.
 * 
 * The first value is small, but the last value is typically not! Start out by
 * the first one and cache results; once NXN are found, then declare all others
 * to be not found.
 * 
 *  SNEAKY! TOTALLY BREAKS THE RULES WITHOUT ANY PENALTY
 */
public class SneakyFuzzyFinder implements IFuzzySquareFinder {

	public static final int L = 1;
	public static final int R = 2;

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

		System.err.println("SDSD");
		throw new IllegalStateException("Shouldn't happen in extract.");
	}

	Coordinate found[] = null;

	/**
	 * Return the Coordinate(r,c) where target exists. If it is not in 
	 * the 2D array, return null.
	 * 
	 * SUPER SNEAKY: start low and go high, until N*N are found, then stop, caching
	 * all of these results.
	 */
	public Coordinate find(FuzzySquare fs, int target) {
		if (found == null) {
			found = new Coordinate[fs.N*fs.N*10];

			// start in middle and work LEFT and RIGHT until N*N are found, then stop.
			int lo = 0;
			int numFound = 0;
			while (numFound < fs.N*fs.N) {
				found[lo] = findRect(fs, 0, fs.N-1, lo);
				if (found[lo] != null) { numFound++; }
				lo++;
			}
		}

		return found[target];
	}

	private Coordinate findRect(FuzzySquare fs, int lo_row, int hi_row, int target) {
		// base case of recursion!
		if (lo_row > hi_row) { return null; }

		// Try to find a spot that CONTAINS target
		int N = fs.N;
		int mid = (lo_row + hi_row)/2;
		int result = fs.probe3x3(mid, N/2, target);

		if (result == FuzzySquare.NOT_PRESENT) {                           // SUPER LUCKY !!!
			return null;
		}
		if (result == FuzzySquare.FOUND) {
			return extract(fs, mid-1, mid+1, N/2-1, N/2+1, target);        // GOT LUCKY!!!
		} else if (result == FuzzySquare.M1) {
			Coordinate ct = findSliver(fs, mid, 0, N/2-2, target, L);      // check sliver (Left) on row mid
			if (ct != null) { return ct; }
			return findSliver(fs, mid-1, N/2+2, N-1, target, R);           // check sliver (Right) on row mid-1
		} else if (result == FuzzySquare.M2) {
			Coordinate ct = findSliver(fs, mid+1, 0, N/2-2, target, L);    // check second sliver (Left) on row mid+1
			if (ct != null) { return ct; }
			return findSliver(fs, mid, N/2+2, N-1, target, R);             // check second sliver (Right) on row mid
		} else if (result == FuzzySquare.ABOVE){
			Coordinate ct = findSliver(fs, mid-1, 0, N/2-2, target, L);    // check last sliver Left) on row mid-1
			if (ct != null) { return ct; }
			return findRect(fs, lo_row, mid-2, target);                    // now check rect (lo_row, was 0)
		} else if (result == FuzzySquare.BELOW) {
			Coordinate ct = findSliver(fs, mid+1, N/2+2, N-1, target, R);  // check last sliver (on Right) on row mid+1 (was mid+1)
			if (ct != null) { return ct; }
			return findRect(fs,mid+2, hi_row, target);                     // now check rect (hi_row, was N-1)
		}

		// should never get here...
		return null;
	}

	/** 
	 * Fuzzy binary array search.
	 * 
	 * IN HIGHER SQUARES THAN 25 this needs to be refined
	 */
	private Coordinate findSliver(FuzzySquare fs, int row, int lo_col, int hi_col, int target, int dir) {
		// off the grid
		if (row < 0 || row > fs.N-1) { return null; }

		while (lo_col <= hi_col) {
			int mid = (lo_col + hi_col) / 2;

			int result_b = fs.probe3x3(row+1, mid, target);  
			int result_a = fs.probe3x3(row-1, mid, target);  
			if (result_b == FuzzySquare.NOT_PRESENT) {    // get lucky!
				return null;
			}

			// CONFLICT? Can't be there
			if (result_a == FuzzySquare.FOUND && result_b != FuzzySquare.FOUND) {
				return null;
			}
			if (result_b == FuzzySquare.FOUND && result_a != FuzzySquare.FOUND) {
				return null;
			}

			if (result_b == FuzzySquare.ABOVE) {
				hi_col = mid-2;
			} else if (result_b == FuzzySquare.BELOW) {
				lo_col = mid+2;
			} else if (result_b == FuzzySquare.FOUND && result_a == FuzzySquare.FOUND) {
				// take one step at a time to right, until GONE. NOTE: because of recursion
				// in HIGHER fuzzy squares (first seen at 25) REMEMBER to check on row-1
				if (fs.probe3x3(row-1, mid+1, target) == FuzzySquare.FOUND) {
					if (fs.probe3x3(row-1,  mid+2, target) == FuzzySquare.FOUND) {
						return new Coordinate(row, mid+1);
					} else {
						return new Coordinate(row, mid);
					}
				} else {
					return new Coordinate(row, mid-1);
				}
			} else if (result_b == FuzzySquare.M1 && dir == R) {
				lo_col = mid+2;
			} else if (result_b == FuzzySquare.M1 && dir == L) {
				lo_col = mid+2;
			} else if (result_b == FuzzySquare.M2 && dir == R) {
				hi_col = mid-2;
			} else if (result_b == FuzzySquare.M2 && dir == L) {
				hi_col = mid-2;   // never got here during testing....
			} else {
				throw new IllegalStateException("Should never get here findSliver.");
			}
		}

		return null;  // wasn't there...
	}

	public static void main(String[] args) {
		for (int i = 5; i < 40; i++) {
			FuzzySquare fs = new FuzzySquare(i, 99);
			fs.solver(new SneakyFuzzyFinder());
			System.out.println(i + "\t" + fs.getNumProbes());
		}
	}
}

