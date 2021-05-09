package aiburns.hw5;

import algs.hw5.Dictionary;
import edu.princeton.cs.algs4.StopwatchCPU;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * More complicated ZIPPER: Bonus Questions only
 */
public class BonusWordZipper {
	
	/**
	 * Main method to execute.
	 *
	 * From console, enter the start and end of the word zipper.
	 * 
	 * Can you beat  117.875 seconds for computing RESTAFF to SHERIFF 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String word1 = args[0];
		String word2 = args[1];
		
		StopwatchCPU cpu = new StopwatchCPU();
		
		Scanner sc = Dictionary.words();
		
		// do all work here
		
		sc.close();
		
		System.out.println(cpu.elapsedTime() + " seconds");
	}
}
