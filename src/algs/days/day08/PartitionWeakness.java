package algs.days.day08;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

// size of next problem is just one smaller on SORTED list...
public class PartitionWeakness {
	public static void main(String[] args) {
		String[] a = new String [] { "ant", "bat", "cat", "dog", "egg", "fly", "get" };
		StdOut.println(Arrays.toString(a) + "\n");

		int part = Quick.partition(a, 0, a.length-1);
        StdOut.println(Arrays.toString(a));
        System.out.println("Partition set to:" + part);
	}
}
