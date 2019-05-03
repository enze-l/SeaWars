package game;

import java.util.ArrayList;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Battleship extends Ship {
    private final int length=5;
    private ArrayList<Coordinate> shipCoordinates=new ArrayList<>();

    @Override
    public void Ship() {
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public void setHit(Coordinate coordinate) {

    }

    @Override
    public FieldStatus getShipStatus(Coordinate coordinate) {
        return null;
    }
}
