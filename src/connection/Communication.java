package connection;

import boards.fields.FieldStatus;
import coordinates.Coordinate;
import exceptions.InputException;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Communication {

    FieldStatus attackEnemy(Coordinate coordinate)throws InputException, IOException;

    void shot();

    void giveUp();

    void start();

    void receive();

    void ready();
}
