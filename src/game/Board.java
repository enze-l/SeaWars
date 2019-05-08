package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Board implements MyBoard {
    private Field[][] board;
    private Ship[] ships;
    private GameStatus gameStatus;

    Board() {
        this.board = new FieldImpl[10][10];
        for (int fieldsHorizontal = 1; fieldsHorizontal <= 10; fieldsHorizontal++) {
            for (int fieldsVertical = 1; fieldsVertical <= 10; fieldsVertical++) {
                this.board[fieldsHorizontal-1][fieldsVertical-1] = new FieldImpl(new CoordinateImpl(fieldsHorizontal, fieldsVertical));
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

    @Override
    public GameStatus getStatus() {
        return this.gameStatus;
    }

    @Override
    public void setStatus(GameStatus status) throws StatusException {
        if (status==null) throw new StatusException();
        this.gameStatus = status;
    }

    @Override
    public int[] shipsAvailable() {
        int[] available=new int[]{0,0,0,0};

        if (ships[0].getPosition() == null) available[0]++;

        for (int cruiserPosition=1; cruiserPosition<=2; cruiserPosition++){
            if (ships[cruiserPosition].getPosition()==null) available[1]++;
        }

        for (int submarinePosition=3; submarinePosition<=5; submarinePosition++){
            if (ships[submarinePosition].getPosition()==null) available[2]++;
        }

        for (int destroyerPosition=6; destroyerPosition<=9; destroyerPosition++){
            if (ships[destroyerPosition].getPosition()==null) available[3]++;
        }

        return available;
    }

    @Override
    public void setShip(ShipType shipType, Coordinate coordinate, Orientation orientation) throws FieldException, ShipException {
        if (shipsAvailable()[shipType.ordinal()]==0)throw new ShipException("Es wurden bereits alle Schiffe dieser Klasse platziert!");
        if (shipPlacementPossible(shipType, orientation, coordinate)) {
            int chosenShip=availableShipFromType(shipType);
            ships[chosenShip].setShip(coordinate, orientation);
            for (int shipSegment = 0; shipSegment < ShipLength.getLength(shipType); shipSegment++) {
                switch(orientation){
                    case HORIZONTAL:
                        board[coordinate.getXCoordinate()+shipSegment][coordinate.getYCoordinate()].setShip(ships[chosenShip]);
                        break;
                    case VERTICAL:
                        board[coordinate.getXCoordinate()][coordinate.getYCoordinate()+shipSegment].setShip(ships[chosenShip]);
                        break;
                }
            }
        }

    }

    @Override
    public void removeShip(Coordinate coordinate) throws FieldException, ShipException{
        if (!coordinate.validCoordinate()){
            throw new FieldException("Es gibt kein Spielfeld mit diesen Koordinaten");
        }
        if(board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getStatus()==FieldStatus.WATER){
            throw new ShipException("An dieser Stelle liegt kein Schiff!");
        }
        Coordinate shipAnchor=board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getShip().getPosition();
        int shipLength=board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getShip().getLength();
        board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getShip().removeShip();
        board[shipAnchor.getXCoordinate()][shipAnchor.getYCoordinate()].removeShip();
        Orientation orientation;
        if (board[shipAnchor.getXCoordinate()+1][shipAnchor.getYCoordinate()].getStatus()==FieldStatus.SHIP){
            orientation=Orientation.HORIZONTAL;
        } else {orientation=Orientation.VERTICAL;}
        for(int shipSegment=1; shipSegment<shipLength; shipSegment++){
            if (orientation==Orientation.HORIZONTAL){
                board[shipAnchor.getXCoordinate()][shipAnchor.getYCoordinate()+shipSegment].removeShip();
            }
            if (orientation==Orientation.VERTICAL){
                board[shipAnchor.getXCoordinate()+shipSegment][shipAnchor.getYCoordinate()].removeShip();
            }
        }
    }

    @Override
    public FieldStatus receiveAttack(Coordinate coordinate) {
        board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].receiveHit();
        return board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getStatus();
    }

    @Override
    public FieldStatus getFieldStatus(Coordinate coordinate) throws FieldException {
        if (coordinate.getXCoordinate()<1
                ||coordinate.getXCoordinate()>10
                ||coordinate.getYCoordinate()<1
                ||coordinate.getYCoordinate()>10){
            throw new FieldException("Feld ausserhalb des Spielfeldes");
        }
        return this.board[coordinate.getXCoordinate()][coordinate.getYCoordinate()].getStatus();
    }

    /**
     * checks if a given type of ship could be placed on the board.
     * it does that by checking if the given coordinates are on the board,
     * a ship is already set on one ore more fields in the place of the new ship
     * and if the ship would touch another ship.
     * @param shipType defines length of the ship
     * @param orientation the direction in which the ship is pointing
     * @param coordinate the anchor-point of the ship
     * @return if the placement of the ship is possible
     * @throws FieldException if one or more fields on which the ship should be placed are outside of the board
     * @throws ShipException if the ship would be on top of another ship or touch another one
     */
    private boolean shipPlacementPossible(ShipType shipType, Orientation orientation, Coordinate coordinate)throws ShipException, FieldException {
        boolean isOnBoard=onBoard(shipType,orientation,coordinate);
        if(!isOnBoard)throw new FieldException("Das Feld liegt außerhalb des Spielbereichs!");
        boolean noHere=noShipHere(shipType,orientation,coordinate);
        if(!noHere)throw new ShipException("An dieser Position liegt bereits ein Schiff!");
        boolean noNearby=noShipNearby(shipType,orientation,coordinate);
        if (!noNearby)throw new ShipException("Diese Position ist zu nah an einem anderen Schiff!");
        return true;
    }

    private boolean noShipHere(ShipType shipType, Orientation orientation, Coordinate coordinate) {
        for (int shipSegment = 0; shipSegment < ShipLength.getLength(shipType); shipSegment++) {
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
                if (getFieldStatus(new CoordinateImpl(x + 1, y + 1)) != FieldStatus.WATER){
                    return false;
                }
            } catch (FieldException e) {}
        }
        return true;
    }

    /**
     * Method for detecting if one ship would be to close to another one if placed.
     * @param shipType needed for detecting the length of the ship
     * @param orientation needed for knowing in which direction the surrounding fields are spread
     * @param coordinate anchor-point around which the ship is rotated
     * @return if ship is to close to another
     */
    private boolean noShipNearby(ShipType shipType, Orientation orientation, Coordinate coordinate) {
        for (int shipLengthOutline = -1; shipLengthOutline < ShipLength.getLength(shipType) + 1; shipLengthOutline++) {
            int x = 0, y = 0, xOffset=0, yOffset=0;
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
                if (
                    //compares the field before the ship with bad-case SHIP
                        shipLengthOutline == -1 && board[x][y].getStatus() == FieldStatus.SHIP
                    //compares the field at the end of the ship with bad-case SHIP
                        || shipLengthOutline == ShipLength.getLength(shipType)
                        && board[x][y].getStatus() == FieldStatus.SHIP
                     //compares the fields sideways of the ship with bad-case SHIP
                        || shipLengthOutline >= 0 && shipLengthOutline < ShipLength.getLength(shipType)
                        && (board[x + xOffset][y + yOffset].getStatus() == FieldStatus.SHIP
                        || board[x - xOffset][y - yOffset].getStatus() == FieldStatus.SHIP)) {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException e) { }
        }
        return true;
    }

    /**
     * checks if given coordinate is possible on the board
     * @param coordinate the place that should be checked
     * @return if the coordinate is possible
     */
    private boolean onBoard(ShipType shipType, Orientation orientation, Coordinate coordinate){
        for (int shipSegment = 0; shipSegment < ShipLength.getLength(shipType); shipSegment++) {
            int x=0,y=0;
            switch (orientation) {
                case HORIZONTAL:
                    x=coordinate.getXCoordinate() + shipSegment;
                    y=coordinate.getYCoordinate();
                    break;
                case VERTICAL:
                    x=coordinate.getXCoordinate();
                    y=coordinate.getYCoordinate() + shipSegment;
                    break;
            }
            if(y<0||y>9||x<0||x>9){
                return false;
            }
        }
        return true;
    }

    /**
     * goes through the list of ships and evaluates if one of the chosen class is available. if it is the case, it
     * returns the position of the ship within the ship-array
     * @param shipType class of ship that is searched for
     * @return position of ship in ship-array
     */
    private int availableShipFromType(ShipType shipType){
        int shipCounter;
        for (shipCounter=0; shipCounter<ships.length-1; shipCounter++){
            if (ShipLength.getLength(shipType)==ships[shipCounter].getLength()){
                break;
            }
        }
        return shipCounter;
    }
}
