package gameInstance;

import boards.*;
import exceptions.*;
import output.Output;

import java.awt.*;
import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class gameInstance extends Thread {
    private static PlayerBoard PLAYER_BOARD = new PlayerBoardImpl();
    private static EnemyBoard ENEMY_BOARD = new EnemyBoardImpl();
    private static boolean BOARD_IS_OUTDATED = false;
    private static int REFRESH_RATE = 0;

    public gameInstance() {
        PLAYER_BOARD = new PlayerBoardImpl();
        ENEMY_BOARD = new EnemyBoardImpl();
        BOARD_IS_OUTDATED = true;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        for (GraphicsDevice device : devices) {
            int deviceRefreshRate = device.getDisplayMode().getRefreshRate();
            if (deviceRefreshRate > REFRESH_RATE) REFRESH_RATE = deviceRefreshRate;
        }
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            while (BOARD_IS_OUTDATED) {
                displayGame();
                BOARD_IS_OUTDATED=false;
            }
            try {
                Thread.sleep(1000 / REFRESH_RATE);
            }catch (InterruptedException ignored){}
        }
    }

    public static void newGame() {
        PLAYER_BOARD = new PlayerBoardImpl();
        ENEMY_BOARD = new EnemyBoardImpl();
    }

    public static EnemyBoard getEnemyBoard() {
        return ENEMY_BOARD;
    }

    public static PlayerBoard getPlayerBoard() {
        return PLAYER_BOARD;
    }

    public static synchronized void changeOccurred(){
        BOARD_IS_OUTDATED=true;
        try {
            while (BOARD_IS_OUTDATED) {
                Thread.sleep(1000 / REFRESH_RATE);
            }
        }catch (InterruptedException ignored){}
    }

    public static void displayGame() {
        try {
            Output.output(PLAYER_BOARD, ENEMY_BOARD);
        } catch (StatusException | InputException | IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
