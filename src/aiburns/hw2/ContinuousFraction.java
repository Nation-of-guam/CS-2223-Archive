package aiburns.hw2;

import java.util.Iterator;

public class ContinuousFraction {

	class ContinuousNode {
		int             value;
		ContinuousNode  next;
		
		ContinuousNode(int v) {
			this.value = v;
		}
	}
	
	/** Use linked list. */
	ContinuousNode first;
	
	public ContinuousFraction(Iterator<Integer> it) {
		throw new RuntimeException("To be completed by student");
	}
	
	public ContinuousFraction(double d) {
		throw new RuntimeException("To be completed by student.");
	}
	
	public double value() {
		throw new RuntimeException("To be completed by student.");
	}
	
	public String toString() {
		return "TO BE COMPLETED BY STUDENT";
	}
	
	public static void main(String[] args) {
		// Print out the ever popular PI!
		ContinuousFraction frac = new ContinuousFraction(Math.PI);
		System.out.println(frac);
		
		// look at tables of square roots for patterns.
		for (int i = 2; i < 20; i++) {
			frac = new ContinuousFraction(Math.sqrt(i));
			System.out.println(i + "\t" + frac);
		}
	}
}
