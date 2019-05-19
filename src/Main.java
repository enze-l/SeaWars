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
            } catch (Exception e) { }
            Input.input(board);
        }
    }
}
