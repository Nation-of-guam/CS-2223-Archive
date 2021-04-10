package algs.hw1.solution;

import algs.hw1.Coordinate;
import algs.hw1.ManhattanSquare;
import algs.hw1.api.IManhattanSquareFinder;

public class ManhattanSquareImproved implements IManhattanSquareFinder {
	
	int quadrant = 0;
	
	/** Do computations for each of the four ordinal coordinates. */
	public Coordinate find(ManhattanSquare ms, int target) {
		// start at upper quadrant
		int N = ms.N;
		
		if (quadrant == 0) {
			int dist = ms.distance(0, 0, target);
			if (dist == 0) { 
				quadrant++; return new Coordinate(0,0); 
				}
			if (dist == 2*(ms.N-1)) { return new Coordinate(N-1, N-1); }  // HAH!
			
			int col = ms.N-1;
			int distD = ms.distance(0, col, target);
			if (distD == 0) { return new Coordinate(0, col); }
			
			// now count inwards on diagonals
			int r = (dist + distD - (N - 1))/2;
			int c = (N - 1) - (distD - dist + (N - 1))/2;
			
			return new Coordinate(r,c);
		}
		
		if (quadrant == 1) {
			int dist = ms.distance(0, N-1, target);
			if (dist == 0) { 
				return new Coordinate(0,N-1); 
				}
			if (dist == 2*(ms.N-1)) { return new Coordinate(N-1, 0); }  // HAH!
			
			int col = ms.N-1;
			int distD = ms.distance(N-1, N-1, target);
			if (distD == 0) { return new Coordinate(N-1, N-1); }
			
			// now count inwards on diagonals
			int c = (N - 1) - (dist + distD - (N - 1))/2;
			int r = (N - 1) - (distD - dist + (N - 1))/2;
			return new Coordinate(r,c);
		}

		return null;
	}
	
	public static void main(String[] args) {
		for (int n = 1; n < 20; n++) {
			ManhattanSquare ms = new ManhattanSquare(13, 99);
			int numProbes = ms.solver(new ManhattanSquareImproved());
			System.out.println(n + "\t" + numProbes);
		}
	}
}
