package Blackjack;

/*
 * A Card object to hold rank and suit information
 * @author Evan Dworkin
 */

public class Card {
    public int rank; //numeric value of a card 1-10, face cards are 10
    public char suit; //suit of a card, given as a character (S/H/D/C)
    public String name; //name of a card (ex Q = queen)

    public Card() {
        rank = 0;
        suit = '0';
        name = "none";
    }

    public Card(int r) {
        rank = r;
        suit = 'S';
        name = "" + r;
    }

    public Card(int r, char s, String n) {//builds a card given rank and suit information
        rank = r;
        suit = s;
        name = n;
    }

    public Card(String abr) { //builds a card based on its name (ex: 9H = nine of hearts)
        assert (abr.length() >= 2);
        if (abr.charAt(0) > 49 && (int) abr.charAt(0) <= 57) {
            rank = abr.charAt(0) - 48;
        }
        else {
            switch (abr.charAt(0)) {
                case ('A'):
                    rank = 11;
                    break;
                case ('J'):
                    rank = 10;
                    break;
                case ('Q'):
                    rank = 10;
                    break;
                case ('K'):
                    rank = 10;
                    break;
                case ('1'): //a 10, since its charAt(0)
                    rank = 10;
                    break;
            }
        }
        suit = abr.charAt(abr.length() - 1);
        name = abr.substring(0, abr.length() - 1);
    }

    public String toString() {
        return name + suit;
    }

    public boolean equals(Card other) {
        return this.rank == other.rank && this.suit == other.suit && this.name.equals(other.name);
    }
}
