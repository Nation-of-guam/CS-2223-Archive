package algs.hw3;

/**
 * When an item is created, it is assigned a unique counter. After
 * each object is created, the global counter advances for the next
 * one.
 * 
 * This counter is not thread-safe, but don't worry about that for this 
 * assignment.
 * 
 * Use this class AS IS without any changes.
 * 
 * If you construct two CountedItem<String> objects, then the one that is 
 * created first has a lower counter value than the second one, which 
 * means the following will be true:
 * 
 *   CountedItem<String> one = new CountedItem("the");
 *   CountedItem<String> two = new CountedItem("the");
 *   System.out.println(one.equals(two));            <-- will be TRUE
 *   System.out.println(one.earlier(two));           <-- will be TRUE
 *   System.out.println(two.earlier(one));           <-- will be FALSE
 *   
 */
public class CountedItem<E extends Comparable<E>> implements Comparable<CountedItem<E>> {
	/** Ever increasing counter. */
	static long globalCounter;
	
	/** Value to store. */
	E value;
	
	/** Counter when object was constructed. */
	public final long counter;
	
	/** Store the given integer value and record counter (via global counter) when it was created. */
	public CountedItem(E value) {
		this.value = value;
		counter = globalCounter++;
	}
	
	/** Standard compareTo. */
	@Override
	public int compareTo(CountedItem<E> o) {
		return this.value.compareTo(o.value);
	}
	
	/** Delegate equality to the inner object. */
	@Override
	public boolean equals(Object o) {
		if (o == null) { return false; }
		if (o instanceof CountedItem) {
			CountedItem<E> other = (CountedItem<E>) o;
			return this.value.equals(other.value);
		}
		
		return false;
	}
	
	/** When printing toString(), include counter. */
	public String toString() {
		return value + "_" + counter;
	}
	
	/** Determines whether the current object was created earlier than other. */
	public boolean earlier (CountedItem<E> other) {
		return this.counter < other.counter;
	}
}
