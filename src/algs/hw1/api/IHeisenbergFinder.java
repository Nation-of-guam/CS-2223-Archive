package algs.hw1.api; // DO NOT COPY THIS CLASS INTO YOUR PROJECT AREA. USE AS IS

import algs.hw1.Heisenberg;

/**
 * Part of your homework assignment is to provide a class that implements this interface
 */
public interface IHeisenbergFinder {
	
	/**
	 * Given a Heisenberg object, determine whether target value is contained within; if 
	 * it does, then a non-negative integer is returned to specify its index, otherwise 
	 * -1 is returned.
	 * 
	 * Whenever you call {@link Heisenberg#inspect(int)}, it perturbs the data. In addition
	 * the object records the number of attempted probes.
	 * 
	 * Note that the number of probes will be recorded by h.
	 */
	int find(Heisenberg h, int target);
}
