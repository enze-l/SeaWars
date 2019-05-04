package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
class ShipSegment {
    private FieldStatus status;
    private Coordinate coordinate;

    /**
     * a ship-segment contains information about its place on the board, as well as it's status.
     * Its default status is "Ship".
     * @param coordinate is the place of the segment on the board
     */
    ShipSegment(Coordinate coordinate){
        this.coordinate=coordinate;
        this.status=FieldStatus.SHIP;
    }

    /**
     * @return placement of segment on the board
     */
    Coordinate getCoordinate(){
        return this.coordinate;
    }

    /**
     * @return status of the segment
     */
    FieldStatus getStatus(){
        return this.status;
    }

    /**
     * changes status of segment to "Hit"
     */
    void setHit(){
        this.status=FieldStatus.HIT;
    }

    /**
     * changes status of segment to "Sunk"
     */
    void setSunk(){
        this.status=FieldStatus.SUNK;
    }
}
