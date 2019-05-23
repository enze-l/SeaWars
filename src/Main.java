import game.*;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Main {
    public static void main(String[] args) {
        MyBoard myBoard = new MyBoardImpl();
        EnemyBoard enemyBoard = new EnemyBoardImpl();
        while (true) {
            try {
                System.out.printf("%n%n%n");
                Output.output(myBoard, enemyBoard);
                Input.input(myBoard);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}