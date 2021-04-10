package algs.hw1.solution;

import algs.hw1.Coordinate;
import algs.hw1.Slicer;
import algs.hw1.api.ISlicerFinder;

public class SlicerFinder implements ISlicerFinder {

	/**
	 * Return the Coordinate(r,c) where target exists.
	 */
	public Coordinate find(Slicer s, int target) {
		int loRow = 0;
		int hiRow = s.N-1;

		// find the row in which value exists.
		while (loRow < hiRow) {
			int mid = (loRow + hiRow) / 2;
			if (s.inTop(mid, target)) {
				hiRow = mid;
			} else {
				loRow = mid+1;
			}
		}
		
		int loCol = 0;
		int hiCol = s.N-1;
		
		while (loCol < hiCol) {
			int mid = (loCol + hiCol) / 2;
			if (s.inLeft(mid, target)) {
				hiCol = mid;
			} else {
				loCol = mid+1;
			}
		}
		
		return new Coordinate(hiRow, hiCol);
	}

	public static void main(String[] args) {
		for (int i = 1; i < 20; i++) {
			Slicer s = new Slicer(i, 99);
			s.solver(new SlicerFinder());
		
			System.out.println(i + "\t" + s.getNumProbes());
		}
		System.out.println();
		
		for (int n = 1; n < 65; n*=2) {
			Slicer s = new Slicer(n, 99);
			int numProbes = s.solver(new SlicerFinder());
			System.out.println(n + "\t" + numProbes);
		}
	}
}
