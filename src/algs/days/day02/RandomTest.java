package algs.days.day02;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Generate random arrays of size 15, containing the values from 
 * 0 to 14, and see how many are found with proper binary array 
 * search, when applied to an unordered array.
 */
public class RandomTest {

	/** This is given a random arrangement of values. Does it still find target? */
	static boolean contains(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc < 0) {
				low = mid+1;
			} else if (rc > 0) {
				high = mid-1;
			} else {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		int[] vals = new int[15];
		for (int i = 0; i < vals.length; i++) {
			vals[i] = i;
		}
		
		int minFound = vals.length;
		int maxFound = -1;
		for (int t = 0; t < 1000; t++) {
			StdRandom.shuffle(vals);
			
			int numFound = 0;
			for (int i = 0; i < vals.length; i++) {
				if (contains(vals, i)) {
					numFound++;
				}
			}
			
			if (numFound < minFound) {
				minFound = numFound;
			}
			if (numFound > maxFound) {
				maxFound = numFound;
			}
		}
		
		
		System.out.println("min:" + minFound + ", max:" + maxFound);
	}
}
