package algs.days.day02;

import java.util.Arrays;
import java.util.Optional;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Three different implementations that find the largest and second-largest integer in 
 * an unsorted array.
 * 
 *  1. {@link findMaximumAndSecondLargestLoop#findMaximumAndSecondLargest(int[])}
 *  2. {@link findMaximumAndSecondLargest#findMaximumAndSecondLargest(int[])}
 *  3. {@link findRecursive#findMaximumAndSecondLargest(int[])}
 * 
 * All three of these implementations are less efficient than the optimal Tournament algorithm,
 * which requires additional space to store partial results.
 */
public class FindMaxSecondLargestRecursively {

	static int numComparisons = 0;

	/** Given an array of n>2 elements. */
	public static int[] findMaximumAndSecondLargestLoop(int[] values) {
		numComparisons = 0;

		int largest = values[0];
		int next = values[1];
		numComparisons++;
		if (next > largest) {
			int tmp = largest; largest = next; next = tmp;
		}

		for (int i = 2; i < values.length; i++) {
			if (values[i] > largest) {
				numComparisons++;
				next = largest;
				largest = values[i];
			} else if (values[i] > next) {
				next = values[i];
				numComparisons += 2;
			} else {
				numComparisons += 2;
			}
		}

		return new int[]{largest, next};
	}

	/** 
	 * Largest is in [0] and second largest is in [1].
	 * 
	 * Assuming values.length = n and n > 1, then the 
	 * number of comparisons required is 3n-5. Note that no extra storage
	 * is needed for this computation.
	 */
	public static int[] findMaximumAndSecondLargest(int[] values) {
		numComparisons = 0;
		if (values.length < 2) {
			throw new IllegalArgumentException ("Not enough values.");
		}

		Optional<Integer>[] result = findMaximum(values, 0, values.length-1);
		return new int[] { result[0].get(), result[1].get() };
	}

	/**
	 * Largest is in [0] and second largest is in [1].
	 * 
	 * Invokes recursion via tail-recursion:
	 * 
	 * T(n) = c + T(n-2)
	 */
	public static int[] findRecursive(int[] values) {
		numComparisons = 0;
		if (values.length < 2) {
			throw new IllegalArgumentException ("Not enough values.");
		}

		return recursive(values, 0, values.length-1);
	}	

	
	/** base case in recursion. */
	static final Optional<Integer>[] end = new Optional[] { Optional.empty(), Optional.empty() };

	/** Given two integers, choose {x, y} or {y, x} depending on ordering. */
	static Optional<Integer>[] choose(int x, int y) {
		numComparisons++;
		if (x < y) {
			return new Optional[] { Optional.of(y),  Optional.of(x) };
		} else {
			return new Optional[] {  Optional.of(x),  Optional.of(y) };
		}
	}

	/**
	 * Invoke with |a| > 1. 
	 * 
	 * Recursive invocation reduces problem size by 2 each time.
	 */
	static int[] recursive(int[] a, int lo, int hi) {

		int[] best = new int[] { a[lo], a[lo+1] };

		numComparisons++;
		if (a[lo] < a[lo+1]) {
			best = new int[] { a[lo+1], a[lo]};
		}

		// If exactly two, done
		if (hi - lo == 1) { return best; }

		// just three elements. Beats best one? move down
		if (hi - lo == 2) { 
			if (a[hi] > best[0]) {
				best[1] = best[0];
				best[0] = a[hi];
				numComparisons++;
				return best;
			} else if (a[hi] > best[1]) {
				numComparisons += 2;
				best[1] = a[hi];
				return best;
			} else {
				numComparisons += 2;
				return best;
			}
		}

		// at least four. Must merge best[] with rec[].
		int[] rec = recursive(a, lo+2, hi);

		int[] result = new int[2];
		numComparisons++;
		if (rec[0] > best[0]) {
			result[0] = rec[0];
			result[1] = best[0];
		} else {
			result[0] = best[0];
			result[1] = rec[0];
		}

		// compare second bests..
		if (rec[1] > result[0]) {
			numComparisons++;
			result[1] = result[0];
			result[0] = rec[1];
		} else if (rec[1] > result[1]) {
			result[1] = rec[1];
			numComparisons += 2;
		} else {
			numComparisons += 2;
		}

		if (best[1] > result[0]) {
			numComparisons++;
			result[1] = result[0];
			result[0] = best[1];
		} else if (best[1] > result[1]) {
			result[1] = best[1];
			numComparisons += 2;
		} else {
			numComparisons += 2;
		}

		return result;
	}

	/** 
	 * Recursive strategy that returns Optional[] values in case size of range is too small. 
	 * 
	 * Uses recursion to break list up in half, and recursively compute Max+secondMax on both
	 * sides, only to merge together at end; special handling necessary to deal with 
	 * cases where the sub-arrays have 1 element or none, hence the need for Optional<>[]
	 * return type.
	 * 
	 * T(n) = c + 2*T(n/2)
	 * 
	 * Each sub-problem is half the size of original.
	 */
	static Optional<Integer>[] findMaximum(int[] a, int lo, int hi) {
		/* Base Case: Zero or One elements requires no comparisons. */
		if (hi < lo) { return end; }
		if (hi == lo) { return new Optional[] { Optional.of(a[lo]), Optional.empty() }; }

		// Base Case: Two elements requires one comparison
		if (hi - lo == 1) {
			return choose(a[lo], a[hi]);
		}

		/* Recursive case: compute both halves. */
		int mid = (lo + hi) / 2;
		Optional<Integer>[] left = findMaximum(a, lo, mid-1);
		Optional<Integer>[] right = findMaximum(a, mid, hi);

		/* Merge these together. If either one is EMPTY, return other one. */
		if (!left[0].isPresent()) {
			return right;   // left is empty? Return right
		} else if (!right[0].isPresent()) {
			return left;    // right is empty? Return left
		} if (!left[1].isPresent() && !right[1].isPresent()) {
			// only one in each? Choose order of the two
			int leftv = left[0].get();
			int rightv = right[0].get();
			return choose(leftv, rightv);
		} else {
			// possible that one might be missing second
			int maxv = left[0].get();
			int secondv = right[0].get();
			numComparisons++;
			if (secondv > maxv) {
				int tmp = secondv; secondv = maxv; maxv = tmp;
			}

			if (left[1].isPresent()) {
				int leftv = left[1].get();
				if (leftv > maxv) {
					numComparisons++;
					secondv = maxv;
					maxv = leftv;
				} else if (leftv > secondv) {
					numComparisons += 2;
					secondv = leftv;
				} else {
					numComparisons += 2;
				}
			}

			if (right[1].isPresent()) {
				int rightv = right[1].get();
				if (rightv > maxv) {
					numComparisons++;
					secondv = maxv;
					maxv = rightv;
				} else if (rightv > secondv) {
					numComparisons += 2;
					secondv = rightv;
				} else {
					numComparisons += 2;
				}
			}

			return new Optional[] { Optional.of(maxv), Optional.of(secondv) };   // DONE!
		}
	}

	/** Helper function to return string "a,b". */
	static String out(int[] vals) {
		return vals[0] + "," + vals[1];
	}

	public static void main(String[] args) {
		int[] sample = { 3, 5, 9, 1, 2 };
		System.out.println("max of " + Arrays.toString(sample) + " is " + out(findMaximumAndSecondLargest(sample)) );

		sample = new int[] { 13, 5, 9, 1, 2 };
		System.out.println("max of " + Arrays.toString(sample) + " is " + out(findMaximumAndSecondLargest(sample)) );

		sample = new int[] { 13, 5, 9, 1, 22 };
		System.out.println("max of " + Arrays.toString(sample) + " is " + out(findMaximumAndSecondLargest(sample)) );

		sample = StdRandom.permutation(80);
		System.out.println("max of " + Arrays.toString(sample) + " is " + out(findMaximumAndSecondLargest(sample)) );

		// Because of the sheer number of permutations, this cannot possibly succeed in finding
		// all of the edge cases...
		System.out.println("RANDOM");
		System.out.println("n\tRec1\tRec2\tLoop\tTournament");
		for (int num = 2; num < 20; num++) {
			int max1, max2, max3;
			max1 = max2 = max3 = 0;
			for (int t = 0; t < 10000; t++) {
				sample = StdRandom.permutation(num);
				int check[] = findMaximumAndSecondLargest(sample);
				if (check[0] != num-1 && check[1] != num-2) {
					System.err.println("INVALID RESULTS!");
				}
				if (numComparisons > max1) {
					max1 = numComparisons;
				}

				check = findRecursive(sample);
				if (check[0] != num-1 && check[1] != num-2) {
					System.err.println("INVALID RESULTS!");
				}
				if (numComparisons > max2) {
					max2 = numComparisons;
				}

				check = findMaximumAndSecondLargestLoop(sample);
				if (check[0] != num-1 && check[1] != num-2) {
					System.err.println("INVALID RESULTS!");
				}
				if (numComparisons > max3) {
					max3 = numComparisons;
				}
			}
			int tourn = num + (int)Math.ceil(Math.log(num)/Math.log(2)) - 2;
			System.out.println(num + "\t" + max1 + "\t" + max2 + "\t" + max3 + "\t" + tourn);
		}

		System.out.println();
		System.out.println("ASCENDING");
		System.out.println("n\tRec1\tRec2\tLoop");
		for (int num = 2; num < 20; num++) {
			int max1, max2, max3;
			max1 = max2 = max3 = 0;
			sample = new int[num];
			for (int i = 0; i < num; i++) { sample[i] = i; }

			int check[] = findMaximumAndSecondLargest(sample);
			if (check[0] != num-1 && check[1] != num-2) {
				System.err.println("INVALID RESULTS!");
			}
			if (numComparisons > max1) {
				max1 = numComparisons;
			}

			check = findRecursive(sample);
			if (check[0] != num-1 && check[1] != num-2) {
				System.err.println("INVALID RESULTS!");
			}
			if (numComparisons > max2) {
				max2 = numComparisons;
			}

			check = findMaximumAndSecondLargestLoop(sample);
			if (check[0] != num-1 && check[1] != num-2) {
				System.err.println("INVALID RESULTS!");
			}
			if (numComparisons > max3) {
				max3 = numComparisons;
			}
			
			System.out.println(num + "\t" + max1 + "\t" + max2 + "\t" + max3);
		}

		System.out.println();
		System.out.println("DESCENDING");
		System.out.println("n\tRec1\tRec2\tLoop");
		for (int num = 2; num < 20; num++) {
			int max1, max2, max3;
			max1 = max2 = max3 = 0;
			sample = new int[num];
			for (int i = 0; i < num; i++) { sample[i] = num-i-1; }

			int check[] = findMaximumAndSecondLargest(sample);
			if (check[0] != num-1 && check[1] != num-2) {
				System.err.println("INVALID RESULTS!");
			}
			if (numComparisons > max1) {
				max1 = numComparisons;
			}

			check = findRecursive(sample);
			if (check[0] != num-1 && check[1] != num-2) {
				System.err.println("INVALID RESULTS!");
			}
			if (numComparisons > max2) {
				max2 = numComparisons;
			}

			check = findMaximumAndSecondLargestLoop(sample);
			if (check[0] != num-1 && check[1] != num-2) {
				System.err.println("INVALID RESULTS!");
			}
			if (numComparisons > max3) {
				max3 = numComparisons;
			}
		
			System.out.println(num + "\t" + max1 + "\t" + max2 + "\t" + max3);
		}
	}
}
