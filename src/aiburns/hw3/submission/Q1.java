package aiburns.hw3.submission;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StopwatchCPU;
import algs.days.day16.ComparableTimSort;
import algs.hw3.CountedItem;
import algs.hw3.PrimitiveTimSort;

/**
 * 
 * Use the existing SortTrial class, and write your own for your implementation
 * of TimSort and also the HeapSort 
 * 
 * https://shakespeare.folger.edu/shakespeares-works/hamlet/download/
 * 
 * What is the longest word which is not a modern English word, according to
 * our dictionary?
 */
public class Q1 {
	
	/** Return time to sort array using merge sort. */
	public static double mergeSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU(); 
		edu.princeton.cs.algs4.Merge.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using quick sort. */
	public static double quickSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		edu.princeton.cs.algs4.Quick.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using Insertion Sort. */
	public static double insertionSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		edu.princeton.cs.algs4.Insertion.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using Selection Sort. */
	public static double selectionSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		edu.princeton.cs.algs4.Selection.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using Heap Sort. */
	public static double heapSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		edu.princeton.cs.algs4.Heap.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using Primitive Tim Sort. */
	public static double primitiveTimSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		PrimitiveTimSort.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using Optimized Tim Sort. */
	public static double builtinSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		ComparableTimSort.sort(A);
		return start.elapsedTime();
	}
	
	/** Determine if the array is sorted. */
	public static boolean isSorted(Comparable[] A) {
		throw new RuntimeException("Completed by Student");
	}

	/** 
	 * Given a sorted array of CountedItem<String> objects, ensure that for 
	 * any two index positions, i and j, if A[i] is equal to A[j] and i < j, 
	 * then A[i].earlier(A[j]) is true.
	 * 
	 * Performance must be O(N).
	 */
	public static boolean isSortedArrayStable(CountedItem[] A) {
		throw new RuntimeException("Completed by Student");
	}
	
	/** 
	 * Given an array of integers, return a CountedItem<Integer> array. If you construct
	 * and add the objects from left to right, then for two duplicate values A[i] and A[j],
	 * you know that the counter for A[i] is smaller than the counter for A[j] if i < j. 
	 */
	static CountedItem<Integer>[] toCountedArray(Integer vals[]) {
		CountedItem<Integer>[] copy = new CountedItem[vals.length];
		for (int i  = 0; i < copy.length; i++) {
			copy[i] = new CountedItem<>(vals[i]);
		}
		
		return copy;
	}
	
	public static void trial1_1() {
		System.out.println("Q1.1");
		
		// create array of integers with opportunities for duplicates
		Integer vals[] = new Integer[4096];
		for (int i = 0; i < vals.length; i++) { vals[i] = StdRandom.uniform(128); }
		
		// using this SAME ARRAY, create different CountedItem<> arrays and 
		// determine which of the sorting algorithms are stable, and which ones are not.
	}
	
	public static void trial1_2() {
		System.out.println("Q1.2");
		
		// completed by student
	}
	
	
	public static void trial1_3() {
		System.out.println("Q1.3");
		
		// completed by student
	}
	
	public static void trial1_4() {
		System.out.println("Q1.4");
		
		// completed by student
	}
	
	public static void main(String[] args) {
		trial1_1();
		trial1_2();
		trial1_3();
		trial1_4();
	}
}
