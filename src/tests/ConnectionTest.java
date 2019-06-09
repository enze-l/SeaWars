package tests;

import boards.*;
import input.Input;
import output.Output;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ConnectionTest {
    public static void main(String[] args) {
        PlayerBoard playerBoard = new PlayerBoardImpl();
        EnemyBoard enemyBoard = new EnemyBoardImpl();
        for(;;) {
            try {
                System.out.printf("%n%n%n");
                Output.output(playerBoard, enemyBoard);
                Input.gameCommands(playerBoard);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
