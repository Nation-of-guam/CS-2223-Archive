package algs.hw1.api; // DO NOT COPY THIS CLASS INTO YOUR PROJECT AREA. USE AS IS

import algs.hw1.Coordinate;
import algs.hw1.ManhattanSquare;

/**
 * Part of your homework assignment is to provide a class that implements this interface
 */
public interface IManhattanSquareFinder {
	
	/**
	 * Locate the target within ManhattanSquare and return its Coordinate
	 * or null if it doesn't exist.
	 * 
	 * Note that the number of probes will be recorded by ms.
	 */
	Coordinate find(ManhattanSquare ms, int target);
}
