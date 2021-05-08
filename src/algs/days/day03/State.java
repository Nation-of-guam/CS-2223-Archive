package algs.days.day03;

import java.util.Optional;

import edu.princeton.cs.algs4.*;

/**
 * Represents the state of a railroad-track interchange with an incoming stack of
 * cars, a spur stack for cars to be stashed away, and a final outgoing ordering 
 * of the initial cars.
 */
public class State {
	final int N;
	final ResizingArrayStack<Integer> incoming;
	final ResizingArrayStack<Integer> spur;
	
	/** Build up record of outgoing cars just by prepending on the left. */
	String outgoing;
	
	// order is from left to right, so we have to push in reverse order
	public State (int [] order) {
		N = order.length;
		incoming = new ResizingArrayStack<Integer>();
		for (int i = order.length-1; i >= 0; i--) {
			incoming.push(order[i]);
		}
		
		outgoing = "";
		
		// empty spur
		spur = new ResizingArrayStack<Integer>();
	}
	
	/** Private constructor for copying. */
	private State (int n, ResizingArrayStack<Integer> in, ResizingArrayStack<Integer> sp, String out) {
		N = n;
		this.incoming = new ResizingArrayStack<Integer>();
		String stash = "";
		for (int iv : in) { stash = iv + stash; }
		for (int i = 0; i < stash.length(); i++) { incoming.push(stash.charAt(i) - '0'); }

		stash = "";
		this.spur = new ResizingArrayStack<Integer>();
		for (int iv : sp) { stash = iv + stash; }
		for (int i = 0; i < stash.length(); i++) { spur.push(stash.charAt(i) - '0'); }
		
		this.outgoing = out;
	}
	
	public Optional<State> canMove() {
		if (!incoming.isEmpty() || !spur.isEmpty()) {
			return Optional.of(new State(N, incoming, spur, outgoing));
		} else {
			return Optional.empty();
		}
		
	}
	
	/** Can move a car to the spur. */
	public boolean moveToSpur() {
		if (!incoming.isEmpty()) {
			int car = incoming.pop();
			spur.push(car);
			return true;
		} else {
			return false;
		}
	}
	
	/** Can move a car directly to the outgoing. */
	public boolean moveToOutgoing() {
		if (!incoming.isEmpty()) {
			int car = incoming.pop();
			outgoing = car + outgoing;
			return true;
		} else {
			return false;
		}
	}	
	
	/** Can move a car from the spur to outgoing. */
	public boolean moveSpurToOutgoing() {
		if (!spur.isEmpty()) {
			int car = spur.pop();
			outgoing = car + outgoing;
			return true;
		} else {
			return false;
		}
	}

}
