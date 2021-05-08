package algs.days.day08;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

// set breakpoint in partition to see this work.
public class PartitionExample {
	public static void main(String[] args) {
		String[] a = new String [] { "egg", "fly", "ant", "cat", "dog", "get", "bat" };
		StdOut.println(Arrays.toString(a) + "\n");

        int part = Quick.partition(a, 0, a.length-1);
        StdOut.println(Arrays.toString(a));
        System.out.println("Partition set to:" + part);
	}
}
