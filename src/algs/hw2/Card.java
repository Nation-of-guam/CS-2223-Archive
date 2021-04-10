package algs.hw2;

import java.util.Objects;

/**
 * Represents a Card in a four-suited deck of cards, whose ranks range from 1 (ACE) to 13 (KING).
 * 
 * Note that a Card can be a key in a hashtable because it implements {@link #hashCode()} and
 * {@link #equals(Object)}. 
 */
public class Card implements Comparable<Card> {
	public final int rank;
	public final Suit suit;
	
	public static int ACE = 1;
	public static int JACK = 11;
	public static int QUEEN = 12;
	public static int KING = 13;

	/** Validate rank on constructor. */
	public Card(Suit s, int r) {
		if (r < 1) { throw new IllegalArgumentException("Rank must be at least 1"); }
		suit = s;
		rank = r;
	}
	
	/** Given a card like "7D" this creates the Seven of Diamonds. */
	public Card (String representation) {
		char suit = representation.charAt(representation.length()-1);
				
		switch (suit) {
		case 'C': this.suit = Suit.CLUBS; break;
		case 'D': this.suit = Suit.DIAMONDS; break;
		case 'H': this.suit = Suit.HEARTS; break;
		case 'S': this.suit = Suit.SPADES; break;
		default: throw new IllegalArgumentException(suit + " is an illegal suit designation.");
		}

		String rank = representation.substring(0, representation.length()-1);
		if (rank.equals("J")) { this.rank = Card.JACK; }
		else if (rank.equals("Q")) { this.rank = Card.QUEEN; }
		else if (rank.equals("K")) { this.rank = Card.KING; }
		else if (rank.equals("A")) { this.rank = Card.ACE; }
		else {
			try {
				int r = Integer.valueOf(rank);
				this.rank = r;
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException(rank + " is an illegal card rank.");
			}
		}
	}
	
	/** Create meaningul hashcode. */
	@Override
	public int hashCode() {
		return Objects.hash(rank, suit);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) { return false; }
		if (o == this) { return true; }
		if (o instanceof Card) {
			Card other = (Card) o;
			return other.rank == rank && other.suit == suit;
		}
		return false;
	}
	
	@Override
	public int compareTo(Card other) {
		// first compare by suit
		int rc = suit.compareTo(other.suit);
		if (rc != 0) { return rc; }
		
		// so we are same suit, so now compare by rank
		return rank - other.rank;
	}
	
	/** Reasonable toString method. */
	public String toString() {
		String left;
		if (rank == ACE) { left = "A"; }
		else if (rank == JACK) { left = "J"; }
		else if (rank == QUEEN) { left = "Q"; }
		else if (rank == KING) { left = "K"; } 
		else { left = "" + rank; }
		
		return left + suit.abbreviation();
	}
}
