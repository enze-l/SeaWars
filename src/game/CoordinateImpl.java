package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class CoordinateImpl implements Coordinate {
    private final int xCoordinate;
    private final int yCoordinate;

    /**
     * subtracts "1" of the given parameters to match the data-representation of the board
     * @param x the x-coordinate of the coordinate
     * @param y the y-coordinate of the coordinate
     */
    CoordinateImpl(int x, int y){
        this.xCoordinate=x-1;
        this.yCoordinate=y-1;
    }

    /** getter-method for x-value of coordinated
     * @return x value
     */
    @Override
    public int getXCoordinate() {
        return this.xCoordinate;
    }

    /**
     * getter-method for y-value of coordinate
     * @return y-value
     */
    @Override
    public int getYCoordinate() {
        return this.yCoordinate;
    }

    /**
     * compares the values of the given coordinate with the own ones
     * @param coordinate the coordinate the should be compared with
     * @return if the contain the same values
     */
    @Override
    public boolean equals(Coordinate coordinate){
        return this.xCoordinate == coordinate.getXCoordinate()
                && this.yCoordinate == coordinate.getYCoordinate();
    }

    /**
     * checks if the coordinate could exist on the board
     * @return if the coordinate can exist
     */
    @Override
    public boolean validCoordinate(){
        return this.xCoordinate>=0
                &&this.xCoordinate<10
                &&this.yCoordinate>=0
                &&this.yCoordinate<10;
    }
}
