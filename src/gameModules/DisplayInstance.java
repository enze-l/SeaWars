package gameModules;

import boards.fields.FieldStatus;
import exceptions.*;
import output.Output;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class DisplayInstance extends Thread {
    private int refreshRate;
    private boolean running;

    public DisplayInstance(){
        this.refreshRate=30;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        for (GraphicsDevice device : devices) {
            int displayRefreshRate = device.getDisplayMode().getRefreshRate();
            if (displayRefreshRate > this.refreshRate) this.refreshRate = displayRefreshRate;
        }
        this.running=false;
    }

    public static void displayGame() {
        try {
            Output.output(GameInstance.getPlayerBoard(), GameInstance.getEnemyBoard());
        } catch (StatusException | InputException | IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public synchronized void run(){
        this.running=true;
        FieldStatus[][] enemyField = GameInstance.getEnemyBoard().getFields();
        FieldStatus[][] playerField = GameInstance.getPlayerBoard().getFields();
        displayGame();
        while(running){
            if(!(Arrays.deepEquals(enemyField, GameInstance.getEnemyBoard().getFields())
                    &&Arrays.deepEquals(playerField, GameInstance.getPlayerBoard().getFields()))){
                displayGame();
                enemyField =GameInstance.getEnemyBoard().getFields();
                playerField =GameInstance.getPlayerBoard().getFields();
            }
            try {
                Thread.sleep(1000 / refreshRate);
            }catch (InterruptedException ignored){}
        }
    }

    public void close(){
        this.running=false;
    }
}
