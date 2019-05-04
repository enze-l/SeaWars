
package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface MyBoard {

    /**
     * @return returns the Status of the Field
     * @throws StatusException when no Status has been set
     */
    GameStatus getStatus();

    /**
     * Sets the status of the Field
     * @throws StatusException if an null Status is attempted to be set
     */
    void setStatus(GameStatus status);

    /**
     * Player positions ship on board
     * @throws FieldException if it is attempted to set a ship on non-existent Field or other Ship
     * @throws ShipException if ths ship has already been set
     * @param shipType type of ship (defines length of ship)
     * @param orientation shows in witch direction the ship is positioned
     * @param coordinate gives the coordinates of the upper left corner of the ship on the board.
     *                    the ship swivels around this point depending on the orientation
     */
    void setShip(ShipType shipType, Orientation orientation, Coordinate coordinate) throws FieldException, ShipException;

    /**
     * removes ship that was already placed
     * @throws ShipException if no ship resides on the field of the coordinates
     * @throws FieldException if an attempt is made to remove an ship outside of the board
     * @param coordinates array with x and y coordinates of field on Board
     */
    void removeShip(Coordinate coordinates);

    /**
     * longest (lenght 5) comes first and least long (length 2) last. At Start of game the amount of ships should be
     * ({1},{2},{3},{4}) at the end it should be ({0},{0},{0},{0})
     * @return array with set length that describes the amount of ships available for each Class of ships.
     */
    int[] shipsAvailable();

    /**
     * @return array with x and y coordinates
     */
    //not really sure how to test this method because its only job is to request an user input that isn't defined jet
    //and in my opinion, should be handled via the communication class
    Coordinate sendAttack();

    /**
     * @throws FieldException if Coordinate doesn't correspond to an actual field
     * @return Opponent Move is recieved and Result of shot is given back
     * @param coordinate array with the x and y coordinate of shot
     */
    FieldStatus receiveAttack(Coordinate coordinate);

    /**
     * @throws FieldException if no Field with the given coordinates exists
     * @param coordinate Coordinates of the field of which the status is requested
     * @return The Status of the Field
     */
    FieldStatus getFieldStatus(Coordinate coordinate) throws FieldException;

    /**
     * displays the Board
     */
    //not shure how to test it. could be done by using this Method in virtually all test methods
    void displayBoard();
}
