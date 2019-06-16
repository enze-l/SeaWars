package tests.ComInstance;

import gameModules.CommunicationInstance;
import gameModules.GameInstance;
import input.Input;
import output.Notifiable;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ComInstanceServer {
    public static void main(String[] args) {
        GameInstance.newGame();
        try {
            CommunicationInstance comInstance = new CommunicationInstance(12345);
            comInstance.start();
            Notifiable.update();
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
