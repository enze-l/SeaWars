package game;

import java.util.ArrayList;

/**
 * @author s0568823 - Leon Enzenberger
 */
public abstract class Ship {
    private ShipSegment[] segment;

    Ship(int length){
        segment=new ShipSegment[length];
    }

    public int getLength(){
        return segment.length;
    }

    void setHit(Coordinate coordinate){
        boolean sunk=false;
        for (ShipSegment shipSegment : segment) {
            if (shipSegment.getCoorinate().equals(coordinate)) {
                shipSegment.setHit();
            }
        }

        for (ShipSegment shipSegment:segment){
            if (shipSegment.getStatus()==FieldStatus.HIT){
                sunk=true;
            }
            else if (shipSegment.getStatus()==FieldStatus.SHIP){
                sunk=false;
                break;
            }
        }

        if (sunk){
            for (ShipSegment shipSegment:segment){
                shipSegment.setSunk();
            }
        }
    }

    FieldStatus getShipStatus(Coordinate coordinate){
        FieldStatus fieldStatus=null;
        for (ShipSegment shipSegment : segment) {
            if (shipSegment.getCoorinate().equals(coordinate)) {
                fieldStatus= shipSegment.getStatus();
            }
        }
        return fieldStatus;
    }
}

