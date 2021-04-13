package algs.days.day12;

import edu.princeton.cs.algs4.StdOut;

public class SampleTilde {
	public static void main(String[] args) {
		for (int N = 2; N <= 40; N++) {
			
			int sum = 0;                        // Block A
			for (int n = N; n > 0; n /= 2) {    // Block B
				for (int i = 0; i < n; i++) {   // Block C
					sum++;
				}
			}
	
			StdOut.println (N + "\t" + sum + "\t" + 2*N + "\t" + (2*N - sum));
		}
	}
}
