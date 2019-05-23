package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class EnemyBoardImpl implements EnemyBoard {
    private Field[][] board;
    private Ship[] ships;
    private GameStatus gameStatus;

    EnemyBoardImpl(){
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

    @Override
    public FieldStatus[][] getFields() {
        return new FieldStatus[0][];
    }

    @Override
    public Ship[] getShips() {
        return new Ship[0];
    }

    @Override
    public FieldStatus getFieldStatus(Coordinate coordinate) throws FieldException {
        return null;
    }

    @Override
    public FieldStatus receiveAttack(Coordinate coordinate) throws FieldException {
        return null;
    }

    @Override
    public void setStatus(GameStatus status) throws StatusException {

    }

    @Override
    public GameStatus getStatus() throws StatusException {
        return null;
    }
}
