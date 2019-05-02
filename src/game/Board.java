package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Board implements MyBoard {
    private Field[][] board;
    private GameStatus gameStatus;

    Board() {
        this.board=new FieldImpl[10][10];
        for(int fieldsHorizontal=0; fieldsHorizontal<10; fieldsHorizontal++){
            for (int fieldsVertical=0; fieldsVertical<10; fieldsVertical++){
                this.board[fieldsHorizontal][fieldsVertical]=new FieldImpl(new CoordinateImpl(fieldsHorizontal+1, fieldsVertical+1));
            }
        }
        this.gameStatus=GameStatus.PREPARATION;
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
        return new int[0];
    }

    @Override
    public void setShip(Ship ship, Orientation orientation, Coordinate coordinate) {
    }

    @Override
    public void removeShip(Coordinate coordinates) {
    }

    @Override
    public Coordinate sendAttack() {
        return null;
    }

    @Override
    public FieldStatus receiveAttack(Coordinate coordinates) {
        return null;
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
