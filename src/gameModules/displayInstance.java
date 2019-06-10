package gameModules;

import exceptions.*;
import output.Output;

import java.awt.*;
import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class displayInstance extends Thread {

    public static void displayGame() {
        try {
            Output.output(gameInstance.getPlayerBoard(), gameInstance.getEnemyBoard());
        } catch (StatusException | InputException | IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public static void changeOccurred(){
        displayGame();
    }
}
