package aiburns.hw1;

import algs.hw1.api.*;
import algs.hw1.Heisenberg;


/**
 * Copy this class into your USERID.hw1 package and improve this implementation.
 *
 * NOTE: The runtime results for this implementation appear in Q4 for
 * this homework assignment.
 */
public class HeisenbergFinder implements IHeisenbergFinder {
	int[] offset;
	int size;
	boolean isEven;
	boolean[] beenHere;


	/**
	 * Replace this inefficient function with something more efficient.
	 *
	 * You can inspect the contents of the array for h using inspect() method.
	 */
	public int find(Heisenberg h, int target) {
		int toReturn = -1;
		size = h.N;



		beenHere = new boolean[size];

		for (boolean i : beenHere) {
			i = false;
		}


		if (size % 2 == 0) {
			isEven = true;
			offset = new int[size];
			size--;
		} else {
			offset = new int[size];
		}

		if (size == 1) {
			toReturn = binarySearch(h, target, 0, 1, false);
		} else if (size == 3) {
			final int midPoint = middle(size);
			toReturn = binarySearch(h, target, 1, 1, true);
		} else {
			final int midPoint = middle(size);
			toReturn = binarySearch(h, target, midPoint, middle(midPoint), false);
		}


		if (isEven && toReturn == -1) {
			int probed = h.inspect(size) - offset[size];
			updateOffset(size, probed);
			if (target == probed) {
				toReturn = size;
			}
		}


		return toReturn;

	}

	public int binarySearch(Heisenberg h, int target, int index, int jump, boolean hasBeenOne){
		int toReturn = -1;
		if (!inBounds(index)){
			return toReturn;
		} else if (beenHere[index]){
			return toReturn;
		}


		int probed = h.inspect(index) - offset[index];
		updateOffset(index, probed);


		if (probed > target ) {
			beenHere[index] = true;
			if (!hasBeenOne){
				if (jump > 1){
					toReturn = binarySearch(h, target, index - jump, middle(jump), false);
				} else {
					toReturn = binarySearch(h, target, index - jump, middle(jump), true);
				}
			} else {
				toReturn = binarySearch(h, target, index - 1, 0, true);
			}
		} else if (probed < target ){
			beenHere[index] = true;
			if (!hasBeenOne){
				if (jump > 1){
					toReturn = binarySearch(h, target, index + jump, middle(jump), false);
				} else {
					toReturn = binarySearch(h, target, index + jump, middle(jump), true);
				}
			} else {
				toReturn = binarySearch(h, target, index + 1, 0, true);
			}

		} else if (probed == target ) {

			toReturn = index;

		}
		return toReturn;
	}

	/**
	 *
	 * @param index the index of the
	 */
	private void updateOffset(int index, int probed){
		for (int i = 0; i < index; i++) {
			offset[i]--;
		}

		for (int i = index+1; i < offset.length; i++) {
			offset[i]++;
		}
	}


	public boolean inBounds(int i){
		return (i < size && i > -1);
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
	public int middle(int a){
		return a/2;
	}

	// You do not need to modify below this line.
	// ------------------------------------------
	public static void main(String[] args) {
		for (int i = 1; i < 20; i++) {
			Heisenberg h = new Heisenberg(i, 99);
			int numProbes = h.solver(new HeisenbergFinder());
			System.out.println(i + "\t" + numProbes);
		}
		System.out.println();

		for (int i = 3; i < 257; i=2*i+1) {
			Heisenberg h = new Heisenberg(i, 99);
			int numProbes = h.solver(new HeisenbergFinder());
			System.out.println(i + "\t" + numProbes);
		}
	}
}
