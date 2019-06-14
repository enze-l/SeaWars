package tests.ComInstance;

import gameModules.CommunicationInstance;
import gameModules.DisplayInstance;
import gameModules.GameInstance;
import input.Input;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ComInstanceServer {
    public static void main(String[] args) {
        GameInstance.newGame();
        try {
            CommunicationInstance comInstance = new CommunicationInstance(12345);
            comInstance.start();
            DisplayInstance d=new DisplayInstance();
            d.start();
            Input input=new Input();
            //noinspection InfiniteLoopStatement
            for (; ; ) {
                try {
                    input.command();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
