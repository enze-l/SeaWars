package gameModules;

import boards.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class gameInstance {
    private static PlayerBoard PLAYER_BOARD = new PlayerBoardImpl();
    private static EnemyBoard ENEMY_BOARD = new EnemyBoardImpl();

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




}
