package aiburns.hw2;

import algs.hw2.Card;
import algs.hw2.Deck;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Aidan Burns  4/5/2021
 * This project does DeckTester on the IntelliJ IDEA
 */

public class DeckTester {
    public static int[] factorialValues;
    public static void main(String[] args) throws Exception{

        Card[] test = new Card[]{new Card("AC"), new Card("2C")};
        System.out.println(test[0].compareTo(test[1]));


        /*for (int i = 2; i < 14; i++) {
            MyDeck iterativeDeck = new MyDeck(i);
            System.out.println("Deck Test, n=" + i);
            System.out.println(iterativeDeck + " \n");


            Deck inTest = iterativeDeck.copy();
            inTest.in();
            System.out.println("in Test, n=" + i);
            System.out.println(inTest + "\n ");

            Deck outTest = iterativeDeck.copy();
            outTest.out();
            System.out.println("out Test, n=" + i);
            System.out.println(outTest + "\n ");

            System.out.println("copy Test, n=" + i);
            System.out.println(iterativeDeck + "\n ");
        }

        MyDeck testDeck = new MyDeck(27);

        System.out.println("\t" + 27 + "\t \t \t" +
                countingTests(testDeck,
                        MyDeck.class.getMethod("out"),
                        MyDeck.class.getMethod("isInOrder")));*/



        /*System.out.println("Q1.1");
        System.out.println("max_rank \t #in()");
        for (int i = 1; i < 21; i++) {
            MyDeck referenceDeck = new MyDeck(i);

            System.out.println("\t" + i + "\t \t \t" +
                    countingTests(referenceDeck,
                            MyDeck.class.getMethod("out"),
                            MyDeck.class.getMethod("isInOrder")));

        }*/

        System.out.println("Q1.1");
        System.out.println("max_rank \t #in()");
        for (int i = 1; i < 21; i++) {
            MyDeck referenceDeck = new MyDeck(i);

            System.out.println("\t" + i + "\t \t \t" +
                    countingTest(referenceDeck, true));

        }

        System.out.println("\n  \n");

        System.out.println("Q1.2");
        System.out.println("max_rank \t #out()");
        for (int i = 1; i < 21; i++) {
            MyDeck referenceDeck = new MyDeck(i);

            System.out.println("\t" + i + "\t \t \t" +
                    countingTest(referenceDeck, false));

        }

        System.out.println("\n  \n");


        System.out.println("Q1.3");
        System.out.println("max_rank \t #in()");

        MyDeck referenceDeck = new MyDeck(13);

        System.out.println("\t" + 13 + "\t \t \t" +
                countingTests(referenceDeck,
                        MyDeck.class.getMethod("in"), MyDeck.class.getMethod("isInReverseOrder")));



        System.out.println("\n  \n");


        System.out.println("Q1.3.1");
        boolean errorThrown = false;
        int h = 0;
        while (!errorThrown){
            h++;
            referenceDeck = new MyDeck(h);

            try {
                countingTests(referenceDeck,
                        MyDeck.class.getMethod("in"), MyDeck.class.getMethod("isInReverseOrder"));
            } catch (ArithmeticException e){
                errorThrown = true;
            }
        }
        System.out.println("The Minimum size of deck that cannot be reversed is: " + h);

    }

    protected static int countingTest(Deck deckToCheck, boolean inShuffle){
        int toReturn = 0;
        Deck toShuffle = deckToCheck.copy();
        if (inShuffle){
            toShuffle.in();
        } else {
            toShuffle.out();
        }
        toReturn++;

        while (!toShuffle.isInOrder()){
            if (inShuffle){
                toShuffle.in();
            } else {
                toShuffle.out();
            }
            toReturn++;
        }



        return toReturn;
    }

    /**
     *
     * @param deckToCheck the reference deck to shuffle.
     * @param shuffleType The type of shuffle for the deck
     * @param orderCheck the type of check for the order of the deck of cards.  MUST RETURN BOOLEAN
     * @return the number of times it takes for the deck to get back to the type of order defined in orderCheck
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException throws when deckToCheck does not pass the orderCheck
     * @throws
     */
    protected static int countingTests(Deck deckToCheck, Method shuffleType, Method orderCheck)
            throws InvocationTargetException, IllegalAccessException, IllegalArgumentException, ArithmeticException{
        if (!deckToCheck.isInOrder()) {
            throw new IllegalArgumentException("Deck being consumed must be in order");
        }

        Deck shuffled = deckToCheck.copy();
        shuffleType.invoke(shuffled);
        long h = 1;

        while (orderCheck.invoke(shuffled).equals(Boolean.valueOf(false))){
            h++;
            shuffleType.invoke(shuffled);

            if (h > (Math.pow(2, 25))){
                throw new ArithmeticException("Sorry cheif, it's just not going to happen");
            }
        }

        return (int) h;
    }



}
