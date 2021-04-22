package algs.days.day13;

public class Sample {
	static int M = 17;              // number of buckets
	static int a[] = new int[M];
	
	/** Convert hashCode() into index 0 and M-1 */
	static int hash(Object o) {
		return (o.hashCode() & 0x7fffffff) % M;
	}

	static String strings[] = new String[] { "it", "was", "the", "best", "of", "times", "worst" }; 
	
	public static void main(String[] args) {
		String s1 = "this";
		String s2 = "that";
		a[hash(s1)] = 17;
		a[hash(s2)] = 19;
		
		System.out.println("Value with " + s2 + " is " + a[hash(s2)]);
		
		for (String s : strings) {
			System.out.println(s + " maps to " + hash(s));
		}
	}
}