package aiburns.hw1;

import algs.hw1.api.*;
import algs.hw1.Coordinate;
import algs.hw1.ManhattanSquare;

public class ManhattanSquareFinder implements IManhattanSquareFinder {
	int size, midPoint;
	/**
	 * Return the Coordinate of location in ManhattanSquare containing target.
	 *
	 * You can inspect the contents of the array for ms using the distance() method.
	 */
	public Coordinate find(ManhattanSquare ms, int target) {
		size = ms.N;
		Coordinate toReturn;
		midPoint = middle(size-1);

		//This is to make sure that Slicer is large enough to contain target
		if ((Math.pow(size, 2) - 1) < target){
			throw new IndexOutOfBoundsException("Target: " + target + " is outside of the bounds.  " +
					"Max target = " + (Math.pow(size, 2) - 1));
		} if (size < 3){
			return size == 1 ? new Coordinate(0,0) : findTwoByTwo(ms, target);
		}

		toReturn = findCoords(ms, target);


		return toReturn;
	}


	public Coordinate findCoords(ManhattanSquare ms, int target){
		int col, row, closer;
		final Coordinate[] finders = {new Coordinate(0, 0),
				new Coordinate(0, size-1)};
		int[] distances = new int[2];


		for (int i = 0; i < finders.length; i++) {
			distances[i] = ms.distance(finders[i].row, finders[i].column, target);
			if (distances[i] == 0){
				return finders[i];
			} else if (distances[i] == 2*(size-1)){
				return new Coordinate((size-1), (size-finders[i].column-1));
			}
		}
		if (distances[0]>distances[1]){
			closer = 1;
		} else if (distances[0] == distances[1]){
			return new Coordinate(distances[0]-midPoint, midPoint);
		} else {
			closer = 0;
		}


		if (distances[0] + distances[1] == finders[1].column){
			col = distances[0];
			row = 0;
		} else if (closer == 0){
			col = midPoint - middle(distances[1] - distances[0]);

			row = distances[0] - col;
		} else {
			col = midPoint + middle(distances[0] - distances[1]);
			if (size % 2 == 0){
				col--;
			}

			row = distances[0] - col;
		}

		return new Coordinate(row, col);
	}

	public static Coordinate addCord(Coordinate a, int r, int c){
		return new Coordinate((a.row + r), (a.column + c));
	}

	public Coordinate findTwoByTwo(ManhattanSquare ms, int target) {
		int closer;
		final Coordinate[] finders = {new Coordinate(0, 0),
				new Coordinate(0, size - 1)};
		int[] distances = new int[2];

		for (int i = 0; i < finders.length; i++) {
			distances[i] = ms.distance(finders[i].row, finders[i].column, target);
			if (distances[i] == 0) {
				return finders[i];
			}
		}

		if (distances[0] > distances[1]) {
			closer = 1;
		} else if (distances[0] == distances[1]) {
			return new Coordinate(distances[0] - midPoint, midPoint);
		} else {
			closer = 0;
		}

		return addCord(finders[closer], 1, 0);
	}

	/**
	 * This method returns the middle number inbetween 1 and a, if a = 1, returns 0
	 * Examples:
	 * middle(0) -> 0
	 * middle(1) -> 0
	 * middle(2) -> 1
	 * middle(3) -> 1
	 * middle(4) -> 2
	 * middle(5) -> 2
	 * @param a the number to split
	 * @return the middle number
	 */
	public static int middle(int a){
		return (int) Math.round((double) a/2);
	}

	// You do not need to modify below this line.
	// ------------------------------------------
	public static void main(String[] args) {
		for (int n = 1; n < 15; n++) {
			ManhattanSquare ms = new ManhattanSquare(n, 99);
			int numProbes = ms.solver(new ManhattanSquareFinder());
			System.out.println(n + "\t" + numProbes);
		}
	}
}
