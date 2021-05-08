package algs.days.day08;

import edu.princeton.cs.algs4.*;

public class PartitionDemonstration {
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		Quick.partition(a, 0, a.length-1);

		for (int i = 0; i < a.length; i++) {
			StdOut.print(a[i] + " ");
		}
	}
}
