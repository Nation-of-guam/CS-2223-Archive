package algs.hw1.solution;

import algs.days.day04.FixedCapacityStack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Complete this implementation that takes a postfix expression and converts it into
 * an Infix Expression using a fixed Capacity stack. Also perform the necessary 
 * computation to produce its value
 * 
 * Using the postfix expression as input
 * 
 *     3 6 + 5 * 8 2 - /
 *  
 * should produce the following as output:
 * 
 *     (((3 + 6) * 5) / (8 - 2)) = 7.5
 *
 * Note that postfix expressions do not need parentheses, which is one of their
 * major selling points.
 */
public class Q3_PostFixToInfix {
	
	public static void main(String[] args) {
		
		FixedCapacityStack<String> exprs = new FixedCapacityStack<String>(100);
		FixedCapacityStack<Double> vals = new FixedCapacityStack<Double>(100);

		while (!StdIn.isEmpty()) {
			// Read token. push if operator
			String s = StdIn.readString();
			
			if ("*/+-".contains(s)) {
				String right = exprs.pop();
				String left = exprs.pop();
				exprs.push("(" + left + " " + s + " " + right + ")");
				
				if (s.equals ("+")) {vals.push(vals.pop() + vals.pop()) ;}
				else if (s.equals ("-")) { Double rt = vals.pop(); vals.push(vals.pop() - rt);}
				else if (s.equals ("*")) { vals.push(vals.pop() * vals.pop()) ;}
				else if (s.equals ("/")) { Double rt = vals.pop(); vals.push(vals.pop() / rt);}

				
			} else {
				// If not an operator, then must be value
				exprs.push(s);
				vals.push(Double.valueOf(s));
			}
		}
		
		StdOut.print(exprs.pop() + " = " + vals.pop());
	}
}
