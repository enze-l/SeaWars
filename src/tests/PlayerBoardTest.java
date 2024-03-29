package tests;

import boards.*;
import boards.coordinates.*;
import boards.fields.*;
import boards.ships.*;
import gameModules.Game;
import org.junit.Assert;
import exceptions.*;
import org.junit.Test;

public class PlayerBoardTest {

    /**
     * checks for the ability to create a Board
     */
    @Test
    public void createBoard() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinateLeftUpper = new CoordinateImpl(1, 1);
        Coordinate coordinateRightUpper = new CoordinateImpl(10, 1);
        Coordinate coordinateLeftLower = new CoordinateImpl(1, 10);
        Coordinate coordinateRightLower = new CoordinateImpl(10, 10);
        try {
            Assert.assertTrue(
                    board.getFieldStatus(coordinateLeftUpper) == FieldStatus.WATER
                            && board.getFieldStatus(coordinateRightLower) == FieldStatus.WATER
                            && board.getFieldStatus(coordinateRightUpper) == FieldStatus.WATER
                            && board.getFieldStatus(coordinateLeftLower) == FieldStatus.WATER
            );
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * checks if "PREPARATION" Status is set when Board has been created
     */
    @Test
    public void getPreparationStatus() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        try {
            Assert.assertEquals(GameStatus.PREPARATION, board.getGameStatus());
        } catch (StatusException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * checks if a null Status can be set for the Board. It should be not possible, hence the exception
     */
    @Test(expected = StatusException.class)
    public void setNullStatus() throws StatusException {
        try {
            Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
            board.setGameStatus(null);
        }catch (DisplayException ignored){}
    }

    /**
     * checks if GameStatus "READY" can be set and retrieved
     */
    @Test
    public void getReadyStatus() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        try {
            board.setGameStatus(GameStatus.READY);
        } catch (StatusException|DisplayException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertEquals(GameStatus.READY, board.getGameStatus());
        } catch (StatusException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * checks if GameStatus "ATTACK" can be set and retrieved
     */
    @Test
    public void getAttackStatus() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        try {
            board.setGameStatus(GameStatus.ATTACK);
        } catch (StatusException|DisplayException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertEquals(GameStatus.ATTACK, board.getGameStatus());
        } catch (StatusException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * checks if GameStatus "RECEIVE" can be set and retrieved
     */
    @Test
    public void getReceiveStatus() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        try {
            board.setGameStatus(GameStatus.RECEIVE);
        } catch (StatusException|DisplayException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertEquals(GameStatus.RECEIVE, board.getGameStatus());
        } catch (StatusException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * checks if a Field gets the Status "WATER" assigned automatically
     */
    @Test
    public void fieldStatusWater() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate = new CoordinateImpl(1, 1);
        try {
            Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(coordinate));
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test(expected = FieldException.class)
    public void noField() throws FieldException {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate = new CoordinateImpl(0, 0);
        board.getFieldStatus(coordinate);
    }

    /**
     * checks if ship gets set at the right location and is turned horizontal
     */
    @Test
    public void setBattleshipHorizontal() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate0 = new CoordinateImpl(1, 2);
        Coordinate coordinate1 = new CoordinateImpl(2, 2);
        Coordinate coordinate2 = new CoordinateImpl(3, 2);
        Coordinate coordinate3 = new CoordinateImpl(4, 2);
        Coordinate coordinate4 = new CoordinateImpl(5, 2);
        Coordinate coordinate5 = new CoordinateImpl(6, 2);
        Coordinate coordinate6 = new CoordinateImpl(7, 2);
        try {
            board.setShip(ShipType.BATTLESHIP, coordinate1, Orientation.HORIZONTAL);
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertTrue(
                    board.getFieldStatus(coordinate0) == FieldStatus.WATER
                            && board.getFieldStatus(coordinate1) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate2) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate3) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate4) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate5) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate6) == FieldStatus.WATER
            );
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * checks if ship gets set at the right location and is turned horizontal
     */
    @Test
    public void setBattleshipVertically() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate0 = new CoordinateImpl(2, 1);
        Coordinate coordinate1 = new CoordinateImpl(2, 2);
        Coordinate coordinate2 = new CoordinateImpl(2, 3);
        Coordinate coordinate3 = new CoordinateImpl(2, 4);
        Coordinate coordinate4 = new CoordinateImpl(2, 5);
        Coordinate coordinate5 = new CoordinateImpl(2, 6);
        Coordinate coordinate6 = new CoordinateImpl(2, 7);
        try {
            board.setShip(ShipType.BATTLESHIP, coordinate1, Orientation.VERTICAL);
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertTrue(
                    board.getFieldStatus(coordinate0) == FieldStatus.WATER
                            && board.getFieldStatus(coordinate1) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate2) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate3) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate4) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate5) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate6) == FieldStatus.WATER
            );
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void setDestroyerVertically() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate0 = new CoordinateImpl(2, 1);
        Coordinate coordinate1 = new CoordinateImpl(2, 2);
        Coordinate coordinate2 = new CoordinateImpl(2, 3);
        Coordinate coordinate3 = new CoordinateImpl(2, 4);
        try {
            board.setShip(ShipType.DESTROYER, coordinate1, Orientation.VERTICAL);
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertTrue(
                    board.getFieldStatus(coordinate0) == FieldStatus.WATER
                            && board.getFieldStatus(coordinate1) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate2) == FieldStatus.SETSHIP
                            && board.getFieldStatus(coordinate3) == FieldStatus.WATER
            );
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Tests if on an empty board all ships are registered as available
     */
    @Test
    public void allShipsAvailable() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        int[] expected = {1, 2, 3, 4};
        int[] actual = board.shipsAvailable();
        Assert.assertArrayEquals(expected, actual);
    }

    /**
     * Test if all ships are registered as set
     */
    @Test
    public void allShipsSet() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        try {
            board.setShip(ShipType.BATTLESHIP, new CoordinateImpl(1, 1), Orientation.HORIZONTAL);
            board.setShip(ShipType.CRUISER, new CoordinateImpl(1, 3), Orientation.HORIZONTAL);
            board.setShip(ShipType.CRUISER, new CoordinateImpl(1, 5), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(1, 7), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(1, 9), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(8, 1), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8, 3), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8, 5), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8, 7), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8, 9), Orientation.HORIZONTAL);
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
        int[] expected = {0, 0, 0, 0};
        int[] actual = board.shipsAvailable();
        Assert.assertArrayEquals(expected, actual);
    }

    /**
     * Tests if Exception is thrown in the case of trying to set too much ships on the board
     */
    @Test(expected = ShipException.class)
    public void tooMuchShips() throws ShipException {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        try {
            board.setShip(ShipType.BATTLESHIP, new CoordinateImpl(1, 1), Orientation.HORIZONTAL);
            board.setShip(ShipType.CRUISER, new CoordinateImpl(1, 3), Orientation.HORIZONTAL);
            board.setShip(ShipType.CRUISER, new CoordinateImpl(1, 5), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(1, 7), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(1, 9), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(8, 1), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8, 3), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8, 5), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8, 7), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8, 9), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(4, 9), Orientation.HORIZONTAL);
        } catch (FieldException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * test for throw of exception if ship is set outside of Board
     */
    @Test(expected = FieldException.class)
    public void setShipOutside() throws FieldException {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate = new CoordinateImpl(0, 0);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate, Orientation.HORIZONTAL);
        } catch (ShipException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * checks if Ship can be set on another ship
     */
    @Test(expected = ShipException.class)
    public void setShipOnShip() throws ShipException {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate1 = new CoordinateImpl(2, 2);
        Coordinate coordinate2 = new CoordinateImpl(4, 2);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate1, Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, coordinate2, Orientation.HORIZONTAL);
        } catch (FieldException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * checks if ship can be set directly next to another ship
     */
    @Test(expected = ShipException.class)
    public void setShipNextToShip() throws ShipException {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate1 = new CoordinateImpl(2, 2);
        Coordinate coordinate2 = new CoordinateImpl(3, 3);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate1, Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, coordinate2, Orientation.HORIZONTAL);
        } catch (FieldException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * test for throw of exception if ship is set outside of Board
     */
    @Test(expected = FieldException.class)
    public void setShipOnBoarder() throws FieldException {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate = new CoordinateImpl(9, 1);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate, Orientation.HORIZONTAL);
        } catch (ShipException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * test if ship can be set near the boarder of the field
     */
    @Test
    public void setShipNearBoarder() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate = new CoordinateImpl(9, 1);
        try {
            board.setShip(ShipType.DESTROYER, coordinate, Orientation.HORIZONTAL);
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertTrue(
                    board.getFieldStatus(new CoordinateImpl(8, 1)) == FieldStatus.WATER
                            && board.getFieldStatus(new CoordinateImpl(9, 1)) == FieldStatus.SETSHIP
                            && board.getFieldStatus(new CoordinateImpl(10, 1)) == FieldStatus.SETSHIP
                            && board.getFieldStatus(new CoordinateImpl(10, 2)) == FieldStatus.WATER
            );
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * test if ship can be removed in case there is none
     */
    @Test(expected = ShipException.class)
    public void removeNoShip() throws ShipException {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate = new CoordinateImpl(1, 1);
        try {
            board.removeShip(coordinate);
        } catch (FieldException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * test if ship can be removed in case there is none
     */
    @Test(expected = FieldException.class)
    public void removeShipOutsideBoard() throws FieldException {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate = new CoordinateImpl(0, 0);
        try {
            board.removeShip(coordinate);
        } catch (ShipException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * test if ship can be removed
     */
    @Test
    public void removeShip() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate1 = new CoordinateImpl(1, 2);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate1, Orientation.HORIZONTAL);
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
        Coordinate coordinate2 = new CoordinateImpl(3, 2);
        try {
            board.removeShip(coordinate2);
            Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(coordinate1));
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * tests if attack is registered correctly
     */
    @Test
    public void registerHit() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate1 = new CoordinateImpl(2, 2);
        Coordinate coordinate2 = new CoordinateImpl(3, 2);
        try {
            board.setShip(ShipType.DESTROYER, coordinate1, Orientation.HORIZONTAL);
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertSame(FieldStatus.HIT, board.receiveAttack(coordinate2));
        } catch (FieldException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * tests if attack is registered correctly
     */
    @Test
    public void registerSunk() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate1 = new CoordinateImpl(1, 1);
        try {
            board.setShip(ShipType.DESTROYER, coordinate1, Orientation.HORIZONTAL);
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
        Coordinate coordinate2 = new CoordinateImpl(2, 1);
        try {
            board.receiveAttack(coordinate1);
            board.receiveAttack(coordinate2);
        } catch (FieldException|DisplayException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertTrue(board.getFieldStatus(coordinate2) == FieldStatus.SUNK
                    && board.getFieldStatus(coordinate2) == FieldStatus.SUNK
                    && board.getFieldStatus(coordinate1) == FieldStatus.SUNK);
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * tests if a shot in water is registered as one
     */
    @Test
    public void registerHitWater() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        try {
            Assert.assertEquals(board.receiveAttack(new CoordinateImpl(5, 5)), FieldStatus.SHOTWATER);
        } catch (FieldException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * tests if Ship is registered as set
     */
    @Test
    public void getShipField() {
        Game.newGame();
        PlayerBoard board = Game.getPlayerBoard();
        Coordinate coordinate1 = new CoordinateImpl(1, 1);
        Coordinate coordinate2 = new CoordinateImpl(2, 1);
        try {
            board.setShip(ShipType.DESTROYER, coordinate1, Orientation.HORIZONTAL);
        } catch (SeaWarException e) {
            System.err.println(e.getMessage());
        }
        try {
            Assert.assertEquals(FieldStatus.SETSHIP, board.getFieldStatus(coordinate2));
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }
}