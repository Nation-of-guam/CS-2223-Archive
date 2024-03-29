package algs.hw4;

import algs.hw4.submission.Add;
import algs.hw4.submission.Expression;
import algs.hw4.submission.Value;

/** 
 * Shows a simple way of building up an expression tree.
 */
public class Example {
	
	public static void main(String[] args) {
		Value one = new Value(1);
		Value five = new Value(5);
		Value seven = new Value(7);
		
		Expression child = new Add(one, five);
		Expression tree = new Add(seven, child);
		
		// this will print out "(7.0+(1.0+5.0)) = 13.0"
		System.out.println(tree.format() + " = " + tree.eval());
	}
}
