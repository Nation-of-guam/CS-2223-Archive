package algs.days.day10;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 
 	K	N	#BInsp	#Bexch	#CInsp		Time		Worst
	16	16384	46046	61408	34568129	0.2910		5120
	16	32768	92189	122537	138258000	0.7060		10240
	16	65536	184252	246131	552980228	3.1530		20480
	16	131072	368677	490267	2210283516	12.9310		40960
	16	262144	737046	982107	8843107737	52.0940		81920

	K	N	#BInsp	#Bexch	#CInsp		Time		Worst
	32	16384	61067	127110	21274111	0.1160		3072
	32	32768	122036	253317	84972212	0.4550		6144
	32	65536	244360	506663	339676212	1.7990		12288
	32	131072	488731	1017115	1358598970	7.1990		24576
	32	262144	977193	2035159	5434375869	29.0560		49152

	K	N	#BInsp	#Bexch	#CInsp		Time		Worst
	64	16384	76575	259189	12678440	0.0660		1792
	64	32768	153420	515059	50654768	0.2670		3584
	64	65536	306626	1031712	202527033	1.0820		7168
	64	131072	613372	2058804	809752185	4.4730		14336
	64	262144	1226596	4130581	3238276563	18.0600		28672

	K	N	#BInsp	#Bexch	#CInsp		Time		Worst
	128	16384	92531	520717	7396915	0.0380		1024
	128	32768	185085	1038087	29508406	0.1590		2048
	128	65536	370334	2085947	117866619	0.6460		4096
	128	131072	740489	4162336	471101211	2.8310		8192
	128	262144	1481314	8319452	1883768754	11.4650		16384

Average Case Analysis. This will take awhile...
12	17723059200	37.0000	479001600
11	21555072000	45.0000	479001600
10	23311411200	48.6667	479001600
9	22992076800	48.0000	479001600
8	23087877120	48.2000	479001600
7	22353408000	46.6667	479001600
6	21623500800	45.1429	479001600
 
 */
public class PerformanceNK {
	
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
	static void averagePermutationAnalysis(int K) {
		int a,b,c,d,e,f,g,h,i,j,k,l,ctr=0;
		long nfact = 12*11*10*9*8*7*6*5*4*3*2;
		int[] s = new int[12];
		
		long totalNumInspections = 0;
		int numTrials = 0;
		for (s[0] = 0; s[0] < 12; s[0]++) 
 		 for (s[1] = 0; s[1] < 12; s[1]++) 
		  if (!in (s[1], s, 1))
		   for (s[2] = 0; s[2] < 12; s[2]++) 
			if (!in (s[2], s, 2)) 
			 for (s[3] = 0; s[3] < 12; s[3]++) 
			  if (!in (s[3], s, 3 ))
			   for (s[4] = 0; s[4] < 12; s[4]++) 
				if (!in (s[4], s, 4 ))
				 for (s[5] = 0; s[5] < 12; s[5]++) 
				  if (!in (s[5], s, 5 ))
				   for (s[6] = 0; s[6] < 12; s[6]++) 
					if (!in (s[6], s, 6 ))
					 for (s[7] = 0; s[7] < 12; s[7]++) 
					  if (!in (s[7], s, 7 ))
					   for (s[8] = 0; s[8] < 12; s[8]++) 
						if (!in (s[8], s, 8 ))
						 for (s[9] = 0; s[9] < 12; s[9]++) 
						  if (!in (s[9], s, 9 ))
						   for (s[10] = 0; s[10] < 12; s[10]++)
						    if (!in (s[10], s, 10 ))
							 for (s[11] = 0; s[11] < 12; s[11]++) 
							  if (!in (s[11], s, 11 )) {
								  numTrials++;
								 NK nk = new NK(K);
								 nk.add(s[0]);
								 nk.add(s[1]);
								 nk.add(s[2]);
								 nk.add(s[3]);
								 nk.add(s[4]);
								 nk.add(s[5]);
								 nk.add(s[6]);
								 nk.add(s[7]);
								 nk.add(s[8]);
								 nk.add(s[9]);
								 nk.add(s[10]);
								 nk.add(s[11]);

								 // contains
								 nk.numInspects = 0;
								 for (int v = 0; v < 12; v++) {
									 if (!nk.contains(v)) {
										 System.err.println("Error in NK not finding value");
										 return;
									 }
								 }
								 totalNumInspections += nk.numInspects;
								 
							 }
		double average = totalNumInspections;
		average /= nfact;
		System.out.printf("%d\t%d\t%.4f\t%d%n", K, totalNumInspections, average, numTrials);
	}
	
	static void averageRandomAnalysis() {
		
		int n = 128;
		for (int k = 16; k <= 128; k *= 2) {
			
			int[] tmp = new int [n];
			for (int i = 1; i <= n; i++) {
				tmp[i-1] = i;
			}
			
			long totalNumInspects = 0;
			for (int t = 0; t < 1000000; t++) {
				StdRandom.shuffle(tmp);
				NK nk = new NK(k);
				
				for (int i : tmp) {
					nk.add(i);
				}
				
				// now find in order
				nk.numInspects = 0;
				int numFound = 0;
				for (int i = 1; i <= n; i++) {
					if (nk.contains(i)) { numFound++; }
				}
				totalNumInspects += nk.numInspects;
				
				if (numFound != n) {
					System.err.println("Unexpected: mismatch in found and planned.");
					return;
				}
			}
			System.out.println(k + "," + n + "," + totalNumInspects);
		}
	}
	
	public static void main(String[] args) {
		
		
		// design experiment to evaluate performance of 'contains'
		// This is 
		System.out.println("Average Case Analysis. This will take awhile...");
		for (int k = 8; k > 0; k--) {
			averagePermutationAnalysis(k);
		}
		
		// choose multiple K and try any number of operations.
		for (int k = 16; k <= 128; k *= 2) {
			StdOut.println("\tK\tN\t#BInsp\t#Bexch\t#CInsp\t\tTime\t\tWorst");
			for (int n = 16384; n <= 262144; n*= 2) {
				NK nk = new NK(k);
				
				int[] tmp = new int [n];
				for (int i = 1; i <= n; i++) {
					tmp[i-1] = i;
				}
				
				StdRandom.shuffle(tmp);
				
				// now add in given order. Keep track of *total* comparisons.
				nk.numInspects = 0;
				nk.numExchanges = 0;
				for (int i : tmp) {
					nk.add(i);
				}
				StdOut.printf("\t%d\t%d\t%d\t%d\t", k, n, nk.numInspects, nk.numExchanges);
				
				// now find in order
				nk.numInspects = 0;
				int numFound = 0;
				Stopwatch sw = new Stopwatch();
				for (int i = 1; i <= n; i++) {
					if (nk.contains(i)) { numFound++; }
				}
				StdOut.printf("%d\t%.4f\t", nk.numInspects, sw.elapsedTime());
				
				// worst case analysis. Look for number that doesn't exist. Do this just once, since same each time
				// also, make this number be greater than any, to force max number of binary array search
				nk.numInspects = 0;
				nk.contains(n+1);
				StdOut.printf("\t%d\n", nk.numInspects);
				
			}
			StdOut.println();
		}

				
	}
}
