package algs.days.day08;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class PartitionUnnecessaryExchanges {
	public static void main(String[] args) {
		String[] a = new String [] { "the", "the", "the", "the", "the", "the", "the" };
		StdOut.println(Arrays.toString(a) + "\n");
		
        int part = Quick.partition(a, 0, a.length-1);
        StdOut.println(Arrays.toString(a));
        System.out.println("Partition set to:" + part);
	}
}
