package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public abstract class Ship {
    private ShipSegment[] segments;

    Ship(int length){
        segments =new ShipSegment[length];
    }

    public void setShip(Coordinate coordinate, Orientation orientation){
        for(int segment=0; segment<segments.length; segment++){
            if (orientation==Orientation.HORIZONTAL) {
                segments[segment] = new ShipSegment(new CoordinateImpl(coordinate.getXCoordinate()+segment, coordinate.getYCoordinate()));
            }
            if (orientation==Orientation.VERTICAL) {
                segments[segment] = new ShipSegment(new CoordinateImpl(coordinate.getXCoordinate(), coordinate.getYCoordinate()+segment));
            }
        }
    }

    public int getLength(){
        return segments.length;
    }

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

    FieldStatus getShipStatus(Coordinate coordinate){
        FieldStatus fieldStatus=null;
        for (ShipSegment shipSegment : segments) {
            if (shipSegment.getCoordinate().equals(coordinate)) {
                fieldStatus= shipSegment.getStatus();
            }
        }
        return fieldStatus;
    }
}

