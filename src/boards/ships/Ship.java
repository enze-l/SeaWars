package boards.ships;

import boards.coordinates.*;
import boards.fields.*;

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
    final public void setShip(Coordinate coordinate, Orientation orientation){
        for(int segment=0; segment<segments.length; segment++){
            if (orientation==Orientation.HORIZONTAL) {
                segments[segment].segmentAssignment(new CoordinateImpl(coordinate.getXCoordinate()+segment+1, coordinate.getYCoordinate()+1));
            }
            if (orientation==Orientation.VERTICAL) {
                segments[segment].segmentAssignment(new CoordinateImpl(coordinate.getXCoordinate()+1, coordinate.getYCoordinate()+segment+1));
            }
        }
    }

    /**
     * resets the status of the Ship
     */
    final public void removeShip(){
        for (ShipSegment segment:segments){
            segment.removeAssignment();
        }
    }

    /**
     * @return anchor point of ship on board
     */
    final public Coordinate getPosition(){
        return segments[0].getCoordinate();
    }

    /**
     * Getter-method for the length of the ship
     * @return length of ship
     */
    final public int getLength(){
        return segments.length;
    }

    /**
     * Compares the given coordinate with the coordinates saved in the segments of the ship.
     * In case the given coordinate is found, the status of the segment is changed to "Hit".
     * Afterwards it is checked if all segments of the ship are registered as "Hit" and if they are, all are given the
     * status "sunk"
     * @param coordinate the coordinate that is getting attacked
     */
    final public void setHit(Coordinate coordinate){
        for (ShipSegment segment : segments) {
            if (segment.getCoordinate().equals(coordinate)) {
                segment.setHit();
            }
        }
        if (allHit()) setShipSunk();
    }

    private boolean allHit(){
        boolean allHit=false;
        for (ShipSegment segment: segments){
            if (segment.getStatus()== FieldStatus.HIT){
                allHit=true;
            }
            else if (segment.getStatus()==FieldStatus.SETSHIP){
                allHit=false;
                break;
            }
        }
        return allHit;
    }

    /**
     * Assignees every ship-segment the status "SUNK"
     */
    public void setShipSunk(){
        for (ShipSegment segment: segments){
            segment.setSegmentSunk();
        }
    }

    /**
     * compares the given coordinate with the ones registered in the segments of the ship. If the searched for is found,
     * its status is given back
     * @param coordinate coordinate of witch the status is asked for
     * @return status of the ship-segment
     */
    public FieldStatus getSegmentStatus(Coordinate coordinate){
        FieldStatus fieldStatus=null;
        for (ShipSegment shipSegment : segments) {
            if (shipSegment.getCoordinate().equals(coordinate)) {
                fieldStatus=shipSegment.getStatus();
            }
        }
        return fieldStatus;
    }

    /**
     * returns the status of the segment
     * @param segment the number of the segment (non Data Format)
     * @return Status of the Segment
     */
    public FieldStatus getSegmentStatus(int segment){
        return this.segments[segment-1].getStatus();
    }
}