package Blackjack;
import java.util.*;

/*
 * A Shoe is a collection of cards from which cards are drawn
 * during the game. A Shoe is made up of a variable number of
 * standard 4-suit 52-card decks.
 * @author Evan Dworkin
 */

public class Shoe {
    public ArrayList<Card> shoe;
    public int decks;

    public Shoe() {
        shoe = new ArrayList<Card>();
        decks = 0;
    }

    public Shoe(int d) {
        decks = d;
        shoe = new ArrayList<Card>();
        char[] suits = {'S', 'H', 'D', 'C'};
        for (int i = 0; i < d; i++) { // repeat for each deck
            for (int j = 0; j < 4; j++) { // suits
                for (int k = 2; k <= 10; k++) { //number cards
                    shoe.add(new Card(k, suits[j], "" + k));
                }
                // face cards
                shoe.add(new Card(10, suits[j], "J"));
                shoe.add(new Card(10, suits[j], "Q"));
                shoe.add(new Card(10, suits[j], "K"));
                shoe.add(new Card(11, suits[j], "A"));
            }
        }
    }

    public int size() {
        return shoe.size();
    }
    
    public String toString() {
        String s = "";
        for (Card c : shoe) {
            s += c + " ";
        }
        return s;
    }

    public void shuffle() {
        Collections.shuffle(shoe);
    }
}
