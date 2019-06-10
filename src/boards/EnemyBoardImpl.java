package boards;

import coordinates.*;
import boards.fields.*;
import exceptions.StatusException;
import ships.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class EnemyBoardImpl implements EnemyBoard {
    private FieldStatus[][] board;
    private Ship[] ships;
    private GameStatus gameStatus;

    public EnemyBoardImpl(){
        this.board = new FieldStatus[10][10];
        for (int fieldsHorizontal = 1; fieldsHorizontal <= 10; fieldsHorizontal++) {
            for (int fieldsVertical = 1; fieldsVertical <= 10; fieldsVertical++) {
                this.board[fieldsHorizontal - 1][fieldsVertical - 1] = FieldStatus.WATER;
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
        return this.board;
    }

    @Override
    public Ship[] getShips() {
        return this.ships;
    }

    @Override
    public FieldStatus getFieldStatus(Coordinate coordinate) {
        return board[coordinate.getXCoordinate()][coordinate.getYCoordinate()];
    }

    @Override
    public FieldStatus setFieldStatus(Coordinate coordinate, FieldStatus fieldStatus) {
        //NEEDS TO IMPLEMENT COMMUNICATION
        return null;
    }

    @Override
    public void setStatus(GameStatus status)throws StatusException {
        if (this.gameStatus==GameStatus.OVER)throw new StatusException("Game is over!");
        this.gameStatus = status;
    }

    @Override
    public GameStatus getStatus() {
        return this.gameStatus;
    }
}
