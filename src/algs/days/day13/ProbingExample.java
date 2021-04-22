package algs.days.day13;

import edu.princeton.cs.algs4.LinearProbingHashST;

public class ProbingExample {
	
	static String[] initials = new String[] { "it", "the", "best", "that", "i", "could", 
											  "do", "but", "if", "is", "badly", "done"};
	
	// to see how it performs on regular strings, make the hashtable larger (like 50 or 100) and 
	// then you can see how poorly the BadHashString performs.
	public static void main(String[] args) {
		LinearProbingHashST<BadHashString,Integer> st = new LinearProbingHashST<>(10);
		
		// count the distribution of random #s
		for (String s : initials) {
			BadHashString bs = new BadHashString(s);
			
			st.put(bs, 1);
			System.out.println("");  // breakpoint here
		}
		
		System.out.println("Breakpoint here");
		
	}
}
