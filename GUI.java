package Blackjack;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* 
 * This GUI implementation is a bit scuffed.
 * It's my first time making a GUI, so I kept it simple
 * but I also only made it compatible with one player,
 * even though the underlying code can handle multiple players.
 * 
 * UPDATE: The GUI works with multiple players, but it can
 * only display one at a time
 * 
 * @author Evan Dworkin
 */


/*
 * TO DO
 * Allow players to choose their own name?
 * 
 * Check for sufficient funds to split or double
 * 
 * ADD CARD IMAGES
 * 
 * playtest for bugs?
 *      If you split kings, then you get an Ace, it instantly moves on
 *      When dealer has blackjack it is too instant
 */

public class GUI implements ActionListener {
    // Logic variables
    public Table table;
    public ArrayList<Player> players;
    public Dealer dealer;
    public Shoe shoe;
    public Scanner console;

    public String action;
    public int temp_bet;
    public boolean bet_placed;

    // All the private stuff is GUI implementation, a series of panels, labels, and buttons
    private JFrame frame;
    private JPanel main_panel; // this panel holds all the other panels in a nx1 grid

    private JLabel instructions_label; // this will tell the user what the program is expecting

    private JLabel dealer_label; // this label goes directly into the main panel
    private JPanel dealer_panel;
    private JLabel dealer_value_label; // this label goes directly into the main panel


    private JPanel player_panel;
    private JLabel player_label;

    private JPanel hand_panel;

    private JLabel value_label; // this label goes directly into the main panel

    private JPanel button_panel;
    private JButton stand_button;
    private JButton hit_button;
    private JButton split_button;
    private JButton double_button;

    private JPanel money_panel;
    private JLabel bet_label;
    private JLabel bankroll_label;
    
    private JPanel bet_panel;
    private JLabel one_label;
    private JButton add_one;
    private JButton sub_one;
    private JLabel five_label;
    private JButton add_five;
    private JButton sub_five;
    private JLabel twenty_label;
    private JButton add_twenty;
    private JButton sub_twenty;
    private JLabel hundred_label;
    private JButton add_hundred;
    private JButton sub_hundred;
    
    private JPanel place_bet_panel;
    private JButton place_bet;
    
    public GUI(Table t) {
        table = t;
        players = t.players;
        dealer = t.dealer;
        shoe = t.shoe;
        console = t.console;
        action = "";
        temp_bet = 0;
        bet_placed = false;

        // panel to hold all the panels
        main_panel = new JPanel(new GridLayout(0, 1));
        main_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        
        // instructions label
        instructions_label = new JLabel("Welcome to Blackjack!", SwingConstants.CENTER);


        // dealer label + panel
        dealer_label = new JLabel("Dealer", SwingConstants.CENTER);
        dealer_panel = new JPanel(new GridLayout(1, 0));
        dealer_value_label = new JLabel("   Value: 0");


        // panel to hold the player title
        player_panel = new JPanel(new GridLayout(1, 0));
        player_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        player_label = new JLabel("", SwingConstants.CENTER);
        
        player_panel.add(player_label);


        // panel to hold the cards in the hand
        hand_panel = new JPanel(new GridLayout(1, 0));
        hand_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));


        // label to hold the value of the cards
        value_label = new JLabel("   Value: 0", SwingConstants.LEFT);


        // panel to hold the action buttons
        button_panel = new JPanel(new GridLayout(1, 0));
        button_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // initialize action buttons
        stand_button = new JButton("Stand");
        hit_button = new JButton("Hit");
        split_button = new JButton("Split");
        double_button = new JButton("Double");

        // add ActionListeners for each button
        stand_button.addActionListener(this);
        hit_button.addActionListener(this);
        split_button.addActionListener(this);
        double_button.addActionListener(this);

        // add buttons to button_panel
        button_panel.add(stand_button);
        button_panel.add(hit_button);
        button_panel.add(split_button);
        button_panel.add(double_button);
        
        
        // money panel
        money_panel = new JPanel(new GridLayout(1, 0));
        money_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        bankroll_label = new JLabel("Bankroll: " + players.get(0).bankroll, SwingConstants.CENTER);
        bet_label = new JLabel("Bet: $" + temp_bet, SwingConstants.CENTER);
        
        money_panel.add(bet_label);
        money_panel.add(bankroll_label);


        // bet panel
        bet_panel = new JPanel(new GridLayout(3, 4));
        bet_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        add_one = new JButton("^");
        add_five = new JButton("^");
        add_twenty = new JButton("^");
        add_hundred = new JButton("^");
        sub_one = new JButton("v");
        sub_five = new JButton("v");
        sub_twenty = new JButton("v");
        sub_hundred = new JButton("v");
        
        add_one.addActionListener(this);
        add_five.addActionListener(this);
        add_twenty.addActionListener(this);
        add_hundred.addActionListener(this);
        sub_one.addActionListener(this);
        sub_five.addActionListener(this);
        sub_twenty.addActionListener(this);
        sub_hundred.addActionListener(this);
        
        one_label = new JLabel("1", SwingConstants.CENTER);
        five_label = new JLabel("5", SwingConstants.CENTER);
        twenty_label = new JLabel("20", SwingConstants.CENTER);
        hundred_label = new JLabel("100", SwingConstants.CENTER);
        
        bet_panel.add(add_one);
        bet_panel.add(add_five);
        bet_panel.add(add_twenty);
        bet_panel.add(add_hundred);
        bet_panel.add(one_label);
        bet_panel.add(five_label);
        bet_panel.add(twenty_label);
        bet_panel.add(hundred_label);
        bet_panel.add(sub_one);
        bet_panel.add(sub_five);
        bet_panel.add(sub_twenty);
        bet_panel.add(sub_hundred);
        

        // place bet panel
        place_bet_panel = new JPanel(new GridLayout(1, 0));
        place_bet_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        place_bet = new JButton("Place bet");
        place_bet.addActionListener(this);
        
        place_bet_panel.add(place_bet);


        // add panels to main panel
        main_panel.add(instructions_label);
        main_panel.add(dealer_label);
        main_panel.add(dealer_panel);
        main_panel.add(dealer_value_label);
        main_panel.add(player_panel);
        main_panel.add(hand_panel);
        main_panel.add(value_label);
        main_panel.add(button_panel);
        main_panel.add(money_panel);
        main_panel.add(bet_panel);
        main_panel.add(place_bet_panel);


        // initialize the frame
        frame = new JFrame();
        frame.add(main_panel, BorderLayout.CENTER);
        // operation must be JFrame.EXIT_ON_CLOSE, DISPOSE_ON_CLOSE, HIDE_ON_CLOSE, or DO_NOTHING_ON_CLOSE
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // makes the frame resizable or not
        frame.setResizable(true);
        // set the title for the frame
        frame.setTitle("Blackjack");
        frame.pack();
        // makes frame visible
        frame.setVisible(true);
    }

    // This method is necessary to update the card labels
    private void update_frame() {
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }

    private void update_instruction_label(String instruction) {
        instructions_label.setText(instruction);
    }

    private void update_dealer_panel(Dealer dealer, boolean hide) {
        dealer_panel.removeAll();
        if (hide) {
            dealer_panel.add(new JLabel(dealer.hand.cards.get(0).toString(), SwingConstants.CENTER));
            dealer_panel.add(new JLabel("???", SwingConstants.CENTER));
            dealer_value_label.setText("   Value: " + dealer.hand.cards.get(0).rank);
        } else {
            for (Card c : dealer.hand.cards) {
                dealer_panel.add(new JLabel(c.toString(), SwingConstants.CENTER));
            }
            dealer_value_label.setText("   Value: " + dealer.hand.value);
        }
        update_frame();
    }

    private void update_player_panel(Player player) {
        player_label.setText("Player " + player.playerID);
    }

    private void update_hand_panel(Hand hand) {
        hand_panel.removeAll();
        for (Card c : hand.cards) {
            hand_panel.add(new JLabel(c.toString(), SwingConstants.CENTER));
        }
        value_label.setText("   Value: " + hand.value);
        update_frame();
    }

    private void update_money_panel(Player player, Hand hand) {
        bankroll_label.setText("Bankroll: $" + player.bankroll);
        bet_label.setText("Bet: $" + hand.bet);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stand_button) {
            action = "stand";
        } else if (e.getSource() == hit_button) {
            action = "hit";
        } else if (e.getSource() == split_button) {
            action = "split";
        } else if (e.getSource() == double_button) {
            action = "double";
        }
        if (e.getSource() == add_one) {
            temp_bet += 1;
        } else if (e.getSource() == sub_one) {
            temp_bet -= 1;
        } else if (e.getSource() == add_five) {
            temp_bet += 5;
        } else if (e.getSource() == sub_five) {
            temp_bet -= 5;
        } else if (e.getSource() == add_twenty) {
            temp_bet += 20;
        } else if (e.getSource() == sub_twenty) {
            temp_bet -= 20;
        } else if (e.getSource() == add_hundred) {
            temp_bet += 100;
        } else if (e.getSource() == sub_hundred) {
            temp_bet -= 100;
        } else if (e.getSource() == place_bet) {
            bet_placed = true;
        }
        // send the info across the thread to play()
        synchronized(this) {
            this.notifyAll();
        }
    }

    // runs a hand in the GUI
    /*
     * THE play() METHOD IN Table RUNS THE GAME IN THE CONSOLE.
     * THE play() METHOD IN GUI RUNS THE GAME IN THE GUI.
     */
    public void play() {
        if (shoe.shoe.size() < 15) {
            shoe = new Shoe(shoe.decks);
            shoe.shuffle();
            table.shoe = shoe;
        }
        System.out.println("---------------------------------------------");
        // place bets
        update_instruction_label("Please place your bet");
        for (Player player : players) {
            update_player_panel(player);
            System.out.println("*** Player " + player.playerID + " ***");
            System.out.println("Your bankroll is $" + player.bankroll);
            for (Hand hand : player.hands) {
                temp_bet = 0;
                bet_placed = false;
                System.out.print("How much would you like to bet?");
                while (!bet_placed) {
                    hand.bet = temp_bet; // included up here and after the loop so that the money panel updates properly
                    update_money_panel(player, hand);
                    synchronized(this) {
                        try {
                            // wait for a button to be pressed to update "temp_bet" and "bet_placed"
                            this.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (temp_bet > player.bankroll) {
                        bet_placed = false;
                        update_instruction_label("You cannot bet more than your bankroll!");
                    }
                    if (temp_bet <= 0) {
                        bet_placed = false;
                        update_instruction_label("Your bet must be positive");
                    }
                }
                update_money_panel(player, hand);
                hand.bet = temp_bet;
                temp_bet = 0;
                System.out.println();
            }
        }

        // deal the cards
        table.deal();
        update_dealer_panel(dealer, true);

        System.out.println("The dealer's upcard is " + dealer.hand.cards.get(0));
        // check for dealer blackjack. if they do everyone must stand
        if (dealer.hand.has_blackjack()) {
            System.out.println("Dealer had blackjack!");
            update_instruction_label("Dealer had blackjack!");
            update_dealer_panel(dealer, false);
            System.out.println(dealer);
            for (Player player : players) {
                update_player_panel(player);
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            table.reset();
            update_dealer_panel(new Dealer(), false);
            update_hand_panel(players.get(0).hands.get(0));
            return;
        }

        update_instruction_label("Please take an action");
        // players play in order
        // player blackjacks are accounted for in the update() method of Hand
        for (Player player : players) {
            update_player_panel(player);
            System.out.println("*** Player " + player.playerID + " ***");
            ArrayList<Hand> hands = player.hands;
            int size = hands.size();
            boolean split = false; // prevent printing the old hand after splitting
            for (int i = 0; i < size; i++) {
                Hand hand = hands.get(i);
                update_money_panel(player, hand);
                while (!hand.done) {
                    update_hand_panel(hand);
                    split = false;
                    System.out.println("Your hand is " + hand + ". What would you like to do? (hit/stand/double/split)");
                    action = "";
                    // synchronizes all the variables across threads (i think)
                    synchronized(this) {
                        try {
                            // wait for a button to be pressed to update "action"
                            this.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
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
                update_hand_panel(hand);
                update_money_panel(player, hand);
            }
        }

        update_instruction_label("Dealer is playing");
        // dealer plays
        System.out.println("*** Dealer ***");
        while (!dealer.hand.done) {
            update_dealer_panel(dealer, false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            if (dealer.hand.value < 17) {
                System.out.println("Dealer hits");
                dealer.hand.hit(shoe);
            } else if (dealer.hand.value == 17 && dealer.hand.soft) {
                dealer.hand.hit(shoe);
                System.out.println("Dealer hits");
            } else {
                dealer.hand.stand();
                System.out.println("Dealer stands");
            }
            System.out.println("Dealer's hand is " + dealer.hand + ".");
            if (dealer.hand.bust) {
                System.out.println("Dealer busted!");
            }
        }
        update_dealer_panel(dealer, false);
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
                update_money_panel(player, hand);
            }
            System.out.println("Player " + player.playerID + "'s bankroll is now " + player.bankroll + "\n");
        }

        update_instruction_label("Paying out...");
        // reset table
        table.reset();
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
        }
        update_dealer_panel(new Dealer(), false);
        update_hand_panel(new Hand());
    }

    public void quit() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
