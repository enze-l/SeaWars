package game;

import java.util.ArrayList;

/**
 * @author s0568823 - Leon Enzenberger
 */
public abstract class Ship {
    private boolean sunk;
    private int length;
    private Coordinate[] coordinates;
    abstract void Ship();

    public int getLength(){
        return length;
    }

    void setHit(Coordinate coordinate){
        for (int shipLength=0; length<coordinates.length; shipLength++){
            if(coordinates[shipLength].equals(coordinate)){

            }
        }
    };

    abstract FieldStatus getShipStatus(Coordinate coordinate);
}

