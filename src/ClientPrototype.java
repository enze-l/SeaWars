import gameModules.*;
import input.CommunicationInstance;
import input.InputImpl;
import gameModules.Display;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ClientPrototype {
    public static void main(String[] args) {
        Game.newGame();
        try {
            String serverIP="127.0.0.1";
            CommunicationInstance comInstance = new CommunicationInstance( serverIP,12345);
            comInstance.start();
            Display.update();
            InputImpl i=new InputImpl();
            //noinspection InfiniteLoopStatement
            for (; ; ) {
                try {
                    i.command();
                } catch (Exception e) {
                    Display.displayMessage(e.getMessage());
                }
            }
        }catch (Exception e){
            Display.displayMessage(e.getMessage());
        }
    }
}
