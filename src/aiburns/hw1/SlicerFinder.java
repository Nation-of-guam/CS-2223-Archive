package aiburns.hw1;

import algs.hw1.api.*;
import algs.hw1.Coordinate;
import algs.hw1.Slicer;

/**
 * Copy this class into your project and complete its implementation
 */
public class SlicerFinder implements ISlicerFinder {
	private int size;

	/**
	 *
	 * @param s The Slicer to search
	 * @param target The integer to be found
	 * @return the Coordinates of target in s' Array
	 * @throws IndexOutOfBoundsException When the Slicer is not large enough to contain target
	 *
	 */
	public Coordinate find(Slicer s, int target) {
		size = s.N;
		final int x = middle(size-1);
		final int y = middle(size-1);
		Coordinate toReturn = new Coordinate(x, y);

		//This is to make sure that Slicer is large enough to contain target
		if ((Math.pow(size, 2) - 1) < target){
			throw new IndexOutOfBoundsException("Target: " + target + " is outside of the bounds.  " +
					"Max target = " + (Math.pow(size, 2) - 1));
		} else if (size == 1){
			return new Coordinate(0,0);
		}

		toReturn = findSideways(s, target, x, toReturn);
		toReturn = findVertical(s, target, y, toReturn);

		return toReturn;
	}

	public Coordinate findSideways(Slicer s, int target, int jumpX, Coordinate coord){
		Coordinate toReturn = coord;
		final boolean toTheLeft = s.inLeft(coord.column, target);
		int midX = middle(jumpX);
		if (toTheLeft){
			if (coord.column == 0) {
				return coord;
			}
			if (addCord(coord, -midX, 0).column >= 0 ){
				if (jumpX > 1){
					toReturn = findSideways(s, target, midX, addCord(coord, -midX, 0));
				} else if (leftZero(s, target, addCord(toReturn, -1, 0))){
					toReturn = addCord(toReturn, -1, 0);
				}
			} else if (addCord(coord, middle(middle(jumpX+1)), 0).column >= 0) {
				if (middle(middle(jumpX+1)) > 1){
					toReturn = findSideways(s, target, middle(middle(jumpX+1)), addCord(coord, -middle(middle(jumpX+1)), 0));
				} else if (leftZero(s, target, addCord(toReturn, -1, 0))){
					toReturn = addCord(toReturn, -1, 0);
				}
			}
		} else {
			if (coord.column == size-1) {
				return coord;
			}
			if (addCord(coord, midX, 0).column < size){
				if (jumpX > 1){
					toReturn = findSideways(s, target, midX, addCord(coord, midX, 0));
				} else if (leftZero(s, target, addCord(toReturn, 1, 0))){
					toReturn = addCord(toReturn, 1, 0);
				}
			} else if (addCord(coord, middle(midX), 0).column < size) {
				if (middle(midX) > 1){
					toReturn = findSideways(s, target, middle(midX), addCord(coord, middle(midX), 0));
				} else if (leftZero(s, target, addCord(toReturn, 1, 0))){
					toReturn = addCord(toReturn, 1, 0);
				}
			}
		}
		return toReturn;
	}

	public boolean leftZero(Slicer s, int target, Coordinate coord){
		int i = 0;
		try{
			return s.inLeft(coord.column, target);
		} catch (IndexOutOfBoundsException e){
			return false;
		}
	}

	public Coordinate findVertical(Slicer s, int target, int jumpY, Coordinate coord){
		Coordinate toReturn = coord;
		final boolean up = s.inTop(coord.row, target);
		int midy = middle(jumpY);
		if (up){
			if (coord.row == 0) {
				return coord;
			}
			if (addCord(coord, 0, -midy).row >= 0){
				if (jumpY > 1){
					toReturn = findVertical(s, target, midy, addCord(coord, 0, -midy));
				} else if (upZero(s, target, addCord(toReturn, 0, -1))){
					toReturn = addCord(toReturn, 0, -1);
				}
			} else if (addCord(coord, 0, -middle(midy)).row > 0) {
				if (middle(midy) > 1){
					toReturn = findVertical(s, target, middle(middle(jumpY+1)), addCord(coord, 0, -middle(middle(jumpY+1))));
				}
			} else if (addCord(coord, 0, -middle(midy)).row == 0) {
				if (upZero(s, target, addCord(toReturn, 0, -1))){
					toReturn = addCord(toReturn, 0, -1);
				}
			}
		} else {
			if (coord.row == size-1) {
				return coord;
			}
			if (addCord(coord, 0, midy).row < size){
				if (jumpY > 1){
					toReturn = findVertical(s, target, midy, addCord(coord, 0, midy));
				} else if (upZero(s, target, addCord(toReturn,  0, 1))){
					toReturn = addCord(toReturn,  0, 1);
				}
			} else if (addCord(coord, 0, middle(middle(jumpY+1))).row < size) {
				if (middle(middle(jumpY+1)) > 1){
					toReturn = findVertical(s, target, middle(middle(jumpY+1)), addCord(coord, 0, middle(middle(jumpY+1))));
				} else if (upZero(s, target, addCord(toReturn, 0, 1))){
					toReturn = addCord(toReturn, 0, 1);
				}
			}
		}
		return toReturn;
	}

	public boolean upZero(Slicer s, int target, Coordinate coord){
		try{
			return s.inTop(coord.row, target);
		} catch (IndexOutOfBoundsException e){
			return false;
		}
	}

	public static Coordinate addCord(Coordinate a, int c, int r){
		return new Coordinate((a.row + r), (a.column + c));
	}

	/**
	 * This method returns the middle number inbetween 1 and a, if a = 1, returns 0
	 * Examples:
	 * middle(0) -> 0
	 * middle(1) -> 0
	 * middle(2) -> 1
	 * middle(3) -> 2
	 * middle(4) -> 2
	 * middle(5) -> 3
	 * @param a the number to split
	 * @return the middle number
	 */
	public static int middle(int a){
		return (int) Math.round((double) a/2);
	}

	// You do not need to modify below this line.
	// ------------------------------------------
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
