package aiburns.hw1;

import algs.days.day04.FixedCapacityStack;
import edu.princeton.cs.algs4.*;
import javax.swing.JOptionPane;


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
	static double[] list;
	static int size;


	public static void main(String[] args) {
		list = new double[102];
		size = 0;

		FixedCapacityStack<String> exprs = new FixedCapacityStack<String>(100);
		FixedCapacityStack<Double> vals = new FixedCapacityStack<Double>(100);

		// COMPLETE IN HERE...

		while (!StdIn.isEmpty()) {
			// Read token. push if operator.
			String s = StdIn.readString();

			if (s.equals ("+") || s.equals ("-") || s.equals ("*") || s.equals ("/")) {

				// pop, evaluate, and push result if token is ")".


				double rightValue = vals.pop();
				double leftValue = vals.pop();
				double n = 0;
				if (exprs.isEmpty() ||
						(contains(leftValue) && rightValue != leftValue && contains(rightValue)) ){
					switch (s){
						case "+":
							vals.push(leftValue + rightValue);
							exprs.push("(" + leftValue + " + " + rightValue + ")");
							break;
						case "-":
							vals.push( leftValue - rightValue);
							exprs.push("(" + leftValue + " - " + rightValue + ")");
							break;
						case "*":
							vals.push( leftValue * rightValue);
							exprs.push("(" + leftValue + " * " + rightValue + ")");
							break;
						case "/":
							vals.push(leftValue / rightValue);
							exprs.push("(" + leftValue + " / " + rightValue + ")");
							break;
					}
					remove(leftValue);
					remove(rightValue);
				} else {
					String previousExpression = exprs.pop();


					String toPush = "";

					switch (s){
						case "+":
							vals.push(leftValue + rightValue);
							break;
						case "-":
							vals.push(leftValue - rightValue);
							break;
						case "*":
							vals.push(leftValue * rightValue);
							break;
						case "/":
							vals.push(leftValue / rightValue);
							break;
					}





					if (contains(leftValue)){
						toPush = "(" + leftValue + " " + s + " " + previousExpression + ")";
					} else if (contains(rightValue)){
						toPush = "(" + previousExpression + " " + s + " " + rightValue + ")";
					} else {
						String previousOfPreviousExpression = exprs.pop();
						toPush = "(" + previousOfPreviousExpression + " " + s + " " + previousExpression + ")";
					}

					remove(rightValue);
					remove(leftValue);
					exprs.push(toPush);
				}
			} else {
				// Token no operator or paren; must be double value to push
				vals.push(Double.parseDouble(s));
				add(Double.parseDouble(s));
			}
		}


		StdOut.print(exprs.pop() + " = " + vals.pop());
	}

	static void resizeList(){
		double[] temp = new double[2*list.length];
		for (int i = 0; i < list.length; i++) {
			temp[i] = list[i];
		}
		list = temp;
	}

	static int getIndex(double num){
		if (size == 0){
			return -1;
		}
		for (int i = 0; i < list.length; i++) {
			if (list[i] == num){
				return i;
			}
		}
		return -1;
	}

	static boolean contains(double num){
		return getIndex(num)!=-1;
	}

	static void remove(double num){
		if (!contains(num)){
			return;
		}
		int index = getIndex(num);
		for (int i = index; i < list.length-1; i++) {
			list[i] = list[i+1];
		}
		size--;

	}

	static void add(double num){
		list[size] = num;
		size++;
	}

}
