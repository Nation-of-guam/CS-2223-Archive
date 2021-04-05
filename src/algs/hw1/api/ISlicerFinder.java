package algs.hw1.api; // DO NOT COPY THIS CLASS INTO YOUR PROJECT AREA. USE AS IS

import algs.hw1.Coordinate;
import algs.hw1.Slicer;

/**
 * Part of your homework assignment is to provide a class that implements this interface
 */
public interface ISlicerFinder {
	
	/**
	 * Given a Slicer object, return Coordinate location of target inside Slicer, 
	 * or null if that target value does not exist.
	 */
	Coordinate find(Slicer s, int target);
	
}
