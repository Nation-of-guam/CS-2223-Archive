package algs.days.day15;
/**
 *
 */
public class Q2 {
	
	static int numSqrt = 0;
	
	/**
	 * Helper method to compute sqrt as a long.
	 */
	static long sqrt(int val) {
		numSqrt += 1;
		return (long) Math.sqrt(val);
	}
	
	public static double proc(int[] a, int lo, int hi) {
		double total = sqrt(a[lo]);
		if (lo == hi) {
			return total + sqrt(hi);
		}
		int m = (lo + hi) / 2;
		total += proc(a, lo, m);
		for (int i = lo; i <= m; i++) {
			total += sqrt(a[i]);
			hi = m;
		}
		return total;
	}
	/**
	 * Complete your analysis of the code and modify this function to return the prediction
	 * of how many times Math.power() is called for an initial problem of size n.
	 */
	static long model(int n) {
		double log2 = Math.log(n)/Math.log(2);
		return (long)(2 + n-1 + log2);              // can simplify as n + 1 + log2(N)
	}
	/**
	 * Bonus question. Can you come up with a (more complicated) formula that predicts
	 * the value, Value, or proc(a,0,n-1) when a is composed of the integers from 0 to n-1.
	 */
	static long bonusModel(int n) {
		return 0;
	}
	/** Launch the experiment. */
	public static void main(String[] args) {
		System.out.println(" N Value Actual Predicted Bonus");
		for (int n = 2; n <= 4096; n *= 2) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++) { a[i] = i; }
			// initiate the request on an array of size n, containing values from 0 to n-1
			// using indices of lo=0 and hi=n-1
			numSqrt = 0;
			long val = (long) proc(a, 0, n-1);
			System.out.println(String.format("%6d\t%12d\t%12d\t%12d\t%12d", n, val, numSqrt, model(n), bonusModel(n)));
		}
	}
}