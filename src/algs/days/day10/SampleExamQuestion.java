package algs.days.day10;

public class SampleExamQuestion {

	static int numSqrt = 0;
	
	static int process(int[] a, int lo, int hi) {
		if (lo== hi) { 
			numSqrt++;
			return(int) Math.sqrt(a[lo]); 
		}

		int mid= lo+ (hi-lo)/2;
		int x= process(a, lo, mid) + process(a, mid+1, hi);

		for (int i= lo; i <= hi; i += 2) {
			numSqrt++;
			if (Math.sqrt(a[i]) == x) { x++; }
		}
		return x;
	}

	public static void main(String[] args) {
		int[] a = new int[64];
		int N = a.length;
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		
		System.out.println(process(a, 0, a.length-1));
		System.out.println("numSqrt = " + numSqrt);
		
		// N + log_2(N)*(N/2)
		double prediction = N + N*(Math.log(N)/Math.log(2))/2;
		System.out.println(prediction);
	}

}
