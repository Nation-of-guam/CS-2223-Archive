package aiburns.hw2;
import algs.hw2.*;

/**
 * @author Aidan Burns  4/5/2021
 * This project does DeckTester on the IntelliJ IDEA
 */

public class DeckTester {
    public static void main(String[] args) {
        for (int i = 2; i < 5; i++) {
            MyDeck iterativeDeck = new MyDeck(i);
            System.out.println("Deck Test, n=" + i);
            System.out.println(iterativeDeck.representation() + " \n");

            Deck inTest = iterativeDeck.copy();
            inTest.in();
            System.out.println("in Test, n=" + i);
            System.out.println(inTest.representation()+ "\n ");
            System.out.println("Deck Test, n=" + i);
            System.out.println(iterativeDeck.representation() + " \n");

            Deck outTest = iterativeDeck.copy();
            outTest.out();
            System.out.println("out Test, n=" + i);
            System.out.println(outTest.representation()+ "\n ");

            Deck copyTest = iterativeDeck.copy();
            System.out.println("copy Test, n=" + i);
            System.out.println(copyTest.representation()+ "\n ");

        }
    }
}
