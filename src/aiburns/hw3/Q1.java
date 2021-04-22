package aiburns.hw3;

import algs.days.day16.ComparableTimSort;
import algs.hw3.CountedItem;
import algs.hw3.PrimitiveTimSort;
import edu.princeton.cs.algs4.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
		Merge.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using quick sort. */
	public static double quickSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		Quick.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using Insertion Sort. */
	public static double insertionSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		Insertion.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using Selection Sort. */
	public static double selectionSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		Selection.sort(A);
		return start.elapsedTime();
	}
	
	/** Return time to sort array using Heap Sort. */
	public static double heapSort(Comparable<?>[] A) {
		StopwatchCPU start = new StopwatchCPU();
		Heap.sort(A);
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
		int i = 0;
		while (i < A.length-1){
			if (A[i].compareTo(A[++i]) > 0){
				return false;
			}
		}
		return true;
	}

	/** 
	 * Given a sorted array of CountedItem<String> objects, ensure that for 
	 * any two index positions, i and j, if A[i] is equal to A[j] and i < j, 
	 * then A[i].earlier(A[j]) is true.
	 * 
	 * Performance must be O(N).
	 */
	public static boolean isSortedArrayStable(CountedItem[] A) {
		for (int i = 0; i < A.length-1; i++) {
			if(A[i].equals(A[i+1]) && !A[i].earlier(A[i+1])){
				return false;
			}
		}
		return true;
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
		System.out.println("Algorithm\t\t\tStable Sort");
		// create array of integers with opportunities for duplicates
		Integer vals[] = new Integer[4096];
		for (int i = 0; i < vals.length; i++) { vals[i] = StdRandom.uniform(128); }
		CountedItem<Integer>[] HeapVals = toCountedArray(vals.clone());
		CountedItem<Integer>[] InsertionVals = toCountedArray(vals.clone());
		CountedItem<Integer>[] MergeVals = toCountedArray(vals.clone());
		CountedItem<Integer>[] QuickVals = toCountedArray(vals.clone());
		CountedItem<Integer>[] SelectionVals = toCountedArray(vals.clone());
		CountedItem<Integer>[] TimPrim = toCountedArray(vals.clone());
		CountedItem<Integer>[] TimOpt = toCountedArray(vals.clone());
		heapSort(HeapVals);
		insertionSort(InsertionVals);
		mergeSort(MergeVals);
		quickSort(QuickVals);
		selectionSort(SelectionVals);
		primitiveTimSort(TimPrim);
		builtinSort(TimOpt);

		// using this SAME ARRAY, create different CountedItem<> arrays and
		// determine which of the sorting algorithms are stable, and which ones are not.
		System.out.println(
				"HeapSort: \t\t\t"+isSortedArrayStable(HeapVals)+"\n" +
				"InsertionSort:\t\t"	+isSortedArrayStable(InsertionVals)+"\n" +
				"MergeSort: \t\t\t"+isSortedArrayStable(MergeVals)+"\n" +
				"QuickSort: \t\t\t"+isSortedArrayStable(QuickVals)+"\n" +
				"SelectionSort: \t\t"+isSortedArrayStable(SelectionVals)+"\n" +
				"TimSort Primitive: \t"+isSortedArrayStable(TimPrim)+"\n" +
				"TimSort Optimized: \t"+isSortedArrayStable(TimOpt));
	}



	public static void trial1_2() throws NoSuchFieldException {
		System.out.println("Q1.2");



		Integer[][] vals = new Integer[4][];
		int[] lengths = new int[]{(1048576*1), (1048576*2), (1048576*4), (1048576*8), (1048576*16)};
		for (int i = 0; i < vals.length; i++) {
			vals[i] = new Integer[lengths[i]];
			for (int j = 0; j < vals[i].length; j++){
				vals[i][j] = StdRandom.uniform(lengths[i]);
			}
		}

		try {
			SortMethod[] methods = new SortMethod[5];
			methods[0] = new SortMethod(class.getMethod("heapSort", Q1),  vals);
			System.out.println(methods[0].name+" done");
			methods[1] = new SortMethod(Q1.class.getMethod("mergeSort", Comparable[].class), vals);
			System.out.println(methods[1].name+" done");
			methods[2] = new SortMethod(Q1.class.getMethod("quickSort", Comparable[].class), vals);
			System.out.println(methods[2].name+" done");
			methods[3] = new SortMethod(Q1.class.getMethod("primitiveTimSort", Comparable[].class), vals);
			System.out.println(methods[3].name+" done");
			methods[4] = new SortMethod(Q1.class.getMethod("builtinSort", Comparable[].class), vals);
			System.out.println(methods[4].name+" done");


			builtinSort(methods);

			for (int i = 0; i < methods.length; i++) {
				System.out.print("N\t\t");
				for(int h = 0; h < methods.length; h++){
					System.out.print(methods[h].name+"\t\t");
				}

				for (int j = 0; j < lengths.length; j++){
					System.out.print(lengths[j] + "\t\t");
					for(int h = 0; h < methods.length; h++){
						System.out.print(methods[h].time[j]+"\t\t");
					}
					System.out.println();
				}
			}
			

		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
			System.out.println(e + ": Idk what went wrong" );
			throw new IllegalArgumentException("idk cheif");
		}
	}




	public static void trial1_3() {
		System.out.println("Q1.3");
		
		// completed by student
	}
	
	public static void trial1_4() {
		System.out.println("Q1.4");
		
		// completed by student
	}
	
	public static void main(String[] args) throws IllegalArgumentException, NoSuchFieldException {
		trial1_1();
		trial1_2();
		trial1_3();
		trial1_4();
	}

	public static class SortMethod implements Comparable{
		String name;
		Double[] time;
		protected SortMethod(Method sortType, Integer[] ... vals) throws InvocationTargetException, IllegalAccessException {
			System.out.println("test");
			time = new Double[]{
					(double) sortType.invoke(vals[0].clone()),
					(double) sortType.invoke(vals[1].clone()),
					(double) sortType.invoke(vals[2].clone()),
					(double) sortType.invoke(vals[3].clone())};
			name = sortType.getName();
		}

		@Override
		public int compareTo(Object o) {
			SortMethod other = (SortMethod) o;
			return this.time[3].compareTo(other.time[3]);
		}
	}

}
