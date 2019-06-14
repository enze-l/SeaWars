package gameModules;

import boards.EnemyBoard;
import boards.GameStatus;
import boards.PlayerBoard;
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

    public DisplayInstance() {
        this.refreshRate = 30;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        for (GraphicsDevice device : devices) {
            int displayRefreshRate = device.getDisplayMode().getRefreshRate();
            if (displayRefreshRate > this.refreshRate) this.refreshRate = displayRefreshRate;
        }
        this.running = false;
    }

    public static void displayGame() {
        try {
            System.out.printf("%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n%n");
            Output.output(GameInstance.getPlayerBoard(), GameInstance.getEnemyBoard());
        } catch (StatusException | InputException | IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public synchronized void run() {
        this.running = true;
        PlayerBoard playerBoard = GameInstance.getPlayerBoard();
        EnemyBoard enemyBoard = GameInstance.getEnemyBoard();
        FieldStatus[][] enemyField = enemyBoard.getFields();
        FieldStatus[][] playerField = playerBoard.getFields();
        try {
            GameStatus enemyStatus = enemyBoard.getStatus();
            GameStatus playerStatus = playerBoard.getStatus();
            displayGame();
            while (running) {
                if (!(Arrays.deepEquals(enemyField, enemyBoard.getFields())
                        && Arrays.deepEquals(playerField, playerBoard.getFields())
                        && enemyStatus == enemyBoard.getStatus()
                        && playerStatus == playerBoard.getStatus())) {
                    displayGame();
                    enemyStatus = enemyBoard.getStatus();
                    playerStatus = playerBoard.getStatus();
                    enemyField = enemyBoard.getFields();
                    playerField = playerBoard.getFields();
                }
                try {
                    Thread.sleep(1000 / refreshRate);
                } catch (InterruptedException ignored) {
                }
            }
        } catch (StatusException ignored) {
        }
    }

    public void close() {
        this.running = false;
    }
}
