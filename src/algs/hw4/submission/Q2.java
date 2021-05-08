package algs.hw4.submission;

import algs.hw4.map.*;
import edu.princeton.cs.algs4.Graph;

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
		Graph copy = null;
		
		// DO YOUR WORK HERE...
		
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
		throw new RuntimeException ("TO BE COMPLETED BY STUDENT");
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
		throw new RuntimeException ("TO BE COMPLETED BY STUDENT");
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
		throw new RuntimeException ("TO BE COMPLETED BY STUDENT");
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
		throw new RuntimeException ("TO BE COMPLETED BY STUDENT");
	}
	
	public static void main(String[] args) {
	
		
	}
}
