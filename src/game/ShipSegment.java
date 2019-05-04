package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
class ShipSegment {
    private FieldStatus status;
    private Coordinate coordinate;

    /**
     * a ship-segment contains information about its place on the board, as well as it's status.
     * Its default status is "Ship" and it doesn't know where it belongs till it is placed.
     */
    ShipSegment(){
        this.coordinate=null;
        this.status=FieldStatus.SHIP;
    }

    /**
     * The ship-segment gets a place on the board assigned.
     * @param coordinate the place on the board
     */
    void segmentAssignment(Coordinate coordinate){
        this.coordinate=coordinate;
    }

    /**
     * The ship-segment gets its coordinates removed and hence doesn't any longer reside on the board.
     */
    void removeAssignment(){
        this.coordinate=null;
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
