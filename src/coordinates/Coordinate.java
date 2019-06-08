package coordinates;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Coordinate {

    /**
     * @return the x-coordinate of the coordinate
     */
    int getXCoordinate();

    /**
     * @return the y-coordinate of the coordinate
     */
    int getYCoordinate();

    /**
     * compares the variables of a given coordinate with the coordinate
     * @param coordinate the coordinate the should be compared with
     * @return if the parameters of both coordinates are the same
     */
    boolean equals(Coordinate coordinate);

    /**
     * checks if the coordinate could reside on the board
     * @return if the coordinate could reside on the board
     */
    boolean validCoordinate();
}
