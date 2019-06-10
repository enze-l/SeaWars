package gameModules;

import exceptions.*;
import output.Output;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class DisplayInstance extends Thread {

    public static void displayGame() {
        try {
            Output.output(GameInstance.getPlayerBoard(), GameInstance.getEnemyBoard());
        } catch (StatusException | InputException | IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public static void changeOccurred(){
        displayGame();
    }
}
