package algs.days.day02;

import java.util.Arrays;

public class FindMaxRecursively {
	
	public static int findMaximum(int[] values) {
		if (values.length == 0) {
			throw new IllegalArgumentException("Cannot find maximum of an empty array.");
		}
		
		if (values.length == 1) {
			return values[0];
		}
		
		return findMaximum(values, 0, values.length-1);
	}
	
	static int findMaximum(int[] a, int lo, int hi) {
		/** Base Case: Two elements just requires one comparison. */
		if (hi - lo == 1) {
			if (a[lo] < a[hi]) {
				return a[hi];
			} else {
				return a[lo];
			}
		}
		
		/** Recursive case: compute largest of remaining objects. One additional comparison. */
		int max = findMaximum (a, lo+1, hi);
		if (max < a[lo]) {
			return a[lo];
		} else {
			return max;
		}
	}
	
	public static void main(String[] args) {
		int[] sample = { 3, 5, 9, 1, 2 };
		System.out.println("max of " + Arrays.toString(sample) + " is " + findMaximum(sample) );
		
		sample = new int[] { 13, 5, 9, 1, 2 };
		System.out.println("max of " + Arrays.toString(sample) + " is " + findMaximum(sample) );
		
		sample = new int[] { 13, 5, 9, 1, 22 };
		System.out.println("max of " + Arrays.toString(sample) + " is " + findMaximum(sample) );
		
	}
}
