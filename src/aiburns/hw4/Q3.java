package aiburns.hw4;


import algs.days.day22.DirectedCycle;
import algs.days.day22.DirectedDFS;
import algs.days.day22.LectureDigraph;


/**
 * How many random directed graphs of V vertices have a cycle? and are connected?
 * 
 * Create a random graph by adding an edge between two vertices u and v with a probability
 * of 50%.
 * 
 * Run the same trial, this time using graphs whose edges each have a probability of 1/N chance
 * of being present.
 */
public class Q3 {
	public static void main(String[] args) {
		LectureDigraph[][] lowEdge = new LectureDigraph[14][10000];
		LectureDigraph[][] highEdge = new LectureDigraph[14][10000];


		for(int j = 0; j < lowEdge[0].length; j++){
			for (int i = 0; i < 14; i++){
				lowEdge[i][j] = new LectureDigraph(i+2);
				for (int h = 0; h < i+2; h++){
					for (int k = 0; k < h; k++){
						double randVal = Math.random();
						if (randVal < .5){
							lowEdge[i][j].addEdge(h,k);
						}
					}
					for (int k = h+1; k < i+2; k++){
						double randVal = Math.random();
						if (randVal < .5){
							lowEdge[i][j].addEdge(h, k);
						}
					}
				}
			}
		}

		for(int j = 0; j < highEdge[0].length; j++){
			for (int i = 0; i < 14; i++){
				double compareTo = 1.0/(double)(i+2);
				highEdge[i][j] = new LectureDigraph(i+2);
				for (int h = 0; h < i+2; h++){
					for (int k = 0; k < h; k++){
						double randVal = Math.random();
						if (randVal < compareTo){
							highEdge[i][j].addEdge(h,k);
						}
					}
					for (int k = h+1; k < i+2; k++){
						double randVal = Math.random();
						if (randVal < compareTo){
							highEdge[i][j].addEdge(h, k);
						}
					}
				}
			}
		}

		DirectedDFS connectedDFS;
		DirectedCycle cycleDFS;
		int[] lowCycleCount = new int[14];
		int[] lowConnCount = new int[14];
		int[] highCycleCount = new int[14];
		int[] highConnCount = new int[14];

		for (int d = 0; d < lowEdge[0].length; d++){
			for (int n = 0; n < 14; n++){
				boolean lowConnected = false;
				boolean highConnected = false;

				cycleDFS = new DirectedCycle(lowEdge[n][d]);
				boolean lowCycle = cycleDFS.hasCycle();


				cycleDFS = new DirectedCycle(highEdge[n][d]);
				boolean highCycle = cycleDFS.hasCycle();


				connectedDFS = new DirectedDFS(lowEdge[n][d], 0);
				lowConnected = (connectedDFS.getConnectedComponents() >= n+2);
				if (n == 0 & d < 5){
					System.out.println("\n"+lowEdge[n][d].toString());
				}
				if (n == 0 & d < 5){
					System.out.println("i= "+0+", "+connectedDFS.getConnectedComponents() + "");
					System.out.println(lowConnected);
				}

				connectedDFS = new DirectedDFS(highEdge[n][d], 0);
				highConnected = (connectedDFS.getConnectedComponents() >= n+2);


				/*for (int i = 0; i < n+2 && !highConnected; i++) {
					connectedDFS = new DirectedDFS(highEdge[n][d], i);
					highConnected = (connectedDFS.getConnectedComponents() == n+2);
				}*/

				if (lowConnected){
					lowConnCount[n]++;
				}
				if (lowCycle){
					lowCycleCount[n]++;
				}
				if (highConnected){
					highConnCount[n]++;
				}
				if (highCycle){
					highCycleCount[n]++;
				}
			}
		}


		System.out.println("Graphs with probability 0.5");
		System.out.println("N\t\t#Cycles\t\t#Connected");
		for (int i = 0; i < lowCycleCount.length; i++) {
			System.out.println(String.format("%2d\t\t%1d\t\t%1d", (i+2), lowCycleCount[i], lowConnCount[i]));
		}


		System.out.println("\n\nGraphs with probability 1/N");
		System.out.println("N\t\t#Cycles\t\t#Connected");
		for (int i = 0; i < highCycleCount.length; i++) {
			System.out.println(String.format("%2d\t\t%1d\t\t%1d", (i+2), highCycleCount[i], highConnCount[i]));
		}

	}
}
