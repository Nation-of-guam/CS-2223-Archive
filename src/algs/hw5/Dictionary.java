package algs.hw5;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/** Use this class AS IS. Do not copy it into your project. */
public class Dictionary {
	
	public static Scanner words() {
		try {
			URL map = Dictionary.class.getResource("/algs/hw5/resources/words.english.txt");
			Scanner sc = new Scanner(map.openStream());
			return sc;
		} catch (IOException ioe) {
			System.err.println("Failed to read graph:" + ioe.getMessage());
			return null;
		}
	}
		
}
