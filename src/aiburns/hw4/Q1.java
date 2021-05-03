package aiburns.hw4;

import algs.days.day04.FixedCapacityStack;
import edu.princeton.cs.algs4.StdIn;

public class Q1 {
	/**
	 * Complete this implementation that takes a postfix expression string and converts it into
	 * an Expression node using a fixed Capacity stack. When done, an Expression node will
	 * be returned.
	 * 
	 * Using the postfix expression as input
	 * 
	 *     3 1 + 4 / 1 5 + 9 * 2 6 * - *
	 *  
	 * should produce the expression from the homework, namely
	 * 
	 *     (((3+1)/4 * (((1+5)*9)-(2*6)))
	 *
	 * Note that postfix expressions do not need parentheses, which is one of their
	 * major selling points.
	 */
	public static void main(String[] args) {

		// since everything IS an expression (even Values) you only need a single stack.
		FixedCapacityStack<Expression> exprs = new FixedCapacityStack<Expression>(100);

		while (!StdIn.isEmpty()) {
			String s = StdIn.readString();
			if (s.equals ("+")) {
				Expression right = exprs.pop();
				Expression left = exprs.pop();
				exprs.push(new Add(left, right));
			} else if (s.equals ("-")) {
				Expression right = exprs.pop();
				Expression left = exprs.pop();
				exprs.push(new Subtract(left, right));
			} else if (s.equals ("*")) {
				Expression right = exprs.pop();
				Expression left = exprs.pop();
				exprs.push(new Multiply(left, right));
			}
			else if (s.equals ("/")) {
				Expression right = exprs.pop();
				Expression left = exprs.pop();
				exprs.push(new Divide(left, right));
			} else {
				exprs.push(new Value(Double.parseDouble(s)));
			}

		}


		// fill in here...
		Expression exp = exprs.pop();
		System.out.println(exp.format() + " = " + exp.eval());		
	}
}
