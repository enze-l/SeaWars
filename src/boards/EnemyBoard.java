package boards;

import boards.coordinates.*;
import boards.fields.*;
import boards.ships.*;
import exceptions.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface EnemyBoard extends Board {
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
     * Method for receiving an attack
     * @param coordinate The coordinates of the field that is getting attacked
     * @throws FieldException If there is no field with the given coordinates
     */
    void setFieldStatus(Coordinate coordinate, FieldStatus fieldStatus) throws FieldException,DisplayException;

    /**
     * Method for setting a game-status for the board
     * @param status The status the board should take on
     * @throws StatusException if it is attempted to set a null status
     */
    void setGameStatus(GameStatus status) throws StatusException, DisplayException;

    /**
     * Method for receiving the game-status of the board
     * @return Game-status of the board
     * @throws StatusException If an null status is attempted to be set
     */
    GameStatus getGameStatus() throws StatusException;
}
