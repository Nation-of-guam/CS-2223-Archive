package algs.days.day26;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Stack;

public class WaterBarrelProblem {
	
	
	Stack<State> doit(State goal) {
		State state = new State(new int[] { 12, 0, 0}, new int[] { 12, 7, 5});
		
		Queue<State> queue = new Queue<>();
		queue.enqueue(state);
		
		SeparateChainingHashST<String, Boolean> closed = new SeparateChainingHashST<>();
		Stack<State> solution = new Stack<>();
		while (!queue.isEmpty()) {
			State lastOne = queue.dequeue();
			closed.put(lastOne.key(), true);
			
			if (lastOne.equals(goal)) {
				while (lastOne != null) {
					solution.push(lastOne);
					lastOne = lastOne.prior;
				}
				return solution;
			}
			
			// generate new states from possible.
			Queue<State> moves = lastOne.moves();
			for (State s : moves) {
				if (!closed.contains(s.key())) {
					queue.enqueue(s);
				}
			}
		}
		
		System.out.println("Failed after " + closed.size() + " states explored.");
		return solution;
		
	}
	
	public static void main(String[] args) {
		State goal = new State(new int[] { 6, 6, 0}, new int[] { 12, 7, 5});
		Stack<State> stack = new WaterBarrelProblem().doit(goal);
		while (!stack.isEmpty()) {
			System.out.println(stack.pop().key());
		}
		
		// now explore to find all possible states. Make a goal that cannot be reached.
		// Set breakpoint when returning solution. You can see a total of 24 states were
		// searched. Last state dequeued is [6,1,5] but perhaps last interesting state
		// is [11,1,0] or [11,0,1], both of which can be stated vaguely "you want to 
		// measure out a single gallon. How do you do it?"
		goal = new State(new int[] { -1, -1, -1}, new int[] { 12, 7, 5});
		stack = new WaterBarrelProblem().doit(goal);
	}
	
}
