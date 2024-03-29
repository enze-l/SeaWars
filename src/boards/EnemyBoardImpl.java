package boards;

import boards.coordinates.*;
import boards.fields.*;
import exceptions.*;
import boards.ships.*;
import gameModules.Display;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class EnemyBoardImpl implements EnemyBoard {
    private FieldStatus[][] board;
    private Ship[] ships;
    private GameStatus gameStatus;

    public EnemyBoardImpl() {
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
    public void setFieldStatus(Coordinate coordinate, FieldStatus fieldStatus) throws FieldException {
        if (!coordinate.validCoordinate()) throw new FieldException("There isn't a field with these coordinates!");
        else if(getFieldStatus(coordinate)!=FieldStatus.SUNK) {
            board[coordinate.getXCoordinate()][coordinate.getYCoordinate()] = fieldStatus;
            if (fieldStatus == FieldStatus.SUNK){
                sinkShip(coordinate);
                markShipSunk(coordinate);
                if (allShipsSunk()) this.gameStatus=GameStatus.LOST;
            }
            Display.update();
        }
    }

    @Override
    public void setGameStatus(GameStatus status) throws StatusException {
        if (this.gameStatus == GameStatus.LOST) throw new StatusException("You have lost the battle!");
        this.gameStatus = status;
        Display.update();
    }

    @Override
    public GameStatus getGameStatus() {
        return this.gameStatus;
    }

    @Override
    public boolean allShipsSunk(){
        for (Ship ship : ships) {
            if (ship.getSegmentStatus(1) != FieldStatus.SUNK) return false;
        }
        return true;
    }

    private void markShipSunk(Coordinate coordinate) {
        Coordinate upperField=new CoordinateImpl(coordinate.getXCoordinate()+1, coordinate.getYCoordinate());
        Coordinate leftField=new CoordinateImpl(coordinate.getXCoordinate(), coordinate.getYCoordinate()+1);
        if(upperField.validCoordinate()&&getFieldStatus(upperField)==FieldStatus.SUNK){
            markShipSunk(upperField);
        }
        else if(leftField.validCoordinate()&&getFieldStatus(leftField)==FieldStatus.SUNK){
            markShipSunk(leftField);
        }
        else{
            for (Ship ship : ships) {
                if (ship.getLength() == getShipLength(coordinate) &&
                        ship.getSegmentStatus(1) != FieldStatus.SUNK) {
                    ship.setShipSunk();
                    break;
                }
            }
        }
    }

    private int getShipLength(Coordinate coordinate){
        int originalXCoordinate=coordinate.getXCoordinate()+1;
        int originalYCoordinate=coordinate.getYCoordinate()+1;
        int shipWidth=1;
        int shipHeight=1;
        Coordinate nextYCoordinate=new CoordinateImpl(originalXCoordinate, originalYCoordinate+shipHeight);
        Coordinate nextXCoordinate=new CoordinateImpl(originalXCoordinate+shipWidth, originalYCoordinate);
        while(nextXCoordinate.validCoordinate()&&getFieldStatus(nextXCoordinate)==FieldStatus.SUNK){
            shipWidth++;
            nextXCoordinate=new CoordinateImpl(originalXCoordinate+shipWidth, originalYCoordinate);
        }
        while(nextYCoordinate.validCoordinate()&&getFieldStatus(nextYCoordinate)==FieldStatus.SUNK){
            shipWidth++;
            nextYCoordinate=new CoordinateImpl(originalXCoordinate+shipWidth, originalYCoordinate);
        }
        if(shipHeight>shipWidth)return shipHeight;
        else{return shipWidth;}
    }

    private void sinkShip(Coordinate coordinate){
        if(getFieldStatus(coordinate)==FieldStatus.SUNK){
            Coordinate upperField=new CoordinateImpl(coordinate.getXCoordinate()+1, coordinate.getYCoordinate());
            Coordinate lowerField=new CoordinateImpl(coordinate.getXCoordinate()+1, coordinate.getYCoordinate()+2);
            Coordinate leftField=new CoordinateImpl(coordinate.getXCoordinate(), coordinate.getYCoordinate()+1);
            Coordinate rightField=new CoordinateImpl(coordinate.getXCoordinate()+2, coordinate.getYCoordinate()+1);
            sinkField(upperField);
            sinkField(lowerField);
            sinkField(leftField);
            sinkField(rightField);
        }
    }

    private void sinkField(Coordinate coordinate){
        if (coordinate.validCoordinate()&&getFieldStatus(coordinate)==FieldStatus.HIT){
            board[coordinate.getXCoordinate()][coordinate.getYCoordinate()]=FieldStatus.SUNK;
            sinkShip(coordinate);
        }
    }
}
