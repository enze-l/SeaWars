package output;

import exceptions.InputException;
import exceptions.StatusException;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Output {

    /**
     * Displays the current playing-board
     * @throws StatusException if an error occurred with the status of on of the boards
     * @throws InputException if there has been mishandling with the Input
     * @throws IOException if there has occurred an error with the streams used
     */
    void output()throws StatusException, InputException, IOException;

    /**
     * Displays the current playing-board with the given message
     * @param message the given message
     * @throws StatusException if an error occurred with the status of on of the boards
     * @throws InputException if there has been mishandling with the Input
     * @throws IOException if there has occurred an error with the streams used
     */
    void output(String message)throws StatusException, InputException, IOException;
}
