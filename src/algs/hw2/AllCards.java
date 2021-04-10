package algs.hw2;

import java.util.Iterator;

import algs.iterator.ArrayIterator;

/** 
 * A Convenience class where you can retrieve all of the cards (in factory order) for the standard deck.
 */
public class AllCards implements Iterable<Card> {
	String[] all = { "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC",
				     "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD",
				     "AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH", 
				     "AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS" };

	class CardIterator implements Iterator<Card> {
		/** Use the array Iterator (presented in class) to iterate over these strings. */
		ArrayIterator<String> iterator;
		
		public CardIterator() {
			iterator = new ArrayIterator<String>(all);
		}
		
		/** Delegate to the string iterator. */
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		/** Construct a card for the next string representation. */
		@Override
		public Card next() {
			String s = iterator.next();
			return new Card(s);
		}
	}
	
	/** Returns a CardIterator that produces all cards in factory-sealed sequence. */
	@Override
	public Iterator<Card> iterator() {
		return new CardIterator();
	}
}
