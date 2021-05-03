package algs.days.day21;

import algs.hw4.map.HighwayMap;
import algs.hw4.map.Information;

import java.io.IOException;
import java.net.URL;

public class Canada {
	public static void main(String[] args) throws IOException {
		
		URL map = HighwayMap.class.getResource("/algs/hw4/resources/CAN-country-simple.tmg");
		Information info = HighwayMap.undirectedGraph(map.openStream());

		// you might find this file useful....
	}
}
