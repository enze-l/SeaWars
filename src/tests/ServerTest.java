package tests;

import gameModules.CommunicationInstance;
import gameModules.DisplayInstance;
import gameModules.GameInstance;
import input.Input;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ServerTest {
    public static void main(String[] args) {
        GameInstance.newGame();
        try {
            CommunicationInstance comInstance = new CommunicationInstance(12345);
            comInstance.start();
            for (; ; ) {
                try {
                    System.out.printf("%n%n%n");
                    DisplayInstance.displayGame();
                    Input.gameCommands();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
