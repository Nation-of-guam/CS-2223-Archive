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
		int numVertex = 14; //V = numVertex+1

		LectureDigraph[][] lowEdge = new LectureDigraph[numVertex][10000];
		LectureDigraph[][] highEdge = new LectureDigraph[numVertex][10000];

		/*
		* Creating the different graphs with different probability
		* I go j i, as I heard that is faster than IJ
		 */
		for(int j = 0; j < lowEdge[0].length; j++){
			for (int i = 0; i < numVertex; i++){
				double compareTo = 1.0/(double)(i+2);
				highEdge[i][j] = new LectureDigraph(i+2);
				lowEdge[i][j] = new LectureDigraph(i+2);

				for (int h = 0; h < i+2; h++){
					for (int k = 0; k < h; k++){
						double randVal = Math.random();
						if (randVal < .5){
							if (randVal < compareTo){
								highEdge[i][j].addEdge(h,k);
							}
							lowEdge[i][j].addEdge(h,k);
						}
					}
					for (int k = h+1; k < i+2; k++){
						double randVal = Math.random();
						if (randVal < .5){
							if (randVal < compareTo){
								highEdge[i][j].addEdge(h,k);
							}
							lowEdge[i][j].addEdge(h, k);
						}
					}
				}
			}
		}

		DirectedDFS connectedDFS;
		DirectedCycle cycleDFS;
		int[] lowCycleCount = new int[numVertex];
		int[] lowConnCount = new int[numVertex];
		int[] highCycleCount = new int[numVertex];
		int[] highConnCount = new int[numVertex];

		for (int j = 0; j < lowEdge[0].length; j++){
			for (int i = 0; i < numVertex; i++){
				cycleDFS = new DirectedCycle(lowEdge[i][j]);
				boolean lowCycle = cycleDFS.hasCycle();


				cycleDFS = new DirectedCycle(highEdge[i][j]);
				boolean highCycle = cycleDFS.hasCycle();


				connectedDFS = new DirectedDFS(lowEdge[i][j], 0);
				boolean lowConnected = (connectedDFS.getConnectedComponents() >= i+2);


				connectedDFS = new DirectedDFS(highEdge[i][j], 0);
				boolean highConnected = (connectedDFS.getConnectedComponents() >= i+2);

				if (lowConnected){
					lowConnCount[i]++;
				}
				if (lowCycle){
					lowCycleCount[i]++;
				}
				if (highConnected){
					highConnCount[i]++;
				}
				if (highCycle){
					highCycleCount[i]++;
				}
			}
		}


		System.out.println("Graphs with probability 0.5");
		System.out.println("N\t\t#Cycles\t\t#Connected");
		for (int i = 0; i < numVertex; i++) {
			System.out.println(String.format("%2d\t\t%1d\t\t%1d", (i+2), lowCycleCount[i], lowConnCount[i]));
		}


		System.out.println("\n\nGraphs with probability 1/N");
		System.out.println("N\t\t#Cycles\t\t#Connected");
		for (int i = 0; i < numVertex; i++) {
			System.out.println(String.format("%2d\t\t%1d\t\t%1d", (i+2), highCycleCount[i], highConnCount[i]));
		}

	}
}
