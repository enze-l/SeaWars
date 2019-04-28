package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Board implements MyBoard {
    public void Board() {
    }

    @Override
    public GameStatus getStatus() {
        return null;
    }

    @Override
    public void setStatus(GameStatus status) {
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
    public FieldStatus getFieldStatus(Coordinate coordinate) {
        return null;
    }

    @Override
    public void displayBoard() {

    }
}
