package algs.hw3;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Strips away everything before "ACT 1"
 *
 * Everything else stripped of punctuation and converted to lowercase.
 *
 * All words that appear in UPPERCASE are assumed to be names, and are summarily
 * stripped from the output. Yes, this grabs the occasional "LORD" or "FIRST" or "SECOND"
 * but that's fine. Just roll with it.
 * 
 * Ugh. Sometimes the em-dash appears "--" and this is dealt with. Also any numbers are skipped.
 * 
 */
public class ShakespearePlay implements Iterable<String> {

	List<String> words = new ArrayList<>();
	String title = null;
	
	/** 
	 * Load up the play from 1 to 38. If you provide an invalid integer, a
	 * runtime exception occurs.
	 */
	public ShakespearePlay(int playNumber)  {
		try {
			// grab title!
			URL ss = this.getClass().getResource("/algs/hw3/resources/plays/" + playNumber + ".txt");
			Scanner sc = new Scanner(ss.openStream());
			title = sc.nextLine();
			sc.close();
			
			sc = new Scanner(ss.openStream());
			processScanner(sc);
			sc.close();
			return;
		} catch (IOException ioe) {
			throw new RuntimeException("Unable to open play # " + playNumber + " (must be 1 <= x <= 38.");
		}
	}
	
	void processScanner(Scanner sc) {
		List<String> namesToIgnore = new ArrayList<>();
		boolean accept = false;
		String [] justOne = new String[1];
		while (sc.hasNext()) {
			String s = sc.next();
			
			// skip all until we get to ACT 1. Grab all names in CAPS as
			// being worthy of skipping
			if (!accept) {

				if (s.contains("ACT")) {
					accept = true;
				}

				if (s.matches("[A-Z]+")) { 
					if (!namesToIgnore.contains(s)) {
						namesToIgnore.add(s.toLowerCase());
					}
				}
				continue;
			}
			
			String wordGroup[];
			if (s.contains("--")) {
				wordGroup = s.split("--"); 
			} else {
				wordGroup = justOne;
				justOne[0] = s;
			}
			
			for (String possibleWord : wordGroup) {
				// allow hyphenated words?
				
				possibleWord = possibleWord.replaceAll("-", "123456");
				// clean up input
				String word = possibleWord.replaceAll("\\p{Punct}", "");
				word = word.replaceAll("123456", "-");
				
				// if all caps then must be a name. Should have been detected
				// before, but might be a small bit-part... Allow "A" and "I"
				// to come through...
				if (word.matches("[A-Z]+") && word.length() > 1) {
					if (!namesToIgnore.contains(word)) {
						namesToIgnore.add(word.toLowerCase());
						continue;
					}
				}
				
				word = word.toLowerCase();
				if (namesToIgnore.contains(word)) { continue; }  // SKIP NAMES
				
				// Is this word a numeric value? Ignore if so.
				try {
					Double.parseDouble(word);
					
					continue;  // was a number! Skip it
				} catch (NumberFormatException nfe) {
					// continue onwards
				}
				
				if (word.length() > 0) {
					words.add(word.toLowerCase());
				}
			}
		}
	}

	
	/** Return the number of words prepared to be returned. */
	public int size() {
		return words.size();
	}
	
	/** Return iterator to the words loaded up (without punctuation). */
	public Iterator<String> iterator() {
		return words.iterator();
	}

	/** Return the title of the play. */
	public String getTitle() {
		return title;
	}
	
	// quick demonstration code
	public static void main(String[] args) throws IOException { 
		ShakespearePlay sp = new ShakespearePlay(7);
		System.out.println(sp.getTitle() + " has " + sp.size() + " words.");
		int i = 0;
		String word = "i";
		for (String s : sp) {
			if (s.equals(word)) {
				i++;
			}
		}
		System.out.println("It says " + word + " " + i + " times!");
	}
}
