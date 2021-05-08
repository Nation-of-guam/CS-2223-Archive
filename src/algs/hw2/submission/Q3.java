package algs.hw2.submission;

/**
 * This is a more complicated version of a question you might see on the midterm.
 * 
 * Currently this code produces the following output:
  
    N            Val     Actual      Predicted
     2	           0
     4	           1
     8	          11
    16	          70
    32	         354
    64	        1599
   128	        6813
   256	       28156
   512	      114524
  1024	      462013
  2048	     1856031
  4096	     7440258

Your task is to modify the program so it produces the following

    N          Val      Actual   Predicted
     2	           0      c2      S(2)
     4	           1      c4      S(4)
     8	          11      c8      S(8)
    16	          70      ...     ...
    32	         354      ...     ...
    64	        1599      ...     ...
   128	        6813      ...     ...
   256	       28156      ...     ...
   512	      114524      ...     ...
  1024	      462013      ...     ...
  2048	     1856031      ...     ...
  4096	     7440258      ...     ...
  
 Where each of the c2, c4, c8, ... is replaced by an integer that reflects the actual number of times 
 that power() was invoked during proc(a, 0, a.length-1) for arrays, a, of size 2, 4, 8, 16, ...
 The final Predicted column reflects the prediction of S(N), which you should implement below
 in the Q3.model(n) method.

 Note for the bonus question: add a fourth-column that shows your prediction of Val. Implement your 
 formula in the bonusModel(int n) method.

 */
public class Q3 {

	/** 
	 * Helper method to compute base^exp as a long.
	 */
	static long power(int base, int exp) {
		return (long) Math.pow(base, exp);
	}
	
	/**
	 * This is the method under review. Do not change this method.
	 */
	public static long proc(int[] a, int lo, int hi) {
		if (lo == hi) { 
			return power(a[lo], 2) + power(a[hi],2);
		}

		int m = (lo + hi) / 2;
		long total = proc(a, lo, m);
		while (hi > lo) {
			m = (lo + hi) / 2;
			total += power(a[m], 2);
			hi = m;
		}
		
		return total;
	}
	
	/**
	 * Complete your analysis of the code and modify this function to return the prediction
	 * of how many times Math.power() is called for an initial problem of size n.
	 */
	static long model(int n) {
		// FIX ME
		return 0;
	}
	
	/**
	 * Bonus question. Can you come up with a (more complicated) formula that predicts
	 * the value, Value, or proc(a,0,n-1) when a is composed of the integers from 0 to n-1. 
	 */
	static long bonusModel(int n) {
		// FIX ME
		return 0;
	}
	
	/** Launch the experiment. */
	public static void main(String[] args) {
		System.out.println("    N          Value            Actual     Predicted");
		for (int n = 2; n <= 4096; n *= 2) {
			
			int[] a = new int[n];
			for (int i = 0; i < n; i++) { a[i] = i; }
			
			// initiate the request on an array of size n, containing values from 0 to n-1
			// using indices of lo=0 and hi=n-1
			long val = proc(a, 0, n-1);
			
			// WILL NEED TO FIX BELOW
			System.out.println(String.format("%6d\t%12d", n, val, 0, model(n)));
		}
	}
}
