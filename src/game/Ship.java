package game;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Ship {
    void Ship();

    int getSize();

    void setShipStatus(Coordinate coordinate);

    FieldStatus getShipStatus(Coordinate coordinate);
}

