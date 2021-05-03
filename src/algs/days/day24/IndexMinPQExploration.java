package algs.days.day24;

import edu.princeton.cs.algs4.IndexMinPQ;

public class IndexMinPQExploration {
	public static void main(String[] args) {
		IndexMinPQ<Double> minpq = new IndexMinPQ<>(15);
		
		for (int i = 0; i < 15; i++) {
			minpq.insert(i, 20.0 - i);    // insert in decreasing values to force heap adjustment
		}
		
		// vertices 0 to 14
		//    I associate a "distance" that is positive (but not infinite)
		//    v0 has distance of 20
		//    v1 has a distance of 19
		//    v2 has a distance of 18
		//    v14 has a distance of 6
		
		System.out.println("Inspect structures in debugger");
		
		// observe:
		// Keys: [20.0, 19.0, 18.0, 17.0, 16.0, 15.0, 14.0, 13.0, 12.0, 11.0, 10.0, 9.0, 8.0, 7.0, 6.0, null]
		// Keys is 0-based index since its index is a vertex ID from 0 to 14 because 15 vertices are in the MinPQ
		// This means that Keys[i] is the best distance for vertex i
		// as you can see the Key with shortest distance is to vertex 14. These values do not form a heap!
		//
		// pq: these values are 0-based and form a heap of 15 elements. The contents of the array are vertex 
		//     identifiers, but note that pq[0] is not used.   The value 14 is in pq[1] because that is the
		//     vertex with the lowest distance (i.e., priority). These represent the HEAP
		// [0, 14, 9, 13, 6, 8, 10, 12, 0, 3, 2, 7, 1, 5, 4, 11]
		//
		// qp: these invert the relationship so we can immediately know where vertex v is in the pq
		//     This matrix is 0-based. Note that vertex 0 can be found at the index position 8 in the pq heap
		//     or in otherwords, qp[0] is 8, and you can see that pq[8] = 0
		// [8, 12, 10, 9, 14, 13, 4, 11, 5, 2, 6, 15, 7, 3, 1, -1]
		//
		// Lots of information! but all of it necessary to be able to decrease Key
		
	}
}
