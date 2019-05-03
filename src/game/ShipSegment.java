package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ShipSegment {
    private FieldStatus status;
    private Coordinate coordinate;

    ShipSegment(Coordinate coordinate){
        this.coordinate=coordinate;
        this.status=FieldStatus.SHIP;
    }

    Coordinate getCoorinate(){
        return this.coordinate;
    }

    FieldStatus getStatus(){
        return this.status;
    }

    void setHit(){
        this.status=FieldStatus.HIT;
    }

    void setSunk(){
        this.status=FieldStatus.SUNK;
    }
}
