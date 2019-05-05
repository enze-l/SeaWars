package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class CoordinateImpl implements Coordinate {
    private final int xCoordinate;
    private final int yCoordinate;

    CoordinateImpl(int x, int y){
        this.xCoordinate=x-1;
        this.yCoordinate=y-1;
    }

    @Override
    public int getXCoordinate() {
        return this.xCoordinate;
    }

    @Override
    public int getYCoordinate() {
        return this.yCoordinate;
    }

    @Override
    public boolean equals(Coordinate coordinate){
        if (this.xCoordinate==coordinate.getXCoordinate()
                &&this.yCoordinate==coordinate.getYCoordinate()){
            return true;
        }
        else return false;
    }
}
