import game.*;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Main {
    public static void main(String[] args) {
        MyBoard board=new Board();
        while (true) {
            try {
                Output.output(board);
                Input.input(board);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
