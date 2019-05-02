package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Field {

    void setShip(Ship ship);

    void receiveHit();

    FieldStatus getStatus();
}
