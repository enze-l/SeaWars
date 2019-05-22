package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
class ShipInfo {

    /**
     * different lengths that correspond to the different ship classes
     * @param type class of ship that the length is needed from
     * @return length of ship
     */
    static int getLength(ShipType type) {
        int length=0;
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
                break;
        }
        return length;
    }

    static int getAmount(ShipType type){
        int amount=0;
        switch (type) {
            case BATTLESHIP:
                amount = 1;
                break;
            case CRUISER:
                amount = 2;
                break;
            case SUBMARINE:
                amount = 3;
                break;
            case DESTROYER:
                amount = 4;
                break;
        }
        return amount;
    }
}
