package algs.hw2;

/** Utility class to remember specific in/out shuffles that yielded given deck. */
public class State {
	public final Deck deck;
	public final String shuffle;
	
	public State(Deck d, String s) {
		this.deck = d;
		this.shuffle = s;
	}
}