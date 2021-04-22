package algs.days.day13;

import algs.days.day08.Quick;

public class LinearCheck {
	static boolean in (int a, int[] ar, int maxIndex) {
		for (int i = 0; i < maxIndex; i++) {
			if (ar[i] == a) { return true; }
		}
		return false;
	}
	
	/**
	 * Computes total of 12! permutations
	 * 
	 * 25291284480 is the final sum, which gives an average of 52.8
	 * 
	 */
	static void averagePermutationAnalysis() {
		Integer[] comp = new Integer[11];
		long totalNumLess = 0;
		long totalNumExch=0;
		int numTrials = 0;
		int s[] = new int[11];
		for (s[0] = 0; s[0] < 11; s[0]++) 
	 		 for (s[1] = 0; s[1] < 11; s[1]++) 
			  if (!in (s[1], s, 1))
			   for (s[2] = 0; s[2] < 11; s[2]++) 
				if (!in (s[2], s, 2)) 
				 for (s[3] = 0; s[3] < 11; s[3]++) 
				  if (!in (s[3], s, 3 ))
				   for (s[4] = 0; s[4] < 11; s[4]++) 
					if (!in (s[4], s, 4 ))
					 for (s[5] = 0; s[5] < 11; s[5]++) 
					  if (!in (s[5], s, 5 ))
					   for (s[6] = 0; s[6] < 11; s[6]++) 
						if (!in (s[6], s, 6 ))
						 for (s[7] = 0; s[7] < 11; s[7]++) 
						  if (!in (s[7], s, 7 ))
						   for (s[8] = 0; s[8] < 11; s[8]++) 
							if (!in (s[8], s, 8 ))
							 for (s[9] = 0; s[9] < 11; s[9]++) 
							  if (!in (s[9], s, 9 ))
							   for (s[10] = 0; s[10] < 11; s[10]++)
							    if (!in (s[10], s, 10 )) {
						    	// now we have eleven elements in array s. Find median
						    	
								  numTrials++;
									comp[0] = s[0];
									comp[1] = s[1];
									comp[2] = s[2];
									comp[3] = s[3];
									comp[4] = s[4];
									comp[5] = s[5];
									comp[6] = s[6];
									comp[7] = s[7];
									comp[8] = s[8];
									comp[9] = s[9];
									comp[10] = s[10];
									Integer result = (Integer)QuickSelect.quickSelect(comp, 5, 0, 10);
									totalNumExch += QuickSelect.numExch;
									totalNumLess += QuickSelect.numLess;
									QuickSelect.numExch = QuickSelect.numLess = 0;
									
									if (result != 5) {
										System.out.println("FAILS on " + comp);
										System.exit(1);
									}
							    }
								System.out.println(numTrials);
								System.out.println("Average Exch:" + (1.0*totalNumExch)/numTrials);
								System.out.println("Average Less:" + (1.0*totalNumLess)/numTrials);
	}
								  
	public static void main(String[] args) {
		//averagePermutationAnalysis();
		long totalNumExch = 0;
		long totalNumLess = 0;
		int TRIALS = 10000;
		System.out.println("N\t#exc\t#less");

		for (int n = 256; n <= 32768; n*= 2) {
			int SIZE = n-1;
			for (int t = 0; t < TRIALS; t++) {
				Integer[] comp = new Integer[SIZE];
				for (int i = 0; i < comp.length; i++) {
					comp[i] = (int) (Math.random()*16777216);
				}
				
				QuickSelect.numExch = QuickSelect.numLess = 0;
				Integer result = (Integer)QuickSelect.quickSelect(comp, comp.length/2, 0, comp.length-1);
				Quick.sort(comp);
				totalNumExch += QuickSelect.numExch;
				totalNumLess += QuickSelect.numLess;
			}
			
			System.out.println(String.format("%6d\t%12f\t%12f", SIZE, (1.0*totalNumExch)/TRIALS, (1.0*totalNumLess)/TRIALS));
		}		
	}

}
