package algs.days.day05;

/**
 * Working with memory is often opaque. Could there be surprising
 * timing results that can detect when memory is being accessed throughout
 * the whole
 */
public class TimingResults {
	public static void total(int[]A, int offset) {
		for (int i = 0; i < 10000000; i++) {
			int t      = A[0];
			A[0]       = A[offset];
			A[offset]  = t;
		}
	}
	
	public static void total(int[][][]A, int offset) {
		for (int i = 0; i < 10000000; i++) {
			int t                     = A[0][0][0];
			A[0][0][0]                = A[offset][offset][offset];
			A[offset][offset][offset] = t;
		}
	}

	public static void main(String[] args) {
		// allocate a[]
		int max = 1024*1024*1024;
		for (int offset = max-1; offset >= 1; offset = offset/2) {
			int[] A = new int[offset+1];
			long before = System.nanoTime();
			total(A, offset);
			double now = System.nanoTime();
			System.out.println(offset + "\t" + (now-before));
			A = null;
			System.gc();
		}
		for (int offset = 1; offset < max; offset = 2*offset + 1) {
			int[] A = new int[offset+1];
			long before = System.nanoTime();
			total(A, offset);
			double now = System.nanoTime();
			System.out.println(offset + "\t" + (now-before));
			A = null;
			System.gc();
		}
		System.gc();
		
		max = 1024;
		for (int offset = max-1; offset >= 1; offset = offset/2) {
			int[][][] B = new int[offset+1][offset+1][offset+1];
			long before = System.nanoTime();
			total(B, offset);
			double now = System.nanoTime();
			System.out.println(offset + "\t" + (now-before));
			B = null;
			System.gc();
		}
		for (int offset = 1; offset < max; offset = 2*offset + 1) {
			int[][][] B = new int[offset+1][offset+1][offset+1];
			long before = System.nanoTime();
			total(B, offset);
			double now = System.nanoTime();
			System.out.println(offset + "\t" + (now-before));
			System.gc();
		}
		System.gc();
	}
	
}
