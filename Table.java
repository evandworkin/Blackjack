package Blackjack;
import java.util.*;

/*
 * A Table emulates a Blackjack table at a casino. It holds its dealer,
 * it's shoe, and the players currently playing at the table.
 * The table can be visualized through the GUI
 * @author Evan Dworkin
 */

public class Table {
    public Scanner console;
    public Dealer dealer;
    public ArrayList<Player> players;
    public Shoe shoe;

    public Table() {
        dealer = new Dealer();
        players = new ArrayList<Player>();
        shoe = new Shoe(0);
    }

    public Table(Shoe s, Scanner c) {
        dealer = new Dealer();
        players = new ArrayList<Player>();
        shoe = s;
        console = c;
    }

    // adds a player to the table
    public void join(Player p) {
        players.add(p);
    }

    // removes a player from the table
    public void leave(Player p) {
        players.remove(p);
    }

    // deals two cards to every player and the dealer
    public void deal() {
        dealer.hand.draw(shoe);
        dealer.hand.draw(shoe);
        for (Player player : players) {
            for (Hand hand : player.hands) {
                hand.draw(shoe);
                hand.draw(shoe);
            }
        }
    }

    // resets the hands of the dealer and all the players
    public void reset() {
        dealer.reset();
        for (Player player : players) {
            player.reset();
        }
    }

    // string representation of the Table
    public String toString() {
        String repr = "";
        repr += dealer;
        repr += "\nThe players at this table are {\n";
        for (Player player : players) {
            repr += "\t" + player + ",\n";
        }
        repr = repr.substring(0, repr.length() - 2);
        repr += "\n}";
        return repr;
    }

    // runs a hand at the table
    /*
     * THE play() METHOD IN Table RUNS THE GAME IN THE CONSOLE.
     * THE play() METHOD IN GUI RUNS THE GAME IN THE GUI.
     */
    public void play() {
        if (shoe.size() < 15) {
            shoe = new Shoe(shoe.decks);
            shoe.shuffle();
        }
        System.out.println("---------------------------------------------");
        // place bets
        for (Player player : players) {
            System.out.println("*** Player " + player.playerID + " ***");
            System.out.println("Your bankroll is $" + player.bankroll);
            for (Hand hand : player.hands) {
                System.out.print("How much would you like to bet?\n$");
                hand.bet = console.nextInt();
                System.out.println();
            }
        }

        // deal the cards
        deal();

        System.out.println("The dealer's upcard is " + dealer.hand.cards.get(0));
        // check for dealer blackjack. if they do everyone must stand
        if (dealer.hand.has_blackjack()) {
            System.out.println("Dealer had blackjack!");
            System.out.println(dealer);
            for (Player player : players) {
                for (Hand hand : player.hands) {
                    hand.stand();
                    hand.payout = -1.0;
                    if (hand.has_blackjack()) {
                        hand.payout = 1.5;
                    }
                    player.bankroll += hand.payout * hand.bet;
                    System.out.println("Player " + player.playerID + " had a payout of " + hand.payout * hand.bet);
                }
                System.out.println("Player " + player.playerID + "'s bankroll is now " + player.bankroll + "\n");
            }
            reset();
            return;
        }

        // players play in order
        // player blackjacks are accounted for in the update() method of Hand
        String action;
        for (Player player : players) {
            System.out.println("*** Player " + player.playerID + " ***");
            ArrayList<Hand> hands = player.hands;
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

        // dealer plays
        System.out.println("*** Dealer ***");
        dealer.play(shoe);
        System.out.println();

        // payouts
        for (Player player : players) {
            System.out.println("*** Player " + player.playerID + " ***");
            for (Hand hand : player.hands) {
                // check for player blackjack
                if (hand.has_blackjack()) {
                    System.out.println("You have blackjack!");
                    hand.payout = 1.5;
                } else if (hand.bust) {
                    hand.payout = -1.0;
                } else if (dealer.hand.bust) {
                    hand.payout = 1.0;
                } else if (hand.value > dealer.hand.value) {
                    hand.payout = 1.0;
                } else if (hand.value == dealer.hand.value) {
                    hand.payout = 0.0;
                } else if (hand.value < dealer.hand.value) {
                    hand.payout = -1.0;
                }
                // update bankroll
                player.bankroll += hand.payout * hand.bet;

                // print results
                System.out.println("Player " + player.playerID + " had a payout of " + hand.payout * hand.bet);
            }
            System.out.println("Player " + player.playerID + "'s bankroll is now " + player.bankroll + "\n");
        }

        // reset table
        reset();
    }
}
