package algs.days.day21;

import java.io.IOException;
import java.net.URL;

import algs.hw4.map.*;

public class Canada {
	public static void main(String[] args) throws IOException {
		
		URL map = HighwayMap.class.getResource("/algs/hw4/resources/CAN-country-simple.tmg");
		Information info = HighwayMap.undirectedGraph(map.openStream());

		// you might find this file useful....
	}
}
