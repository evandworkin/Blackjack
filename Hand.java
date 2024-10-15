package Blackjack;
import java.util.ArrayList;

/*
 * A Hand holds cards and information on hand status,
 * such as the bet on the hand, if it has blackjack, value, etc.
 * @author Evan Dworkin
 */

public class Hand {
    public ArrayList<Card> cards; // list of the cards in the hand
    public int value; // value of the cards in the hand
    public int bet; // bet amount on the hand
    public double payout; // payout amount for the hand
    public boolean bust = false; // has the hand busted?
    public boolean soft; // is the hand soft?
    public boolean blackjack; // does tha hand have blackjack?
    public boolean done; // is the hand done for the round? (mostly commonly updated after standing or busting)

    public Hand() {
        cards = new ArrayList<Card>();
        value = 0;
        bet = 0;
        payout = 0.0;
        soft = false;
        bust = false;
        blackjack = false;
        done = false;
    }

    public Hand(int b) {
        cards = new ArrayList<Card>();
        value = 0;
        bet = b;
        payout = 0.0;
        soft = false;
        bust = false;
        blackjack = false;
        done = false;
    }

    public Hand(int b, Card c) {
        cards = new ArrayList<Card>();
        cards.add(c);
        value = 0;
        bet = b;
        payout = 0.0;
        soft = false;
        bust = false;
        blackjack = false;
        done = false;
        update();
    }

    public Hand(ArrayList<Card> cs) {
        cards = cs;
        value = 0;
        soft = false;
        done = false;
        update();
    }

    // internal method to add cards to the hand
    private void add(Card c) {
        cards.add(c);
        update();
    }

    // hand draws a card
    public void draw(Shoe shoe) {
        // System.out.println("Drawing a card from the shoe: " + shoe);
        Card drawn = shoe.shoe.get(0);
        add(drawn);
        shoe.shoe.remove(0);
    }

    public void hit(Shoe shoe) {
        draw(shoe);
    }

    public void stand() {
        done = true;
    }

    public int size() {
        return cards.size();
    }

    // check if the hand has blackjack
    public boolean has_blackjack() {
        blackjack = (cards.size() == 2 && value == 21);
        return blackjack;
    }

    // updates hand to reflect the current cards
    private void update() {
        int aces = 0;
        value = 0;
        for (Card c : cards) {
            if (c.name.equals("A")) {
                aces++;
            }
            value += c.rank;
        }
        while (value > 21 && aces != 0) {
            value -= 10;
            aces--;
        }
        bust = false;
        soft = false;
        if (value > 21) {
            bust = true;
            done = true;
        }
        if (aces > 0) {
            soft = true;
        }
        if (has_blackjack()) {
            blackjack = true;
            done = true;
        }
    }

    // string representation of the hand
    public String toString() {
        return "{" + cards.toString() + " | value: " + value + "}";
    }
}
