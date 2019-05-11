package game;

/**
 * @author s0568823 - Leon Enzenberger
 */

/**
 * different lengths that correspond to the different ship classes
 */
class ShipLength {
    static int getLength(ShipType type) {
        int length = 0;
        switch (type) {
            case BATTLESHIP:
                length = 5;
                break;
            case CRUISER:
                length = 4;
                break;
            case SUBMARINE:
                length = 3;
                break;
            case DESTROYER:
                length = 2;
        }
        return length;
    }
}
