package algs.days.day02;

import edu.princeton.cs.algs4.*;

public class CompareOperation {
	public static void main(String[] args) {
		// timing comparison of addition vs. multiplication vs. square root
		if (args.length == 0) { args = new String[] { "32768" }; }
		int N = Integer.parseInt(args[0]);
		
		// only here so the code won't be detected as having no external effect.
		long sum;
		int[] numbers = new int[N];
		for (int i = 0; i < N; i++) {
			numbers[i] = StdRandom.uniform(-1000000, 1000000);
		}
		
		// SWITCH THESE AROUND AND SEE WHAT HAPPENS!
		
		Stopwatch addsw = new Stopwatch();
		sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += (numbers[i]+numbers[j]);
			}
		}
		System.out.println("Add:" + addsw.elapsedTime() + " seconds");
		System.out.println("For the record, sum was " + sum);
		
		Stopwatch multsw = new Stopwatch();
		sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += (numbers[i]*numbers[j]);
			}
		}
		System.out.println("Mult:" + multsw.elapsedTime() + " seconds");
		System.out.println("For the record, sum was " + sum);
		
		Stopwatch sqrtsw = new Stopwatch();
		sum = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += (Math.sqrt(numbers[i])+Math.sqrt(numbers[j]));
			}
		}
		System.out.println("Sqrt:" + sqrtsw.elapsedTime() + " seconds");
		System.out.println("For the record, sum was " + sum);
		
	}
}
