import gameModules.*;
import input.CommunicationInstance;
import input.InputImpl;
import gameModules.Display;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ServerPrototype {
    public static void main(String[] args) {
        Game.newGame();
        try {
            CommunicationInstance comInstance = new CommunicationInstance(12345);
            comInstance.start();
            Display.update();
            InputImpl input = new InputImpl();
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
