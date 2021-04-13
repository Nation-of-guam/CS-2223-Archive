package algs.days.day12;

public class Exercise {
	public static void main(String[] args) {
		MaxPQ<Integer> mpq = new MaxPQ<Integer>(7);
		
		for (int v : new int[] { 2, 7, 4, 9, 8, 6}) {
			mpq.insert(v);
			System.out.println(mpq);
		}
		
		System.out.println("Del Max Once");
		int res = mpq.delMax();
		System.out.println(res + " returned.");
		System.out.println(mpq);

		System.out.println("Del Max Twice");
		res = mpq.delMax();
		System.out.println(res + " returned.");
		System.out.println(mpq);
	}
	
}
