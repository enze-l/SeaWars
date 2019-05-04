package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public abstract class Ship {
    private ShipSegment[] segments;

    /**
     * Constructor creates as many ship-segments as needed for given length
     * @param length the length of the ship
     */
    Ship(int length){
        segments= new ShipSegment[length];
        for (int segment=0; segment<length; segment++){
            segments[segment] = new ShipSegment();
        }
    }

    /**
     * Takes the given coordinate as anchor and rotates the ship in the given orientation by creating ship-segments with
     * the following coordinates in either the x or y direction
     * @param coordinate upper left segment of the ship (anchor-point)
     * @param orientation the direction in witch the ship is pointed
     */
    public void setShip(Coordinate coordinate, Orientation orientation){
        for(int segment=0; segment<segments.length; segment++){
            if (orientation==Orientation.HORIZONTAL) {
                segments[segment].segmentAssignment(new CoordinateImpl(coordinate.getXCoordinate()+segment, coordinate.getYCoordinate()));
            }
            if (orientation==Orientation.VERTICAL) {
                segments[segment].segmentAssignment(new CoordinateImpl(coordinate.getXCoordinate(), coordinate.getYCoordinate()+segment));
            }
        }
    }

    /**
     * @return anchor point of ship on board
     */
    public Coordinate getPosition(){
        return segments[0].getCoordinate();
    }

    /**
     * resets the status of
     */
    public void removeShip(){
        for (ShipSegment segment:segments){
            segment.removeAssignment();
        }
    }

    /**
     * Getter-method for the length of the ship
     * @return length of ship
     */
    public int getLength(){
        return segments.length;
    }

    /**
     * Compares the given coordinate with the coordinates saved in the segments of the ship.
     * In case the given coordinate is found, the status of the segment is changed to "Hit".
     * Afterwards it is checked if all segments of the ship are registered as "Hit" and if they are, all are given the
     * status "sunk"
     * @param coordinate the coordinate that is getting attacked
     */
    void setHit(Coordinate coordinate){
        boolean sunk=false;
        for (ShipSegment shipSegment : segments) {
            if (shipSegment.getCoordinate().equals(coordinate)) {
                shipSegment.setHit();
            }
        }

        for (ShipSegment shipSegment: segments){
            if (shipSegment.getStatus()==FieldStatus.HIT){
                sunk=true;
            }
            else if (shipSegment.getStatus()==FieldStatus.SHIP){
                sunk=false;
                break;
            }
        }

        if (sunk){
            for (ShipSegment shipSegment: segments){
                shipSegment.setSunk();
            }
        }
    }

    /**
     * compares the given coordinate with the ones registered in the segments of the ship. If the searched for is found,
     * its status is given back
     * @param coordinate coordinate of witch the status is asked for
     * @return status of the ship-segment
     */
    FieldStatus getShipStatus(Coordinate coordinate){
        FieldStatus fieldStatus=null;
        for (ShipSegment shipSegment : segments) {
            if (shipSegment.getCoordinate().equals(coordinate)) {
                fieldStatus=shipSegment.getStatus();
            }
        }
        return fieldStatus;
    }
}

