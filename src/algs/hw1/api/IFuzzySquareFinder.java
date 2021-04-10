package algs.hw1.api; // DO NOT COPY THIS CLASS INTO YOUR PROJECT AREA. USE AS IS

import algs.hw1.Coordinate;
import algs.hw1.FuzzySquare;

/**
 * Part of your homework assignment is to provide a class that implements this interface
 */
public interface IFuzzySquareFinder {

	/** 
	 * Returns the Coordinate in which target is found, or null if it doesn't exist.
	 * 
	 * Note that the number of probes will be recorded by fs.
	 */
	Coordinate find(FuzzySquare fs, int target);
}
