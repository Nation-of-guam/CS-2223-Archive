package algs.days.day13;

import edu.princeton.cs.algs4.StdOut;

public class Arrays {
	public static void main(String[] args) {

		int[][] a = new int[20][5];

		int n = a.length;
		int m = a[0].length;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				a[r][c] = r*c;
			}
		}

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				StdOut.print(a[r][c] + " ");
			}
			StdOut.println();
		}
		
	}
}
