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
    public void setShip(ShipType shipType, Orientation orientation, Coordinate coordinate) throws FieldException, ShipException{
        switch (shipType){
            case BATTLESHIP:

        }
    }

    @Override
    public void removeShip(Coordinate coordinates) {
    }

    @Override
    public Coordinate sendAttack() {
        return null;
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

    @Override
    public void displayBoard() {

    }
}
