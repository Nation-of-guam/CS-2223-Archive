package algs.days.day13;

public class Clashes {
	static int M = 17;              // number of buckets
	static String keys[] = new String[M];
	static int values[] = new int[M];
	
	/** Convert hashCode() into index 0 and M-1 */
	static int hash(Object o) {
		return (o.hashCode() & 0x7fffffff) % M;
	}

	static String strings[] = new String[] { "it", "was", "the", "best", "of", "times", "worst" }; 
	
	public static void main(String[] args) {
		for (String s : strings) {
			int bucket = hash(s);
			if (keys[bucket] == null) {
				keys[bucket] = s;
				values[bucket] = 10;     // I don't care what the values are 
			} else {
				if (keys[bucket].equals(s)) {
					System.out.println("Overwrite value associated " + s);
					values[bucket]++;
				} else {
					System.out.println("CLASH!");
				}
			}
		}
		
		System.out.println(java.util.Arrays.toString(keys));
		System.out.println(java.util.Arrays.toString(values));
	}
}