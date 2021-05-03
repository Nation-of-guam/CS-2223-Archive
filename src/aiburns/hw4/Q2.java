package aiburns.hw4;

import algs.hw4.map.GPS;
import algs.hw4.map.HighwayMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.DepthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The goal of this question is to:
 * 
 * 1. Find the western-most location in Massachusetts
 * 2. Find the eastern-most location in Massachusetts
 * 3. Determine the shortest distance between these two locations IN TERMS OF THE 
 *    TOTAL NUMBER OF HIGHWAY EDGES USED. YOU ARE NOT YET READY TO TAKE MILEAGE INTO ACCOUNT
 * 4. Next create a copy of the highway graph that removes all line segments from I-90, the 
 *    Massachusetts Turnpike toll road.
 * 5. From this copy, determine the shortest distance between these two locations IN TERMS OF THE 
 *    TOTAL NUMBER OF HIGHWAY EDGES USED. YOU ARE NOT YET READY TO TAKE MILEAGE INTO ACCOUNT.
 */
public class Q2 {
	static int[] counting;
	/**
	 * This method must create a copy of the graph, which you can do by recreate a graph with 
	 * the same number of vertices as the original one, BUT you only add an edge to the copy
	 * if the edge in the original graph IS NOT EXCLUSIVELY a line segment from the Mass Pike.
	 * 
	 * For example, in the data set you will see two nodes:
	 * 
	 * 		I-90@49|MA 42.169702 -72.580876
	 * 		I-90@51|MA 42.161558 -72.541995
	 * 
	 * These lines correspond to vertex #639 (the first one @49) and vertex #641 (the second one @51).
	 * Because the label for both of these vertices includes "I-90@" this edge must not appear in 
	 * the copied graph, since it is a highway segment exclusively on the Mass Turnpike.
	 * 
	 * Note that the edge is eliminated only when BOTH are present. For example, the following
	 * line segment will remain:
	 * 
	 * 		I-95(23)/MA128	                ==> vertex #705
	 * 		I-90@123B&I-95@24&MA128@24(95)  ==> vertex #1785
	 */
	static Information remove_I90_segments(Information info) {
		Graph copy = new Graph(info.graph.V());

		for (Integer vertIterator : counting){
			for (Integer edgeIterator : info.graph.adj(vertIterator)){
				if (!info.labels.get(vertIterator).contains("I-90@") || !info.labels.get(edgeIterator).contains("I-90@")){
					copy.addEdge(vertIterator, edgeIterator);
				}
			}
		}



		
		Information newInfo = new Information(copy, info.positions, info.labels);
		return newInfo;
	}
	
	
	/** 
	 * This helper method returns the western-most data point in the Information, given its latitude and
	 * longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int westernMostVertex(Information info) {
		try {
			return superlativeVertex(info, Math.class.getMethod("min", float.class, float.class), false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/** 
	 * This helper method returns the western-most data point in the Information, given its latitude and
	 * longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int easternMostVertex(Information info) {
		try {
			return superlativeVertex(info, Math.class.getMethod("max", float.class, float.class), false);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/** 
	 * This helper method returns the southern-most data point in the Information, given its latitude and
	 * longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int southernMostVertex(Information info) {
		try {
			return superlativeVertex(info, Math.class.getMethod("min", new Class[]{float.class, float.class}), true);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/** 
	 * This helper method returns the northern-most data point in the Information, given its latitude and
	 * longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int northernMostVertex(Information info) {
		try {
			return superlativeVertex(info, Math.class.getMethod("max", new Class[]{float.class, float.class}), true);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int superlativeVertex(Information info, Method minOrMax ,boolean northSouth) throws InvocationTargetException, IllegalAccessException {
		SeparateChainingHashST<Integer,GPS> nodeList = info.positions;
		Integer toReturn = 0;
		float toReturnPos;
		if (northSouth){
			toReturnPos = nodeList.get(toReturn).latitude;
		} else {
			toReturnPos = nodeList.get(toReturn).longitude;
		}
		for (int key : counting){
			float thisPos;
			if (northSouth){
				thisPos = nodeList.get(key).latitude;
			} else {
				thisPos = nodeList.get(key).longitude;
			}
			float superlativePos = (float) minOrMax.invoke(null, new Float[] {toReturnPos, thisPos});
			if (superlativePos == thisPos){
				toReturn = key;
				toReturnPos = thisPos;
			}
		}
		return toReturn;
	}
	
	public static void main(String[] args) {
		Information info;
		info = HighwayMap.undirectedGraph();
		counting = new int[info.graph.V()];
		for (int i = 0; i < info.graph.V(); i++){
			counting[i] = i;
		}
		int north = northernMostVertex(info);
		int south = southernMostVertex(info);
		int east = easternMostVertex(info);
		int west = westernMostVertex(info);
		System.out.println("North, South, East, West \n");
		System.out.println(info.labels.get(north));
		System.out.println(info.labels.get(south));
		System.out.println(info.labels.get(east));
		System.out.println(info.labels.get(west));

		int count = 0;
		BreadthFirstPaths bfsWest = new BreadthFirstPaths(info.graph, west);
		BreadthFirstPaths bfsSouth = new BreadthFirstPaths(info.graph, south);


		Iterable<Integer> westBFSEast = bfsWest.pathTo(east);
		Iterable<Integer> southBFSNorth = bfsSouth.pathTo(north);

		System.out.println("\nBFS West to East: ");
		for (Integer thisLoc : westBFSEast){
			/*System.out.println(info.labels.get(thisLoc));*/
			count++;
		}
		System.out.println(count);
		count = 0;

		System.out.println("\nBFS South to North: \n");
		for (Integer thisLoc : southBFSNorth){
			/*System.out.println(info.labels.get(thisLoc));*/
			count++;
		}
		System.out.println(count);
		count = 0;

		DepthFirstPaths dfsWest = new DepthFirstPaths(info.graph, west);
		DepthFirstPaths dfsSouth = new DepthFirstPaths(info.graph, south);

		Iterable<Integer> westDFSEast = dfsWest.pathTo(east);
		Iterable<Integer> southDFSNorth = dfsSouth.pathTo(north);

		System.out.println("\nDFS West to East: ");
		for (Integer thisLoc : westDFSEast){
			count++;
		}
		System.out.println(count);
		count = 0;

		System.out.println("\nDFS South to North: ");
		for (Integer thisLoc : southDFSNorth){
			count++;
		}
		System.out.println(count);

		Information no90 = remove_I90_segments(info);
		north = northernMostVertex(no90);
		south = southernMostVertex(no90);

		BreadthFirstPaths bfsWestno90 = new BreadthFirstPaths(no90.graph, west);
		BreadthFirstPaths bfsSouthno90 = new BreadthFirstPaths(no90.graph, south);


		Iterable<Integer> westBFSEastno90 = bfsWestno90.pathTo(east);
		Iterable<Integer> southBFSNorthno90 = bfsSouthno90.pathTo(north);

		System.out.println("\nBFS West to East: ");
		for (Integer thisLoc : westBFSEastno90){
/*
			System.out.println(no90.labels.get(thisLoc));
*/			count++;
		}
		System.out.println(count);
		count = 0;
		System.out.println("\nBFS South to North: ");
		for (Integer thisLoc : southBFSNorthno90){
/*
			System.out.println(no90.labels.get(thisLoc));
*/			count++;
		}
		System.out.println(count);
		count = 0;

	}
}
