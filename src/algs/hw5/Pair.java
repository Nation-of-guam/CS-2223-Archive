package algs.hw5;

import java.util.Objects;

/**
 * This class will be useful for the PopularSymbolTable. USE AS IS WITHOUT CHANGES. DO NOT COPY INTO YOUR AREA.
 * 
 * This Pair stores two integer values. For example Pair(4, 7)
 * 
 * Two Pair objects are equal if and only if both integer values are the same.
 * 
 * Compare two Pair objects as follows:
 *  1. Pair(4, 7) < Pair(5, 10) because the '4' is smaller than the '5'.
 *  2. Pair(6, 8) > Pair(6, 3) because the first integer ('6') is the same but '8' > '3'.
 *  3. Pair(7, 14) == Pair(7, 14) obviously.
 * 
 * So when comparing two Pair objects, check the first value, and only if these first
 * values are the same do you need to check the second value.
 */
public class Pair implements Comparable<Pair> {
	public final Integer value1;
	public final Integer value2;
	
	public Pair (Integer value1, Integer value2) {
		this.value1 = value1;
		this.value2 = value2;
	}

	public int hashCode() {
		return Objects.hash(value1, value2);
	}
	
	@Override
	public int compareTo(Pair other) {
		if (value1 < other.value1) { return -1; }
		if (value1 > other.value1) { return +1; }
		
		if (value2 < other.value2) { return -1; }
		if (value2 > other.value2) { return +1; }
		
		return 0;
	}
	
	public boolean equals(Object o) {
		if (o == null) { return false; }
		if (o instanceof Pair) {
			Pair other = (Pair) o;
			return value1.equals(other.value1) && value2.equals(other.value2);
		}
		
		return false;
	}
	
	public String toString() { return value1 + ", " + value2; }
}
