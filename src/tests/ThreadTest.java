package tests;

import coordinates.CoordinateImpl;
import gameModules.*;
import ships.Orientation;
import ships.ShipType;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ThreadTest {
    public static void main(String[] args) {
        GameInstance.newGame();
        DisplayInstance d=new DisplayInstance();
        d.start();
        DisplayInstance.changeOccurred();
        try {
            GameInstance.getPlayerBoard().setShip(ShipType.DESTROYER, new CoordinateImpl(1, 1), Orientation.VERTICAL);
            GameInstance.getPlayerBoard().setShip(ShipType.DESTROYER, new CoordinateImpl(3, 1), Orientation.VERTICAL);
        }catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
