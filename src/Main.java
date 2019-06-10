import gameModules.*;
import input.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Main {
    public static void main(String[] args) {
        GameInstance.newGame();
        //noinspection InfiniteLoopStatement
        for (; ; ) {
            try {
                System.out.printf("%n%n%n");
                DisplayInstance.displayGame();
                Input.gameCommands();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}