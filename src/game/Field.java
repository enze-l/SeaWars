package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Field {

    /**
     * @return the assigned ship
     */
    Ship getShip();

    /**
     * assignees a ship to a coordinate
     *
     * @param ship the ship which should be saved in the field
     */
    void setShip(Ship ship);

    /**
     * removes assignment of a ship to a field
     */
    void removeShip();

    /**
     * accepts hit
     */
    void receiveHit();

    /**
     * @return status of the field
     */
    FieldStatus getFieldStatus();
}
