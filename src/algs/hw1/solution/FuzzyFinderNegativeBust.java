package algs.hw1.solution;

import java.util.Date;

import algs.hw1.Coordinate;
import algs.hw1.FuzzySquare;
import algs.hw1.api.IFuzzySquareFinder;

/**
 * Finds a way to apply binary array search to the problem.
 * 
 * Work the right edge, ignoring everything else...
 */
public class FuzzyFinderNegativeBust implements IFuzzySquareFinder {

	/**
	 * Return the Coordinate(r,c) where target exists. If it is not in 
	 * the 2D array, return null.
	 */
	public Coordinate find(FuzzySquare fs, int target) {
		int lo = 0;
		int hi = fs.N-2; // this is surprising. And significantly drops
		
		while (lo <= hi) {
			int mid = (lo+hi)/2;
			int result = fs.probe3x3(mid, fs.N, target);
			if (result == FuzzySquare.NOT_PRESENT) {
				System.out.println("not present shouldn't happen");
				return null;  // SHOULD NEVER HAPPEN, but just in case....
			}
			if (result == FuzzySquare.M1) {
				return findRow(fs, mid, target);
			} else if (result == FuzzySquare.M2) {
				return findRow(fs, mid+1, target);
			}
			
			if (result == FuzzySquare.ABOVE) {
				hi = mid;  // might not have guaranteed its exclusion
			} else if (result == FuzzySquare.BELOW) {
				lo = mid+1;  // this always happens....
			} else if (result == FuzzySquare.FOUND){
				int actual_r = mid-1;
				int actual_c = fs.N-1;
				if (fs.probe3x3(mid+1, actual_c, target) != FuzzySquare.FOUND) {
					return new Coordinate(actual_r, actual_c);
				} else {
					mid++;
					if (fs.probe3x3(mid+1, actual_c, target) != FuzzySquare.FOUND) {
						return new Coordinate(actual_r+1, actual_c);
					} else {
						return new Coordinate(actual_r+2, actual_c);
					}
				}
			}
		}
		
		return null;
	}
	
	/** 
	 * Narrowed down to a row. Do some further digging. Gist is to start binary array
	 * search OFF by one row, so you can get valid M1 and M2 responses, and act accordingly.
	 * Note you can ignore the N-1 column, since it wasn't FOUND there...
	 */
	private Coordinate findRow(FuzzySquare fs, int r, int target) {
		int lo = 0; 
		int hi = fs.N-2;
		
		while (lo <= hi) {
			int mid = (lo + hi)/2;
			
			int result = fs.probe3x3(r, mid, target);
			if (result == FuzzySquare.NOT_PRESENT) {
				return null;
			} else if (result == FuzzySquare.M1 || result == FuzzySquare.ABOVE) {
				hi = mid - 2;  // take advantage!
			} else if (result == FuzzySquare.M2 || result == FuzzySquare.BELOW) {
				lo = mid + 2;
			} else if (result == FuzzySquare.FOUND) {
				// in a 1x3 region... move right one by one until lost, then you know where it was
				int actual_r = r;
				int actual_c = mid-1;
				if (fs.probe3x3(r-1, mid+1, target) != FuzzySquare.FOUND) {
					return new Coordinate(actual_r, actual_c);
				} else {
					actual_c++;
					if (fs.probe3x3(r-1, mid+2, target) != FuzzySquare.FOUND) {
						return new Coordinate(actual_r, actual_c);
					} else {
						return new Coordinate(actual_r, actual_c+1);
					}
				}
			} else {
				System.out.println("UNEXPECTED HERE....");
				return null;
			}
		}
		
		return null;
	}
		

	public static void main(String[] args) {
//		FuzzySquare fs2 = new FuzzySquare(13, 99);
//		System.out.println(new Date());
//		while (fs2.getNumProbes() >= 0) {
//			fs2.probe3x3(0,0,0);
//		}
//		while (fs2.getNumProbes() < -8000) {
//			fs2.probe3x3(0,0,0);
//		}
//		System.out.println(new Date());
//		System.out.println(fs2.getNumProbes());
		
		for (int i = 3; i < 40; i++) {
			FuzzySquare fs = new FuzzySquare(i, 99);
			while (fs.getNumProbes() >= 0) {
				fs.probe3x3(0,0,0);
			}
			while (fs.getNumProbes() < -7652) {
				fs.probe3x3(0,0,0);
			}
			fs.solver(new FuzzyFinderNegativeBust());
			System.out.println(i + "\t" + fs.getNumProbes());
		}
	}
}

