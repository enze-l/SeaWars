package input;

import exceptions.*;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Input {
    /**
     * takes command and processes it
     * @throws IOException if there is a problem with the input/OutputImpl-Stream
     * @throws StatusException if a command cannot be set because the player isn't in a position to do so
     * @throws InputException if an command isn't legit
     * @throws FieldException if the given coordinates for a field are outside the board
     * @throws ShipException if the place where a ship should be placed is already occupied
     * @throws DisplayException if an error occurred with the output
     */
    void command()throws IOException, StatusException, InputException, FieldException, ShipException, DisplayException;
}
