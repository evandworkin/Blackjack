package Blackjack;

/*
 * A Dealer plays against Players, but by the rules of Blackjack
 * has similar traits to a Player. It is really just a Hand, but
 * it helps to think of it as a Player
 * @author Evan Dworkin
 */

// Dealer is really just a Hand, but it helps to think of it as a player
public class Dealer extends Player {
    public Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    public void reset() {
        hand = new Hand();
    }

    public String toString() {
        return "Dealer's hand is " + hand;
    }

    // Dealer plays according to predefined rules
    public void play(Shoe shoe) {
        System.out.println("Dealer's hand is " + hand + ".");
        while (!hand.done) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                
            }
            if (hand.value < 17) {
                System.out.println("Dealer hits");
                hand.hit(shoe);
            } else if (hand.value == 17 && hand.soft) {
                hand.hit(shoe);
                System.out.println("Dealer hits");
            } else {
                hand.stand();
                System.out.println("Dealer stands");
            }
            System.out.println("Dealer's hand is " + hand + ".");
            if (hand.bust) {
                System.out.println("Dealer busted!");
            }
        }
    }
}
