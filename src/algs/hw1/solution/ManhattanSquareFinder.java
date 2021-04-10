package algs.hw1.solution;

import algs.hw1.Coordinate;
import algs.hw1.ManhattanSquare;
import algs.hw1.api.IManhattanSquareFinder;

public class ManhattanSquareFinder implements IManhattanSquareFinder {
	
	/** Return the Coordinate of location in ManhattanSquare containing target. */
	public Coordinate find(ManhattanSquare ms, int target) {
		// start at upper quadrant
		int N = ms.N;
		int dist = ms.distance(0, 0, target);
		if (dist == 0) { return new Coordinate(0,0); }
		if (dist == 2*(ms.N-1)) { return new Coordinate(N-1, N-1); }  // HAH!
		int col = ms.N-1;
		int distD = ms.distance(0, col, target);
		if (distD == 0) { return new Coordinate(0, col); }
		
		// now count inwards on diagonals
		int r = (dist + distD - N + 1)/2;
		int c = N - 1 - (distD - dist + N - 1)/2;
		
		return new Coordinate(r,c);
	}
	
	public static void main(String[] args) {
		for (int n = 1; n < 20; n++) {
			ManhattanSquare ms = new ManhattanSquare(n, 99);
			int numProbes = ms.solver(new ManhattanSquareFinder());
			System.out.println(n + "\t" + numProbes);
		}
	}
}
