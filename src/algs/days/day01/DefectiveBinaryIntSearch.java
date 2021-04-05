package algs.days.day01;

import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class DefectiveBinaryIntSearch {
	boolean defectiveContains(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low < high) {
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
		DefectiveBinaryIntSearch bis = new DefectiveBinaryIntSearch();
		
		int[] values = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		StdOut.println ("Here are your values: " + Arrays.toString(values));
		for (int val : values) {
			if (!bis.defectiveContains(values, val)) {
				System.out.println("Couldn't find " + val);
			}
		}
	}
}
