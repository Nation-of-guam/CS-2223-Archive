package algs.days.day23;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.StopwatchCPU;

public class RandomSearch {
	public static void main(String[] args) {

		for (int N = 4; N <= 128; N *= 2) {
			int numFound = 0;
			StopwatchCPU cpu = new StopwatchCPU();
			for (int t = 0; t < 1000; t++) {
				Graph graph = new Graph(N);
				for (int i = 0; i < N; i++) {
					for (int j = i+1; j < N; j++) {
						if (i == j) { continue; }

						if (Math.random() < 0.35) { 
							graph.addEdge(i, j); 
						}
					}
				}

				HamiltonPath hp = new HamiltonPath(graph);
				hp.visited.push(0);
				if (hp.search(0, 1)) { numFound++; }
			}
			
			System.out.println(N + "\t" + numFound + "\t" + cpu.elapsedTime());
		}
	}
}
