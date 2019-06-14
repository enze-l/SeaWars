package boards;

import boards.coordinates.*;
import boards.fields.*;
import exceptions.*;
import boards.ships.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Board {
    /**
     * Method to receive the discovered status of all fields in an matrix
     * @return Status of each fields in an matrix
     */
    FieldStatus[][] getFields();

    /**
     * Method for receiving the discovered Status of the ships
     * @return ships with the discovered status
     */
    Ship[] getShips();

    /**
     * Method for getting the status of on particular field
     * @param coordinate Of the field in question
     * @return Status of the field
     * @throws FieldException if there is no field with the given coordinates
     */
    FieldStatus getFieldStatus(Coordinate coordinate)throws FieldException;

    /**
     * Method for setting a game-status for the board
     * @param status The status the board should take on
     * @throws StatusException if it is attempted to set a null status
     */
    void setStatus(GameStatus status) throws StatusException;

    /**
     * Method for receiving the game-status of the board
     * @return Game-status of the board
     * @throws StatusException If an null status is attempted to be set
     */
    GameStatus getStatus() throws StatusException;
}
