package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
class ShipLength {

    /**
     * different lengths that correspond to the different ship classes
     * @param type class of ship that the length is needed from
     * @return length of ship
     */
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
