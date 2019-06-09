package tests;

import coordinates.CoordinateImpl;
import gameInstance.gameInstance;
import ships.Orientation;
import ships.ShipType;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ConnectionTest {
    public static void main(String[] args) {
        Thread game = new gameInstance();
        game.start();
        gameInstance.changeOccurred();
        try {
            gameInstance.getPlayerBoard().setShip(ShipType.DESTROYER, new CoordinateImpl(1, 1), Orientation.VERTICAL);
            gameInstance.getPlayerBoard().setShip(ShipType.DESTROYER, new CoordinateImpl(3, 1), Orientation.VERTICAL);
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
