import boards.*;
import boards.PlayerBoardImpl;
import exceptions.InputException;
import exceptions.StatusException;
import output.Output;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class gameInstance {
    private static PlayerBoard playerBoard=new PlayerBoardImpl();
    private static EnemyBoard enemyBoard=new EnemyBoardImpl();

    public static void newGame(){
        playerBoard=new PlayerBoardImpl();
        enemyBoard=new EnemyBoardImpl();
    }

    public static EnemyBoard getEnemyBoard(){
        return enemyBoard;
    }

    public static PlayerBoard getPlayerBoard(){
        return playerBoard;
    }

    public static void displayGame(){
        try {
            Output.output(playerBoard, enemyBoard);
        }catch (StatusException| InputException| IOException e){
            System.err.println(e.getLocalizedMessage());
        }
    }
}
