package algs.days.day08;

import algs.days.day08.instrumented.*;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Example sorting of different algorithms when compared to exch and less.
 */
public class SortComparison {

	/** Generate N=2^k size data. */
	interface Generator {
		int[] generate(int k);
	}

	static final int NumTrials = 3;

	static final int lowSize = 12;  //  2^12 = 4096
	static final int highSize = 21; //  2^22 = 2097152

	/**
	 * Each AlgorithmEvaluation records best performance, whether fewest number of exchanges,
	 * or fewest number of less() invocations for a specific algorithm on a specific data set.
	 *
	 * As improved results are recorded, these numbers only decrease. DO NOT MODIFY THIS CLASS.
	 */
	class AlgorithmEvaluation {
		long   fewestArrayUpdate = -1;    // fewest number of array update operations
		long   fewestLess = -1;           // fewest number of less operations
		double fewestAverageMoved = -1;   // average number of times an individual value is moved
		double fewestMaxMoved = -1;       // average number of times an individual value is moved

		/** Initialize to worst possible. */
		AlgorithmEvaluation () {
		}

		/**
		 * Updates the best results so far for #array updates, #less executions.
		 */
		void update(long exch, long less, double averageMoved, int maxMoved) {
			if (fewestLess == -1) {
				fewestArrayUpdate = exch; 
				fewestLess = less; 
				fewestAverageMoved = averageMoved;
				fewestMaxMoved = maxMoved;
				return;
			}
			
			if (exch < fewestArrayUpdate) { fewestArrayUpdate = exch; }
			if (less < fewestLess) { fewestLess = less; }
			if (averageMoved < fewestAverageMoved) { fewestAverageMoved = averageMoved; }
			if (maxMoved < fewestMaxMoved) { fewestMaxMoved = maxMoved; }
		}
	}

	/**
	 * Represents a number of AlgorithmEvaluation trials, each on a different-sized data set.
	 */
	abstract class Benchmark {
		/** Information on each of the runs. */
		AlgorithmEvaluation[] trials;

		/**
		 * Sets up a benchmark for a given number of trials on known Sort algorithms.
		 * 
		 * @param numTrials
		 */
		Benchmark (int numTrials) {
			trials = new AlgorithmEvaluation[numTrials];

			for (int i = 0; i < numTrials; i++) {
				trials[i] = new AlgorithmEvaluation();
			}
		}

		/** Return the specialized sort algorithm on the given data. */
		abstract SortAlgorithm sortInstance(int[] vals);

		/** Return information about the algorithm. */
		AlgorithmEvaluation evaluation(int idx) { return trials[idx]; }

		/**
		 * Conduct a run of all algorithms for given trial number (idx) and given values.
		 * 
		 * Ensures that values is not mutated by this method (it makes a copy).
		 * 
		 * Enforce execution to complete within maxSeconds
		 * 
		 * @param idx           which trial is being executed.
		 * @param values        data to be sorted.
		 */
		void conductTrial (int idx, int[] values) {
			SortAlgorithm alg;
			
			try {
				alg = sortInstance(copy(values));
			} catch (Exception e) {
				// silently stop further work...
				return;
			}

			try {
				alg.sort();
				trials[idx].update(alg.getArrayUpdateCount(), alg.getLessCount(),alg.getMoveAverage(),alg.getMoveMax());

				if (!alg.isSorted()) {
					System.out.printf("ERROR with %s\n", alg.getClass().getName());
				}
			} catch (StackOverflowError e) {
				System.out.printf("Stack Overflow on %s!\n", alg.getClass().getName());
			}
		}

		/** 
		 * Return a copy of the incoming array. DO NOT MODIFY THIS METHOD.
		 */
		int[] copy(int[] a) {
			int[] c = new int[a.length];
			for (int i = 0; i < a.length; i++) { c[i] = a[i]; }
			return c;
		}
	}

	class InsertionSortBenchmark extends Benchmark {
		InsertionSortBenchmark(int numTrials) { super(numTrials); }
		SortAlgorithm sortInstance(int[] vals) { return new Insertion(vals);	}
	}

	class MergeSortBenchmark extends Benchmark {
		MergeSortBenchmark(int numTrials) { super(numTrials); }
		SortAlgorithm sortInstance(int[] vals) { return new Merge(vals);	}
	}

	class QuickSortBenchmark extends Benchmark {
		QuickSortBenchmark(int numTrials) { super(numTrials); }
		SortAlgorithm sortInstance(int[] vals) { return new QuickOriginal(vals);	}
	}

	class QuickSortAlternateBenchmark extends Benchmark {
		QuickSortAlternateBenchmark(int numTrials) { super(numTrials); }
		SortAlgorithm sortInstance(int[] vals) { return new QuickAlternate(vals);	}
	}


	/** Adapted from StdRandom.shuffle(). DO NOT MODIFY THIS METHOD. */
	public static void shuffle(int[] a, int lo, int hi) {
		if (a == null) throw new NullPointerException("argument array is null");
		int N = hi - lo + 1;
		for (int i = 0; i < N; i++) {
			int r = lo + StdRandom.uniform(N-i);     // between i and N-1
			int swap = a[i];
			a[i] = a[r];
			a[r] = swap;
		}
	}

	/**
	 * Generates an array of N=2^k unique integers in reverse order. 
	 * 
	 * @param k
	 * @return
	 */
	class ReverseUniqueData implements Generator {
		public int[] generate(int k) {
			int N = (int) Math.pow(2, k);
			int[] ar = new int[N];
			for (int i = 0; i < N; i++) {
				ar[i] = N-i-1;
			}

			return (ar);
		}
	}
	
	/**
	 * Generates an array of N=2^k unique integers in ascending order. 
	 * 
	 * @param k
	 * @return
	 */
	class AscendingUniqueData implements Generator {
		public int[] generate(int k) {
			int N = (int) Math.pow(2, k);
			int[] ar = new int[N];
			for (int i = 0; i < N; i++) {
				ar[i] = i;
			}

			return (ar);
		}
	}
	
	/**
	 * Generates an array of N=2^k unique integers in random arrangement. 
	 * 
	 * @param k
	 * @return
	 */
	class UniqueRandomData implements Generator {
		public int[] generate(int k) {
			int N = (int) Math.pow(2, k);
			int[] ar = new int[N];
			for (int i = 0; i < N; i++) {
				ar[i] = i;
			}

			shuffle(ar, 0, N-1);
			return (ar);
		}
	}

	public void report() {
		System.out.println("Baseline: Unique Data In Random Order");
		generateBaseReport(new UniqueRandomData());
		System.out.println();
		
		System.out.println("Testing: Data in Reverse Order");
		generateBaseReport(new ReverseUniqueData());
		System.out.println();
		
		System.out.println("Testing: Data in Ascending Order");
		generateBaseReport(new AscendingUniqueData());
	}

	public void generateBaseReport(Generator generator) {

		int numTrials = highSize - lowSize + 1;
		StdOut.println("                   ArrayUpdate                                            Average Moved                               Average Less");
		StdOut.println("   N\t   Quick\t QuickAlt\t   Merge\t Insertion\tQuick\tQuickA\tMerge\tInsert.\t  Quick\t         QuickA\t           Merge\tInsertion");
		for (int k = lowSize; k <= highSize; k++) {
			int N = (int) Math.pow(2, k);

			// run 10 independent trials on uniqueData
			Benchmark quickSortBenchmark = new QuickSortBenchmark(numTrials);
			Benchmark quickSortAlternateBenchmark = new QuickSortAlternateBenchmark(numTrials);
			Benchmark mergeBenchmark = new MergeSortBenchmark(numTrials);
			Benchmark insertionSortBenchmark = new InsertionSortBenchmark(numTrials);

			int[] data = generator.generate(k);
			quickSortBenchmark.conductTrial(k - lowSize, data);
			quickSortAlternateBenchmark.conductTrial(k - lowSize, data);
			mergeBenchmark.conductTrial(k - lowSize, data);
			insertionSortBenchmark.conductTrial(k - lowSize, data);
		
			AlgorithmEvaluation quick = quickSortBenchmark.evaluation(k-lowSize);
			AlgorithmEvaluation quickAlt = quickSortAlternateBenchmark.evaluation(k-lowSize);
			AlgorithmEvaluation merge = mergeBenchmark.evaluation(k-lowSize);
			AlgorithmEvaluation insertion = insertionSortBenchmark.evaluation(k-lowSize);
			StdOut.printf("%6d\t%8d\t%8d\t%8d\t%8d\t%.0f\t%.0f\t%.0f\t%.0f\t%8d\t%8d\t%8d\t%8d%n", 
					N, 
					quick.fewestArrayUpdate, quickAlt.fewestArrayUpdate, merge.fewestArrayUpdate, insertion.fewestArrayUpdate,
					quick.fewestAverageMoved, quickAlt.fewestAverageMoved, merge.fewestAverageMoved, insertion.fewestAverageMoved,
					quick.fewestLess, quickAlt.fewestLess, merge.fewestLess, insertion.fewestLess);
		}
	}
	// use as is...
	public static void main(String[] args) {
		new SortComparison().report();
	}
}
