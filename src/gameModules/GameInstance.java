package gameModules;

import boards.*;
import output.OutputImpl;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class GameInstance {
    private static PlayerBoard PLAYER_BOARD = new PlayerBoardImpl();
    private static EnemyBoard ENEMY_BOARD = new EnemyBoardImpl();

    private GameInstance(){}

    /**
     * creates new game
     */
    public static void newGame() {
        PLAYER_BOARD = new PlayerBoardImpl();
        ENEMY_BOARD = new EnemyBoardImpl();
        OutputImpl OUTPUT = new OutputImpl(PLAYER_BOARD, ENEMY_BOARD);
        Display.initialize(OUTPUT);
    }

    public static EnemyBoard getEnemyBoard() {
        return ENEMY_BOARD;
    }

    public static PlayerBoard getPlayerBoard() {
        return PLAYER_BOARD;
    }




}
