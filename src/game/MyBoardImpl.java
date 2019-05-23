package game;

import java.util.Arrays;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class MyBoardImpl implements MyBoard {
    private Field[][] board;
    private Ship[] ships;
    private GameStatus gameStatus;

    /**
     * A board has the size 10*10 an gets equally as many fields with the corresponding coordinates.
     * If a board is created, its gameStatus is automatically set to preparation because it still needs to get ships
     * placed on it.
     * The ships that can be placed on it are automatically created and placed in an array. They don't have any
     * coordinates assigned to them.
     */
    public MyBoardImpl() {
        this.board = new FieldImpl[10][10];
        for (int fieldsHorizontal = 1; fieldsHorizontal <= 10; fieldsHorizontal++) {
            for (int fieldsVertical = 1; fieldsVertical <= 10; fieldsVertical++) {
                this.board[fieldsHorizontal - 1][fieldsVertical - 1] = new FieldImpl(new CoordinateImpl(fieldsHorizontal, fieldsVertical));
            }
        }

        this.gameStatus = GameStatus.PREPARATION;

        this.ships = new Ship[]{
                new Battleship(),
                new Cruiser(), new Cruiser(),
                new Submarine(), new Submarine(), new Submarine(),
                new Destroyer(), new Destroyer(), new Destroyer(), new Destroyer()
        };
    }

    /**
     * getter method for the phase of the game in which the board is currently within the game
     *
     * @return gameStatus of the board
     */
    @Override
    public GameStatus getStatus() throws StatusException {
        if (this.gameStatus == null) {
            throw new StatusException("No Status has been set jet!");
        }
        return this.gameStatus;
    }

    /**
     * Method for setting the boards status in the game phase
     *
     * @param status that should be set
     * @throws StatusException if a "null"-status is attempted to be set
     */
    @Override
    public void setStatus(GameStatus status) throws StatusException {
        if (status == null) throw new StatusException();
        this.gameStatus = status;
    }

    /**
     * Returns an array with a field for each type of ship an the corresponding number of ships of this type, that are
     * still available
     *
     * @return number of ships of each type, that are available
     */
    @Override
    public int[] shipsAvailable() {
        int[] available = new int[]{0, 0, 0, 0};

        if (ships[0].getPosition() == null) available[0]++;

        for (int cruiserPosition = 1; cruiserPosition <= 2; cruiserPosition++) {
            if (ships[cruiserPosition].getPosition() == null) available[1]++;
        }

        for (int submarinePosition = 3; submarinePosition <= 5; submarinePosition++) {
            if (ships[submarinePosition].getPosition() == null) available[2]++;
        }

        for (int destroyerPosition = 6; destroyerPosition <= 9; destroyerPosition++) {
            if (ships[destroyerPosition].getPosition() == null) available[3]++;
        }

        return available;
    }

    @Override
    public boolean allShipsSet(){
        return Arrays.equals(shipsAvailable(), new int[]{0,0,0,0});
    }

    /**
     * method for setting a ship on the board
     *
     * @param shipType    type of ship (defines length of ship)
     * @param coordinate  gives the coordinates of the upper left corner of the ship on the board.
     * @param orientation shows in witch direction the ship is positioned
     * @throws FieldException in case the coordinate is not on the field
     * @throws ShipException  if there is no ship of this type available any more or the ship is attempted to be
     *                        positioned onto or directly next to another ship.
     */
    @Override
    public void setShip(ShipType shipType, Coordinate coordinate, Orientation orientation) throws FieldException, ShipException {
        if (shipsAvailable()[shipType.ordinal()] == 0)
            throw new ShipException("Es wurden bereits alle Schiffe dieser Klasse platziert!");
        if (shipPlacementPossible(shipType, orientation, coordinate)) {
            int chosenShip = availableShipFromType(shipType);
            ships[chosenShip].setShip(coordinate, orientation);
            for (int shipSegment = 0; shipSegment < ShipInfo.getLength(shipType); shipSegment++) {
                switch (orientation) {
                    case HORIZONTAL:
                        board[coordinate.getXCoordinate() + shipSegment][coordinate.getYCoordinate()].setShip(ships[chosenShip]);
                        break;
                    case VERTICAL:
                        board[coordinate.getXCoordinate()][coordinate.getYCoordinate() + shipSegment].setShip(ships[chosenShip]);
                        break;
                }
            }
        }

    }

    /**
     * Method for removing a ship from a field with the given coordinate
     *
     * @param coordinate coordinate where part of the ship resides
     * @throws FieldException if the coordinate given does not exist on the board
     * @throws ShipException  if there is no ship at the given coordinate
     */
    @Override
    public void removeShip(Coordinate coordinate) throws FieldException, ShipException {
        if (!coordinate.validCoordinate()) {
            throw new FieldException("Es gibt kein Spielfeld mit diesen Koordinaten");
        }
        if (board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getFieldStatus() == FieldStatus.WATER) {
            throw new ShipException("An dieser Stelle liegt kein Schiff!");
        }
        Coordinate shipAnchor = board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getShip().getPosition();
        int shipLength = board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getShip().getLength();
        Orientation orientation;
        if (board[shipAnchor.getXCoordinate() + 1][shipAnchor.getYCoordinate()].getFieldStatus() == FieldStatus.SETSHIP) {
            orientation = Orientation.HORIZONTAL;
        } else {
            orientation = Orientation.VERTICAL;
        }
        board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getShip().removeShip();
        for (int shipSegment = 0; shipSegment < shipLength; shipSegment++) {
            if (orientation == Orientation.HORIZONTAL) {
                board[shipAnchor.getXCoordinate() + shipSegment][shipAnchor.getYCoordinate()].removeShip();
            }
            if (orientation == Orientation.VERTICAL) {
                board[shipAnchor.getXCoordinate()][shipAnchor.getYCoordinate()+ shipSegment].removeShip();
            }
        }
    }

    /**
     * Methode for receiving a hit. Gives back the result
     *
     * @param coordinate array with the x and y coordinate of shot
     * @return result of ship (hitwater, hit, sunk)
     */
    @Override
    public FieldStatus receiveAttack(Coordinate coordinate) throws FieldException {
        if (!coordinate.validCoordinate()) throw new FieldException("Es gibt kein Feld mit diesen Koordinaten!");
        board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].receiveHit();
        return board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getFieldStatus();
    }

    /**
     * Returns status of the field with the given coordinates
     *
     * @param coordinate Coordinates of the field of which the status is requested
     * @return status of the field
     * @throws FieldException if there is no field with the given coordinates
     */
    @Override
    public FieldStatus getFieldStatus(Coordinate coordinate) throws FieldException {
        if (coordinate.getXCoordinate() < 0
                || coordinate.getXCoordinate() > 9
                || coordinate.getYCoordinate() < 0
                || coordinate.getYCoordinate() > 9) {
            throw new FieldException("Feld ausserhalb des Spielfeldes");
        }
        return this.board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getFieldStatus();
    }

    /**
     * Returns an two dimensional array containing the field-status of each field
     *
     * @return field-status for each field
     */
    @Override
    public FieldStatus[][] getFields() {
        FieldStatus[][] fields = new FieldStatus[this.board.length][this.board[0].length];
        for (int column = 0; column < this.board.length; column++) {
            for (int row = 0; row < this.board[0].length; row++) {
                fields[column][row] = this.board[column][row].getFieldStatus();
            }
        }
        return fields;
    }

    @Override
    public Ship[] getShips(){
        return this.ships;
    }

    /**
     * checks if a given type of ship could be placed on the board.
     * it does that by checking if the given coordinates are on the board,
     * a ship is already set on one ore more fields in the place of the new ship
     * and if the ship would touch another ship.
     *
     * @param shipType    defines length of the ship
     * @param orientation the direction in which the ship is pointing
     * @param coordinate  the anchor-point of the ship
     * @return if the placement of the ship is possible
     * @throws FieldException if one or more fields on which the ship should be placed are outside of the board
     * @throws ShipException  if the ship would be on top of another ship or touch another one
     */
    private boolean shipPlacementPossible(ShipType shipType, Orientation orientation, Coordinate coordinate) throws ShipException, FieldException {
        boolean isOnBoard = onBoard(shipType, orientation, coordinate);
        if (!isOnBoard) throw new FieldException("Das Feld liegt außerhalb des Spielbereichs!");
        boolean noHere = noShipHere(shipType, orientation, coordinate);
        if (!noHere) throw new ShipException("An dieser Position liegt bereits ein Schiff!");
        boolean noNearby = noShipNearby(shipType, orientation, coordinate);
        if (!noNearby) throw new ShipException("Diese Position ist zu nah an einem anderen Schiff!");
        return true;
    }

    /**
     * Checks if there would be another ship in the way of a new one
     *
     * @param shipType    defines length of the ship that is attempted to be placed
     * @param orientation the direction the ship is pointing
     * @param coordinate  the anchor-point of the ship
     * @return if there is a ship
     */
    private boolean noShipHere(ShipType shipType, Orientation orientation, Coordinate coordinate) {
        for (int shipSegment = 0; shipSegment < ShipInfo.getLength(shipType); shipSegment++) {
            int x = 0, y = 0;
            switch (orientation) {
                case HORIZONTAL:
                    x = coordinate.getXCoordinate() + shipSegment;
                    y = coordinate.getYCoordinate();
                    break;
                case VERTICAL:
                    x = coordinate.getXCoordinate();
                    y = coordinate.getYCoordinate() + shipSegment;
                    break;
            }
            try {
                if (getFieldStatus(new CoordinateImpl(x + 1, y + 1)) == FieldStatus.SETSHIP) {
                    return false;
                }
            } catch (FieldException e) {
            }
        }
        return true;
    }

    /**
     * Method for detecting if one ship would be to close to another one if placed.
     *
     * @param shipType    needed for detecting the length of the ship
     * @param orientation needed for knowing in which direction the surrounding fields are spread
     * @param coordinate  anchor-point around which the ship is rotated
     * @return if ship is to close to another
     */
    private boolean noShipNearby(ShipType shipType, Orientation orientation, Coordinate coordinate) {
        for (int shipLengthOutline = -1; shipLengthOutline < ShipInfo.getLength(shipType) + 1; shipLengthOutline++) {
            int x = 0, y = 0, xOffset = 0, yOffset = 0;
            switch (orientation) {
                case HORIZONTAL:
                    x = coordinate.getXCoordinate() + shipLengthOutline;
                    y = coordinate.getYCoordinate();
                    yOffset = 1;
                    break;
                case VERTICAL:
                    x = coordinate.getXCoordinate();
                    y = coordinate.getYCoordinate() + shipLengthOutline;
                    xOffset = 1;
                    break;
            }
            try {
                if (//compares the field before the ship with bad-case SHIP
                        shipLengthOutline == -1 && board[x][y].getFieldStatus() == FieldStatus.SETSHIP)
                    return false;
                if (//compares the field after the ship with bad-case SHIP
                        shipLengthOutline == ShipInfo.getLength(shipType)
                                && board[x][y].getFieldStatus() == FieldStatus.SETSHIP)
                    return false;
                if (//compares the fields sideways of the ship with bad-case SHIP
                        shipLengthOutline >= 0 && shipLengthOutline < ShipInfo.getLength(shipType)
                                && (board[x + xOffset][y + yOffset].getFieldStatus() == FieldStatus.SETSHIP
                                || board[x - xOffset][y - yOffset].getFieldStatus() == FieldStatus.SETSHIP)) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        return true;
    }

    /**
     * checks if given coordinate is possible on the board
     *
     * @param coordinate the place that should be checked
     * @return if the coordinate is possible
     */
    private boolean onBoard(ShipType shipType, Orientation orientation, Coordinate coordinate) {
        for (int shipSegment = 0; shipSegment < ShipInfo.getLength(shipType); shipSegment++) {
            int x = 0, y = 0;
            switch (orientation) {
                case HORIZONTAL:
                    x = coordinate.getXCoordinate() + shipSegment;
                    y = coordinate.getYCoordinate();
                    break;
                case VERTICAL:
                    x = coordinate.getXCoordinate();
                    y = coordinate.getYCoordinate() + shipSegment;
                    break;
            }
            if (y < 0 || y > 9 || x < 0 || x > 9) {
                return false;
            }
        }
        return true;
    }

    /**
     * goes through the list of ships and evaluates if one of the chosen class is available. if it is the case, it
     * returns the position of the ship within the ship-array
     *
     * @param shipType class of ship that is searched for
     * @return position of ship in ship-array
     */
    private int availableShipFromType(ShipType shipType) throws ShipException {
        int shipCounter;
        for (shipCounter = 0; shipCounter < ships.length; shipCounter++) {
            if (ShipInfo.getLength(shipType) == ships[shipCounter].getLength()
                    && ships[shipCounter].getPosition() == null) {
                return shipCounter;
            }
        }
        throw new ShipException("Es wurden bereits alle Schiffe dieses Typs gesetzt!");
    }
}