package tests;

import boards.PlayerBoard;
import boards.coordinates.CoordinateImpl;
import gameModules.*;
import boards.ships.Orientation;
import boards.ships.ShipType;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ThreadTest {
    public static void main(String[] args) {
        PlayerBoard playerBoard=GameInstance.getPlayerBoard();
        DisplayInstance d = new DisplayInstance();
        d.start();
        try {
            Thread.sleep(100);
            playerBoard.setShip(ShipType.DESTROYER, new CoordinateImpl(1, 1), Orientation.VERTICAL);
            Thread.sleep(100);
            playerBoard.setShip(ShipType.DESTROYER, new CoordinateImpl(3, 1), Orientation.VERTICAL);
            Thread.sleep(100);
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        d.close();
    }
}
