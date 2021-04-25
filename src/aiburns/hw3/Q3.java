package aiburns.hw3;


import algs.hw3.ShakespearePlay;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Aidan Burns  4/23/2021
 * This project does Q3 on the IntelliJ IDEA
 */

public class Q3 {
    public static void main(String[] args) throws IOException {
        ArrayList<ShakespearePlay> plays = new ArrayList<>();
        for (int i = 1; i <= 38 ; i++) {
            plays.add(new ShakespearePlay(i));
        }

        ArrayList<BST> playsBSTs = new ArrayList<>();
        BST allPlays = new BST();
        for (int i = 0; i < plays.size(); i++){
            BST toAdd = new BST();
            for (String words : plays.get(i)){
                toAdd.put(words);
                allPlays.put(words);
            }
            playsBSTs.add(toAdd);
        }

        String mostCommon = allPlays.mostFrequent();

        for (int i = 0; i < plays.size(); i++){
            ArrayList<String> thisTop5 = top5(playsBSTs.get(i));
            if (thisTop5.contains(mostCommon)){
                plays.remove(i);
                playsBSTs.remove(i);
                i--;
            } else {
                System.out.println(thisTop5.toString() + "\t" + plays.get(i).getTitle());
            }
        }

        System.out.println("3.1");
        plays = new ArrayList<>();
        for (int i = 1; i <= 38 ; i++) {
            plays.add(new ShakespearePlay(i));
        }

        allPlays = new BST();
        for (int i = 0; i < plays.size(); i++){
            for (String words : plays.get(i)){
                allPlays.put(words, words.length());
            }
        }
        while (allPlays.mostFrequent().contains("-")){
            allPlays.delete(allPlays.mostFrequent());
        }
        System.out.println(allPlays.mostFrequent());
        System.out.println("I had to look this up, but apparently it means \"the state of being able to achieve honours\", " +
                "which I still don't know what it means, I think it means like that you can still do good things?  idk");
    }

    public static ArrayList<String> top5(BST toCheck){
        BST checking = toCheck.copy();
        ArrayList<String> toReturn = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            String ithCommon = checking.mostFrequent();
            toReturn.add(ithCommon);
            checking.delete(ithCommon);
        }
        return toReturn;
    }
}
