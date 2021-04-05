This exercise shows how to optimize existing code for speed. The overall algorithm
has not changed, but rather the data structures have.

Benchmark of all uses "gradstenseshine"

 Speed up goes from:
 
  9.38 days     \
  3.627 seconds / This is the million-fold speed up
  0.244 seconds \
  0.097 seconds  - This is a 43-fold speed up; not as impressive but still very helpful
  0.084 seconds /


0. Anagram15 -- naive brute force implementation (estimated 9.38 days)

   no structure at all. Still uses words[] array of ordered Strings.

   Using a 15-nested for loop structure, this checks all 15! permutations

1. HeinemanFinder (3.627 with 934268 lookups)

  structure is:
  
    	static String[] words;
    	
  Baseline implementation.  3.627 with 934268 lookups.
  
  
2. StringBufferHeinemanFinder (0.244 with 39677 lookups)

  structure is:
  
        static StringBuilder[][] words;
   
  So instead of having a single array, I have k arrays, for each of the lengths. So words[k][]
  contains an array of words that have k letters. This reduces the number of lookups, since
  you are focused only on the 15-letter words.
  
  PROBLEM: Since StringBuilder doesn't have compareTo() operation (!!) it makes this more
  complicated to determine (efficiently) whether an anagram has been seen before. I note that
  even the hashCode is different, since they don't override the default hashCode method!
   
  Must move on!
  
3. StriatedHeinemanFinder (0.218 with 39677 lookups)

  structure is:
  
        static String[][] words;
   
  So instead of having a single array, I have k arrays, for each of the lengths. So words[k][]
  contains an array of words that have k letters. This reduces the number of lookups, since
  you are focused only on the 15-letter words.
  
4. RawArraysHeinemanFinder (0.097 with 39677 lookups)

  structure is:
  
        static char[][][] words;   // triple-dimension
        
  now we have a triply-dimensioned array. words[k] is a two-dimensional array of words that all
  have k-letters. words[k][i][] is a single-dimension array containing the characters for the ith
  word that has k letters. words[k][i][j] is the jth character in the ith word that has k letters.
  For the record, these arrays are all in reverse order, which makes it more efficient to build,
  thus the binary array search is in reverse order
  
  Still uses MyFixedCapacityStack as a more efficient implementation to work with.
  
5. OptimizedRawArraysHeineman (0.084 with 39677 lookups)

  Even further optimization to ensure all comparisons are directly to the words[][][] array, rather
  than (arbitrarily) building up a sample word as an object from the letters in the permutation.
  
  Not sure how to get any lower than this.
  
  
 