import gameModules.*;
import input.*;

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
                System.err.println(e.getMessage());
            }
        }
    }
}