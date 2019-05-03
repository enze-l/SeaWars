package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Board implements MyBoard {
    private Field[][] board;
    private int[] ships;
    private GameStatus gameStatus;

    Board() {
        this.board=new FieldImpl[10][10];
        for(int fieldsHorizontal=0; fieldsHorizontal<10; fieldsHorizontal++){
            for (int fieldsVertical=0; fieldsVertical<10; fieldsVertical++){
                this.board[fieldsHorizontal][fieldsVertical]=new FieldImpl(new CoordinateImpl(fieldsHorizontal+1, fieldsVertical+1));
            }
        }

        this.gameStatus=GameStatus.PREPARATION;

        this.ships=new int[]{1,2,3,4};
    }

    @Override
    public GameStatus getStatus() {
        return this.gameStatus;
    }

    @Override
    public void setStatus(GameStatus status) {
        this.gameStatus=status;
    }

    @Override
    public int[] shipsAvailable() {
        return ships;
    }

    @Override
    public void setShip(Ship ship, Orientation orientation, Coordinate coordinate) throws FieldException{

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
