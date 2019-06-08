import boards.*;
import input.*;
import output.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Main {
    public static void main(String[] args) {
        MyBoard myBoard = new MyBoardImpl();
        EnemyBoard enemyBoard = new EnemyBoardImpl();
        for(;;) {
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