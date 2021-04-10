package algs.hw2;

/**
 * Four possible suits. Note that for convenience in Card, each suit has an integer equivalent.
 */
public enum Suit {
	CLUBS(0)    { public String abbreviation() { return "C"; } }, 
	DIAMONDS(1) { public String abbreviation() { return "D"; } }, 
	HEARTS(2)   { public String abbreviation() { return "H"; } }, 
	SPADES(3)   { public String abbreviation() { return "S"; } };
	
	int value;
	
	Suit(int v) {
        this.value = v;
    }
	
	/** Each suit has a value. */
	public int getValue() {
        return value;
    }
	
	/** Return single-character code for each suit. */
	public abstract String abbreviation();
}
