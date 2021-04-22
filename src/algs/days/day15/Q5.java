package algs.days.day15;

public class Q5 {
	/** Takes advantage of fact that A[0] and A[N-1] are already known. */
	public static int optimizedNumSmaller(int[]A) {
		int lo = 1;
		int hi = A.length - 2;
		int smaller = A[0];
		while (lo <= hi) {
			int mid = (lo + hi)/2;
			if (A[mid] == smaller) {
				lo = mid+1;
			} else {
				hi = mid-1;
			}
		}
		
		// # compares is floor(log (N-2)) + 2
		return lo;
	}
	
	/** Return number of copies of smaller value in A. */
	public static int numSmallerRight(int[]A) {
		int lo = 0;
		int hi = A.length - 1;
		int small = A[0];  // do once, so don't have to do repeated # of times.
		while (lo <= hi) {
			int mid = (lo + hi)/2;
			if (A[mid] == small) {
				lo = mid+1;
			} else {
				hi = mid-1;
			}
		}

		// count is actually lo, since hi is on the last one.
		// # compares is floor(log N) + 2
		return lo;
	}
	
	/** Only works first time. */
	public static int numSmaller(int[]A) {
		int numSmaller = 1;
		int N = A.length;
		boolean found = false;
		numSmaller = N/2;
		
		// only works first time....
		while (!found) {
			if (A[N/2] > A[0] && A[N/2-1] > A[0]) {
				N = N/4; numSmaller -= N/4;
			}
			if (A[N/2] > A[0] && A[N/2-1] == A[0]) {
				found = true;
			}
			if (A[N/2] ==A[0] && A[N/2-1] == A[0]) {
				N = 3*N/4; numSmaller += N/4;
			}
		}
		
		return numSmaller;		
	}

	public static void main(String[] args) {
		int[] A = { 3, 5, 5, 5, 5, 5, 5};
		System.out.println(optimizedNumSmaller(A));
	}
}