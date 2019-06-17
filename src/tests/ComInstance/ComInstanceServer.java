package tests.ComInstance;

import gameModules.*;
import input.Input;
import output.Display;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ComInstanceServer {
    public static void main(String[] args) {
        GameInstance.newGame();
        try {
            CommunicationInstance comInstance = new CommunicationInstance(12345);
            comInstance.start();
            Display.update();
            Input input = new Input();
            //noinspection InfiniteLoopStatement
            for (; ; ) {
                try {
                    input.command();
                } catch (Exception e) {
                    Display.displayMessage(e.getMessage());
                }
            }
        } catch (Exception e) {
            Display.displayMessage(e.getMessage());
        }
    }
}
