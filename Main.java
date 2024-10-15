package Blackjack;
import java.util.*;

/*
 * Main function to run the Blackjack game. See underpinned comment for
 * instructions on how to run including the doc icon on Mac.
 * 
 * Please excuse my lack of programming etiquette, I don't have much
 * group programming experience, all is self-taught
 * 
 * The game now runs on a GUI, and no longer supports console inputs.
 * The game status still prints to the console for logging purposes.
 * 
 * @author Evan Dworkin
 */

/* 
 * to run it with the dock icon, go to Blackjack directory and run:
 * javac -d . *.java                                                // this will compile all the files in a new directory "Blackjack"
 * java -Xdock:icon=Icons/blackjack_icon.png Blackjack.Main         // this will run the Main.class file in the newly created directory with the icon as the little blackjack icon
 */

public class Main {
    public static Scanner console = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println(
            "\n\nThe console output is for display purposes only. " + 
            "After the addition of the GUI, the terminal input cannot " +
            "be interacted with. Please use the GUI"
        );
        Shoe shoe = new Shoe(8);
        shoe.shuffle();
        
        Table table = new Table(shoe, console);
        Player p1 = new Player(100);
        Player p2 = new Player(200);
        table.join(p1);
        table.join(p2);

        GUI gui = new GUI(table);
        while (p1.bankroll > 0) {
            gui.play();
        }
        gui.quit();
    }
}
