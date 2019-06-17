import gameModules.*;
import input.*;
import output.Display;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Main {
    public static void main(String[] args) {
        GameInstance.newGame();
        Input input=new Input();
        //noinspection InfiniteLoopStatement
        for (; ; ) {
            try {
                input.command();
            } catch (Exception e) {
                Display.displayMessage(e.getMessage());
            }
        }
    }
}