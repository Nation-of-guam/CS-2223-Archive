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
				"TimSort Optimized: \t"+isSortedArrayStable(TimOpt) + "\n");
	}


	private static String trialnotOne(int divisor, boolean backwards){
		String toReturn = "";
		Integer[][] vals = new Integer[5][];
		int[] lengths = new int[]{(1048576*1), (1048576*2), (1048576*4), (1048576*8), (1048576*16)};
		for (int i = 0; i < vals.length; i++) {
			vals[i] = new Integer[lengths[i]];
			if (backwards){
				for (int j = 0; j < lengths[i]; j++){
					vals[i][j] = lengths[i]-j;
				}
			} else {
				for (int j = 0; j < lengths[i]; j++){
					vals[i][j] = StdRandom.uniform(lengths[i]/divisor);
				}
			}
		}
		try {
			Method[] algorithms = new Method[]{
					Q1.class.getMethod("heapSort", Comparable[].class),
					Q1.class.getMethod("mergeSort", Comparable[].class),
					Q1.class.getMethod("quickSort", Comparable[].class),
					Q1.class.getMethod("primitiveTimSort", Comparable[].class),
					Q1.class.getMethod("builtinSort", Comparable[].class)
			};

			SortMethod[] methods = new SortMethod[5];
			for (int i = 0; i < algorithms.length; i++){
				methods[i] = new SortMethod(algorithms[i], vals.clone());
			}
			algorithms = null;
			vals = null;


			builtinSort(methods);

			toReturn = toReturn + ("N\t\t\t");
			for(int h = 0; h < methods.length; h++){
				toReturn = toReturn + methods[h].name+"\t";
			}
			toReturn = toReturn + "\n";

			for (int j = 0; j < lengths.length-1; j++){
				toReturn = toReturn + String.format("%2d\t\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f", lengths[j],
						methods[0].time[j],
						methods[1].time[j],
						methods[2].time[j],
						methods[3].time[j],
						methods[4].time[j]) + "\n";
			}
			int j = 4;
			toReturn = toReturn + String.format("%2d\t%.3f\t%.3f\t%.3f\t%.3f\t%.3f\n", lengths[j],
					methods[0].time[j],
					methods[1].time[j],
					methods[2].time[j],
					methods[3].time[j],
					methods[4].time[j])+ "\n";
			return toReturn;
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
			System.out.println(e + " : Idk what went wrong" );
			throw new IllegalArgumentException("idk cheif");
		}
	}

	public static String trial1_2() throws NoSuchFieldException {
		return "Q1.2 \n"+ trialnotOne(1, false);
	}

	public static String trial1_3() {
		return  "Q1.3 \n"+ trialnotOne(512, false);
	}
	
	public static String trial1_4() {
		String toReturn = "Q1.4 \n"+ trialnotOne(1, true);
		 toReturn += "\n\tMy statement: This is showing how timsort is the best in all situations, and somehow quicksort is actually trash, \n" +
				 "and optimized timsort is for the enlightened individuals among us.  I am honnestly suprised that mergesort absolutlely \n" +
				 "destroyed quicksort in every measuralbe way.  Heap sort sadly is almost the slowest.  At the time of writing this, I\n" +
				 "think there may be something up with the Timsort Optimized, as it is somehow ~44x faster at sorting for Q1.2, and 1.3 \n" +
				 "which is confusing, as with the other people I checked with only had ~1.5x-2x faster performance.  This may just be the \n" +
				 "fact that I have 10gb of memory for the JVM.";
		return toReturn;
	}
	
	public static void main(String[] args) throws IllegalArgumentException, NoSuchFieldException {
		trial1_1();

		try{
			/*
			 *This implementation of multithreads was adapted from this stackOverflow article I found from @user aioobe
			 * https://stackoverflow.com/questions/9664036/how-to-run-two-methods-simultaneously
			 *
			 * I did this because if it broke, then it would work
			 */

			System.out.println("Get ready, this takes like 2 minutes, and then just prints them all at once \n");

			Thread thread1 = new Thread() {
				String q2;
				public void run() {
					try {
						q2 = trial1_2();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					}
				}

				public String toString(){
					return q2;
				}
			};

			Thread thread2 = new Thread() {
				String q3;
				public void run() {
					q3 = trial1_3();
				}

				public String toString(){
					return q3;
				}
			};

			Thread thread3 = new Thread() {
				String q4;
				public void run() {
					q4 = trial1_4();
				}

				public String toString(){
					return q4;
				}
			};

			thread1.start();
			thread2.start();
			thread3.start();

			thread1.join();
			thread2.join();
			thread3.join();

			System.out.println(thread1.toString());
			System.out.println(thread2.toString());
			System.out.println(thread3.toString());

		} catch (Exception e){
			trial1_2();
			trial1_3();
			trial1_4();
		}

	}

	public static class SortMethod implements Comparable{
		String name;
		Double[] time;
		protected SortMethod(Method sortType, Integer[] ... vals) throws InvocationTargetException, IllegalAccessException {
			time = new Double[] {
					(double) sortType.invoke(null, new Object[] {vals[0]}),
					(double) sortType.invoke(null, new Object[] {vals[1]}),
					(double) sortType.invoke(null, new Object[] {vals[2]}),
					(double) sortType.invoke(null, new Object[] {vals[3]}),
					(double) sortType.invoke(null, new Object[] {vals[4]})};
			fixName(sortType);

		}

		private void fixName(Method sortType){
			switch (sortType.getName()){
				case "builtinSort":
					name = "TimSort";
					return;
				case "mergeSort":
					name = "Merge";
					return;
				case "primitiveTimSort":
					name = "PrimTS";
					return;
				case "heapSort":
					name = "Heap";
					return;
				case "quickSort":
					name = "Quick";
					return;
			}
		}

		@Override
		public int compareTo(Object o) {
			SortMethod other = (SortMethod) o;
			return this.time[4].compareTo(other.time[4]);
		}
	}

}
