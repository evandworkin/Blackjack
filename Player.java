package Blackjack;
import java.util.*;

/*
 * A Player holds their bankroll, their hand(s), and their unique ID
 * The player will belong to a Table that they are playing at
 * @author Evan Dworkin
 */

public class Player {
    private static int ID = 0;

    public ArrayList<Hand> hands; // a single player can have multiple hands (splitting/playing multiple hands)
    public int bankroll; // money in the purse
    public int playerID; // unique player ID (player number)

    public Player() {
        hands = new ArrayList<Hand>();
        hands.add(new Hand());
        bankroll = 0;
        playerID = generatePlayerID();
    }

    // generate a player with a given bankroll
    public Player(int b) {
        hands = new ArrayList<Hand>();
        hands.add(new Hand());
        bankroll = b;
        playerID = generatePlayerID();
    }

    // reset the player's hands
    public void reset() {
        hands.clear();
        hands.add(new Hand());
    }

    // string representation of the player
    public String toString() {
        String repr = "Player " + playerID + "'s ";
        if (hands.size() == 1) {
            repr += "hand is " + hands.get(0);
        } else {
            repr += "hands are {";
            for (Hand hand : hands) {
                repr += hand.toString() + ", ";
            }
            repr = repr.substring(0, repr.length() - 2);
            repr += "}";
        }
        repr += ". They have $" + bankroll + " in their bankroll";
        return repr;
    }

    // player plays their hand
    // blackjacks are accounted for in the update() method of Hand
    public void play(Shoe shoe, Scanner console) {
        String action;
        int size = hands.size();
        boolean split = false; // prevent printing the old hand after splitting
        for (int i = 0; i < size; i++) {
            Hand hand = hands.get(i);
            while (!hand.done) {
                split = false;
                System.out.println("Your hand is " + hand + ". What would you like to do? (hit/stand/double/split)");
                action = console.next();
                switch (action) {
                    case "hit":
                        hand.hit(shoe);
                        break;
                    case "stand": 
                        hand.stand();
                        break;
                    case "split":
                        if (hand.size() != 2) {
                            System.out.println("You cannot split that hand");
                            break;
                        }
                        if (!hand.cards.get(0).name.equals(hand.cards.get(1).name)) {
                            System.out.println("You cannot split that hand");
                            break;
                        }
                        hands.add(new Hand(hand.bet, hand.cards.get(0)));
                        hands.add(new Hand(hand.bet, hand.cards.get(1)));
                        hand.done = true;
                        hands.remove(hand);
                        size += 1;
                        i -= 1;
                        split = true;
                        break;
                    case "double":
                        hand.bet *= 2;
                        hand.hit(shoe);
                        hand.stand();
                        break;
                    default:
                        System.out.println("Please enter either (hit/stand/double/split)");
                }
            }
            if (!split) {
                System.out.println("Your hand is " + hand + ".");
            }
            if (hand.bust) {
                System.out.println("You busted!");
            }
        }
    }

    // internal method to generate a player's ID
    private static int generatePlayerID() {
        return ID++;
    }
}
