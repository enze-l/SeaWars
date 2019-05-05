package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ShipLength {
    public static int getLength(ShipType type){
        int length=0;
        switch (type){
            case BATTLESHIP:length=5;
            case CRUISER:length= 4;
            case SUBMARINE:length= 3;
            case DESTROYER:length= 2;
        }
        return length;
    }
}
