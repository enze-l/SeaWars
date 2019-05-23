
package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface MyBoard extends Board{

    /**
     * @return returns the Status of the Field
     * @throws StatusException when no Status has been set
     */
    GameStatus getStatus() throws StatusException;

    /**
     * Sets the status of the Field
     *
     * @throws StatusException if an null Status is attempted to be set
     */
    void setStatus(GameStatus status) throws StatusException;

    /**
     * Player positions ship on board
     *
     * @param shipType    type of ship (defines length of ship)
     * @param orientation shows in witch direction the ship is positioned
     * @param coordinate  gives the coordinates of the upper left corner of the ship on the board.
     *                    the ship swivels around this point depending on the orientation
     * @throws FieldException if it is attempted to set a ship on non-existent Field or other Ship
     * @throws ShipException  if ths ship has already been set
     */
    void setShip(ShipType shipType, Coordinate coordinate, Orientation orientation) throws FieldException, ShipException;

    /**
     * removes ship that was already placed
     *
     * @param coordinates array with x and y coordinates of field on Board
     * @throws ShipException  if no ship resides on the field of the coordinates
     * @throws FieldException if an attempt is made to remove an ship outside of the board
     */
    void removeShip(Coordinate coordinates) throws FieldException, ShipException;

    /**
     * longest (lenght 5) comes first and least long (length 2) last. At Start of game the amount of ships should be
     * ({1},{2},{3},{4}) at the end it should be ({0},{0},{0},{0})
     *
     * @return array with set length that describes the amount of ships available for each Class of ships.
     */
    int[] shipsAvailable();

    boolean allShipsSet();
    /**
     * gives back all ships saved for the board
     * @return an array of ships
     */
    Ship[] getShips();

    /**
     * @param coordinate array with the x and y coordinate of shot
     * @return Opponent Move is received and Result of shot is given back
     * @throws FieldException if Coordinate doesn't correspond to an actual field
     */
    FieldStatus receiveAttack(Coordinate coordinate) throws FieldException;

    /**
     * @param coordinate Coordinates of the field of which the status is requested
     * @return The Status of the Field
     * @throws FieldException if no Field with the given coordinates exists
     */
    FieldStatus getFieldStatus(Coordinate coordinate) throws FieldException;

    /**
     * Returns an two dimensional array containing the field-status of each field
     *
     * @return field-status for each field
     */
    FieldStatus[][] getFields();
}
