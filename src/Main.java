import boards.*;
import input.*;
import output.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Main {
    public static void main(String[] args) {
        gameInstance.newGame();
        //noinspection InfiniteLoopStatement
        for (; ; ) {
            try {
                System.out.printf("%n%n%n");
                gameInstance.displayGame();
                Input.gameCommands(playerBoard);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}