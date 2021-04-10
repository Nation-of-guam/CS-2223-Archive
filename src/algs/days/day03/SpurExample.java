package algs.days.day03;

import edu.princeton.cs.algs4.StdIn;
import algs.days.day04.FixedCapacityStack;

public class SpurExample {
	
	/** Show left and right */
	static void top(FixedCapacityStack<String> stack, String right) {
		String result = "";
		
		for (String engine : stack) {
			result = engine + result;
		}
		
		System.out.println("left:[" + result + "]      right:" + right);
	}
	
	/** Represent spur in multiple lines */
	static void spur(int N, FixedCapacityStack<String> stack) {
		String pad = "       ";
		for (int i = 0; i < N; i++) { pad += " "; }
		
		for (String engine : stack) {
			System.out.println(pad + "  " + engine);
		}
		
		System.out.println(pad + "spur");
	}
	
	public static void main(String[] args) {
		// number of engines
		int N = 8;
		
		FixedCapacityStack<String> leftTrack = new FixedCapacityStack<>(N);
		FixedCapacityStack<String> spur = new FixedCapacityStack<>(N);
		String rightTrack = "";   // engines are pre-pended to the result
		
		char letter = 'A';
		for (int i = 0; i < N; i++) {
			leftTrack.push(Character.toString(letter));
			letter++;
		}
		
		
		while (rightTrack.length() < N) {
			top(leftTrack, rightTrack);
			System.out.println();
			spur(N, spur);
			
			System.out.println("\n------------------------------------------");
			System.out.println("enter [straight] or [toSpur] or [fromSpur]");
			String cmd = StdIn.readString();
			System.out.println();
			
			if (cmd.equalsIgnoreCase("toSpur") && !leftTrack.isEmpty()) {
				String engine = leftTrack.pop();
				spur.push(engine);
			} else if (cmd.equalsIgnoreCase("fromSpur") && !spur.isEmpty()) {
				String engine = spur.pop();
				rightTrack = engine + rightTrack;
			} else if (cmd.equalsIgnoreCase("straight") && !leftTrack.isEmpty()) {
				String engine = leftTrack.pop();
				rightTrack = engine + rightTrack;
			}
		}
		
		System.out.println("Final Result:" + rightTrack);
	}
	
	
}
