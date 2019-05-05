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
        for (int fieldsHorizontal = 0; fieldsHorizontal < 10; fieldsHorizontal++) {
            for (int fieldsVertical = 0; fieldsVertical < 10; fieldsVertical++) {
                this.board[fieldsHorizontal][fieldsVertical] = new FieldImpl(new CoordinateImpl(fieldsHorizontal + 1, fieldsVertical + 1));
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
    public void setStatus(GameStatus status) {
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
        if (shipsAvailable()[shipType.ordinal()]>0&&shipPlacementPossible(shipType,orientation, coordinate)) {
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
    public void removeShip(Coordinate coordinates) {
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

        return this.board[coordinate.getXCoordinate()-1][coordinate.getYCoordinate()-1].getStatus();
    }

    private boolean shipPlacementPossible(ShipType shipType, Orientation orientation, Coordinate coordinate) throws FieldException, ShipException{
        for (int shipSegment = 0; shipSegment < ShipLength.getLength(shipType); shipSegment++) {
            switch (orientation) {
                case HORIZONTAL:
                    if (!onBoard(new CoordinateImpl(coordinate.getXCoordinate() + shipSegment, coordinate.getYCoordinate()))) {
                        throw new FieldException("Das Schiff kann nicht ausserhalb des Spielfelds platziert werden!");
                    }
                    if (board[coordinate.getXCoordinate() + shipSegment][coordinate.getYCoordinate()].getStatus() != FieldStatus.WATER) {
                        throw new FieldException("An dieser Stelle liegt bereits ein Schiff!");
                    }
                    break;
                case VERTICAL:
                    if (!onBoard(new CoordinateImpl(coordinate.getXCoordinate(), coordinate.getYCoordinate() + shipSegment))) {
                        throw new FieldException("Das Schiff kann nicht ausserhalb des Spielfelds platziert werden!");
                    }
                    if (board[coordinate.getXCoordinate()][coordinate.getYCoordinate() + shipSegment].getStatus() != FieldStatus.WATER) {
                        throw new FieldException("An dieser Stelle liegt bereits ein Schiff!");
                    }
                    break;
            }
        }
        return !shipNearby(shipType, orientation, coordinate);
    }

    private boolean shipNearby(ShipType shipType, Orientation orientation, Coordinate coordinate) throws ShipException {
        for (int shipOutline = -1; shipOutline < ShipLength.getLength(shipType) + 1; shipOutline++) {
            switch (orientation) {
                case HORIZONTAL:
                    if (shipOutline == -1 && board[coordinate.getXCoordinate() - 1][coordinate.getYCoordinate()].getStatus() == FieldStatus.SHIP) {
                        throw new ShipException("Das Schiff kann nicht direkt neben einem anderen gesetzt werden!");
                    } else if (shipOutline == ShipLength.getLength(shipType)
                            && board[coordinate.getXCoordinate() + 1][coordinate.getYCoordinate()].getStatus() == FieldStatus.SHIP) {
                        throw new ShipException("Das Schiff kann nicht direkt neben einem anderen gesetzt werden!");
                    } else if (shipOutline>=0&&shipOutline<ShipLength.getLength(shipType)
                            &&board[coordinate.getXCoordinate()][coordinate.getYCoordinate() + 1].getStatus() == FieldStatus.SHIP
                            && board[coordinate.getXCoordinate()][coordinate.getYCoordinate() - 1].getStatus() == FieldStatus.SHIP) {
                        throw new ShipException("Das Schiff kann nicht direkt neben einem anderen gesetzt werden!");
                    }
                    break;
                case VERTICAL:
                    if (shipOutline == -1 && board[coordinate.getXCoordinate()][coordinate.getYCoordinate()-1].getStatus() == FieldStatus.SHIP) {
                        throw new ShipException("Das Schiff kann nicht direkt neben einem anderen gesetzt werden!");
                    } else if (shipOutline == ShipLength.getLength(shipType)
                            && board[coordinate.getXCoordinate()][coordinate.getYCoordinate()+1].getStatus() == FieldStatus.SHIP) {
                        throw new ShipException("Das Schiff kann nicht direkt neben einem anderen gesetzt werden!");
                    }else if (shipOutline>=0&&shipOutline<ShipLength.getLength(shipType)
                            &&board[coordinate.getXCoordinate()+1][coordinate.getYCoordinate()].getStatus() == FieldStatus.SHIP
                            && board[coordinate.getXCoordinate()-1][coordinate.getYCoordinate()].getStatus() == FieldStatus.SHIP) {
                        throw new ShipException("Das Schiff kann nicht direkt neben einem anderen gesetzt werden!");
                    }
                    break;
            }
        }
        return true;
    }

    /**
     * checks if given coordinate is possible on the board
     * @param coordinate the place that should be checked
     * @return if the coordinate is possible
     */
    private boolean onBoard(Coordinate coordinate){
        return coordinate.getXCoordinate() >= 1
                && coordinate.getXCoordinate() <= 10
                && coordinate.getYCoordinate() >= 1
                && coordinate.getYCoordinate() <= 10;
    }

    private int availableShipFromType(ShipType shipType) throws ShipException{
        for (int shipCounter=0; shipCounter<ships.length; shipCounter++){
            if (ships[0].getLength()==ShipLength.getLength(shipType)){
                return shipCounter;
            }
        }
        throw new ShipException("Es sind bereits alle Schiffe dieser Klasse platziert!");
    }
}
