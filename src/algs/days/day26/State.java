package algs.days.day26;

import edu.princeton.cs.algs4.Queue;

public class State {
	final int barrels[]; // contents
	State prior = null;
	
	final int sizes[];    // capacity of each
	
	public State(int[] contents, int[] sizes) {
		barrels = new int[contents.length];
		for (int i = 0; i < barrels.length; i++) {
			barrels[i] = contents[i];
		}
		this.sizes = new int[sizes.length];
		for (int i = 0; i < sizes.length; i++) {
			this.sizes[i] = sizes[i];
		}
	}
	
	/** Return all possible new states. */
	public Queue<State> moves() { 
		Queue<State> newOnes = new Queue<>();
		for (int i = 0; i < barrels.length; i++ ) {
			if (barrels[i] == 0) { continue; }
			
			// something to do!
			for (int o = 0; o < barrels.length; o++) {
				if (i == o) { continue; }
				
				// destination can accept how much BUT ONLY if > 0
				int totalToAccept = sizes[o] - barrels[o];
				if (totalToAccept == 0) { continue; }
				
				
				State newState = new State(barrels, sizes);
				newState.prior = this;
				
				// if I have less than what can be accepted, move ALL.
				// Otherwise, only move what can be supported.
				if (barrels[i] <= totalToAccept) {
					// empty out barrels[i]
					newState.barrels[o] += newState.barrels[i];
					newState.barrels[i] = 0;
				} else {
					newState.barrels[o] = newState.sizes[o];
					newState.barrels[i] -= totalToAccept;
				}
				
				newOnes.enqueue(newState);
			}
		}
		
		return newOnes;
	}
	
	
	public String key() {
		return "[" + barrels[0] + "," + barrels[1] + "," + barrels[2] + "]";
	}
	
	public String toString() {
		return key();
	}
	
	public boolean equals(Object o) {
		if (o == null) { return false; }
		
		if (o instanceof State) {
			State other = (State) o;
			for (int i = 0; i < barrels.length; i++) {
				if (other.barrels[i] != barrels[i]) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
}