import gameModules.*;
import input.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Main {
    public static void main(String[] args) {
        GameInstance.newGame();
        Input input=new Input();
        DisplayInstance display=new DisplayInstance();
        display.start();
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