package game;

import org.junit.Assert;
import org.junit.Test;

public class MyBoardTest {

    /**
     * checks for the ability to create a Board
     */
    @Test
    public void createBoard(){
        Board board = new Board();
        Coordinate coordinateLeftUpper=new CoordinateImpl(1,1);
        Coordinate coordinateRightUpper=new CoordinateImpl(10,1);
        Coordinate coordinateLeftLower=new CoordinateImpl(1,10);
        Coordinate coordinateRightLower=new CoordinateImpl(10,10);
        try {
            Assert.assertTrue(
                    board.getFieldStatus(coordinateLeftUpper) == FieldStatus.WATER
                            && board.getFieldStatus(coordinateRightLower) == FieldStatus.WATER
                            && board.getFieldStatus(coordinateRightUpper) == FieldStatus.WATER
                            && board.getFieldStatus(coordinateLeftLower) == FieldStatus.WATER
            );
        } catch (FieldException e){}
    }

    /**
     * checks if "PREPARATION" Status is set when Board has been created
     */
    @Test
    public void getStartStatus(){
        Board board = new Board();
        Assert.assertEquals(GameStatus.PREPARATION, board.getStatus());
    }

    /**
     * checks if a null Status can be set for the Board. It should be not possible, hence the exception
     */
    @Test(expected = StatusException.class)
    public void setNullStatus(){
        Board board=new Board();
        board.setStatus(null);
    }

    /**
     * checks if GameStatus "READY" can be set and retrieved
     */
    @Test
    public void getReadyStatus(){
        Board board = new Board();
        board.setStatus(GameStatus.READY);
        Assert.assertEquals(GameStatus.READY, board.getStatus());
    }

    /**
     * checks if GameStatus "ATTACK" can be set and retrieved
     */
    @Test
    public void getAttackStatus(){
        Board board = new Board();
        board.setStatus(GameStatus.ATTACK);
        Assert.assertEquals(GameStatus.ATTACK, board.getStatus());
    }

    /**
     * checks if GameStatus "RECEIVE" can be set and retrieved
     */
    @Test
    public void getReceiveStatus(){
        Board board = new Board();
        board.setStatus(GameStatus.RECEIVE);
        Assert.assertEquals(GameStatus.RECEIVE, board.getStatus());
    }

    /**
     * checks if GameStatus "OVER" can be set and retrieved
     */
    @Test
    public void getOverStatus(){
        Board board = new Board();
        board.setStatus(GameStatus.OVER);
        Assert.assertEquals(GameStatus.OVER, board.getStatus());
    }

    /**
     * checks if a Field gets the Status "WATER" assigned automatically
     */
    @Test
    public void fieldStatusWater(){
        Board board=new Board();
        Coordinate coordinate=new CoordinateImpl(1,1);
        try {
            Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(coordinate));
        }catch(FieldException e){}
    }

    @Test(expected = FieldException.class)
    public void noField() throws FieldException {
        Board board=new Board();
        Coordinate coordinate=new CoordinateImpl(0,0);
        board.getFieldStatus(coordinate);
    }

    /**
     *checks if ship gets set at the right location and is turned horizontal
     */
    @Test
    public void setShipHorizontal(){
        Board board=new Board();
        Ship battleship=new Battleship();
        Coordinate coordinate0=new CoordinateImpl(2,1);
        Coordinate coordinate1=new CoordinateImpl(2,2);
        Coordinate coordinate2=new CoordinateImpl(2,3);
        Coordinate coordinate3=new CoordinateImpl(2,4);
        Coordinate coordinate4=new CoordinateImpl(2,5);
        Coordinate coordinate5=new CoordinateImpl(2,6);
        Coordinate coordinate6=new CoordinateImpl(2,7);
        try {
            board.setShip(battleship, Orientation.HORIZONTAL, coordinate1);
        }catch (FieldException e){}
        try {
            Assert.assertTrue(
                    board.getFieldStatus(coordinate0) == FieldStatus.WATER
                            && board.getFieldStatus(coordinate1) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate2) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate3) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate4) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate5) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate6) == FieldStatus.WATER
            );
        } catch(FieldException e){}
    }

    /**
     *checks if ship gets set at the right location and is turned horizontal
     */
    @Test
    public void setShipVertically(){
        Board board=new Board();
        Ship battleship=new Battleship();
        Coordinate coordinate0=new CoordinateImpl(1,2);
        Coordinate coordinate1=new CoordinateImpl(2,2);
        Coordinate coordinate2=new CoordinateImpl(3,2);
        Coordinate coordinate3=new CoordinateImpl(4,2);
        Coordinate coordinate4=new CoordinateImpl(5,2);
        Coordinate coordinate5=new CoordinateImpl(6,2);
        Coordinate coordinate6=new CoordinateImpl(7,2);
        try {
            board.setShip(battleship, Orientation.VERTICAL, coordinate1);
        } catch (FieldException e){}
        try {
            Assert.assertTrue(
                    board.getFieldStatus(coordinate0) == FieldStatus.WATER
                            && board.getFieldStatus(coordinate1) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate2) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate3) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate4) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate5) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate6) == FieldStatus.WATER
            );
        }catch (FieldException e){}
    }

    /**
     * Tests if on an empty board all ships are registered as available
     */
    @Test
    public void allShipsAvailable(){
        Board board=new Board();
        int[] expected={1,2,3,4};
        Assert.assertArrayEquals(expected, board.shipsAvailable());
    }

    /**
     * Test if all ships are registered as set
    */
    @Test
    public void allShipsSet(){
        Board board=new Board();
        Ship battleship =new Battleship();
        Ship cruiser1   =new Cruiser();
        Ship cruiser2   =new Cruiser();
        Ship submarine1 =new Submarine();
        Ship submarine2 =new Submarine();
        Ship submarine3 =new Submarine();
        Ship destroyer1 =new Destroyer();
        Ship destroyer2 =new Destroyer();
        Ship destroyer3 =new Destroyer();
        Ship destroyer4 =new Destroyer();
        Coordinate coordinateB=new CoordinateImpl(1,1);
        Coordinate coordinateC1=new CoordinateImpl(1,3);
        Coordinate coordinateC2=new CoordinateImpl(1,5);
        Coordinate coordinateS1=new CoordinateImpl(1,7);
        Coordinate coordinateS2=new CoordinateImpl(1,9);
        Coordinate coordinateS3=new CoordinateImpl(8,1);
        Coordinate coordinateD1=new CoordinateImpl(8,3);
        Coordinate coordinateD2=new CoordinateImpl(8,5);
        Coordinate coordinateD3=new CoordinateImpl(8,7);
        Coordinate coordinateD4=new CoordinateImpl(8,9);
        try {
            board.setShip(battleship, Orientation.HORIZONTAL, coordinateB);
            board.setShip(cruiser1, Orientation.HORIZONTAL, coordinateC1);
            board.setShip(cruiser2, Orientation.HORIZONTAL, coordinateC2);
            board.setShip(submarine1, Orientation.HORIZONTAL, coordinateS1);
            board.setShip(submarine2, Orientation.HORIZONTAL, coordinateS2);
            board.setShip(submarine3, Orientation.HORIZONTAL, coordinateS3);
            board.setShip(destroyer1, Orientation.HORIZONTAL, coordinateD1);
            board.setShip(destroyer2, Orientation.HORIZONTAL, coordinateD2);
            board.setShip(destroyer3, Orientation.HORIZONTAL, coordinateD3);
            board.setShip(destroyer4, Orientation.HORIZONTAL, coordinateD4);
        }catch (FieldException e){}
        int[] expected={0,0,0,0};
        Assert.assertArrayEquals(expected, board.shipsAvailable());
    }

    /**
     * Tests if Exception is thrown in the case of trying to set too much ships on the board
     */
    @Test(expected = ShipException.class)
    public void tooMuchShips(){
        Board board=new Board();
        Ship battleship =new Battleship();
        Ship cruiser1   =new Cruiser();
        Ship cruiser2   =new Cruiser();
        Ship submarine1 =new Submarine();
        Ship submarine2 =new Submarine();
        Ship submarine3 =new Submarine();
        Ship destroyer1 =new Destroyer();
        Ship destroyer2 =new Destroyer();
        Ship destroyer3 =new Destroyer();
        Ship destroyer4 =new Destroyer();
        Ship destroyer5 =new Destroyer();
        Coordinate coordinateB=new CoordinateImpl(1,1);
        Coordinate coordinateC1=new CoordinateImpl(1,3);
        Coordinate coordinateC2=new CoordinateImpl(1,5);
        Coordinate coordinateS1=new CoordinateImpl(1,7);
        Coordinate coordinateS2=new CoordinateImpl(1,9);
        Coordinate coordinateS3=new CoordinateImpl(8,1);
        Coordinate coordinateD1=new CoordinateImpl(8,3);
        Coordinate coordinateD2=new CoordinateImpl(8,5);
        Coordinate coordinateD3=new CoordinateImpl(8,7);
        Coordinate coordinateD4=new CoordinateImpl(8,9);
        Coordinate coordinateD5=new CoordinateImpl(4,9);
        try {
            board.setShip(battleship, Orientation.HORIZONTAL, coordinateB);
            board.setShip(cruiser1, Orientation.HORIZONTAL, coordinateC1);
            board.setShip(cruiser2, Orientation.HORIZONTAL, coordinateC2);
            board.setShip(submarine1, Orientation.HORIZONTAL, coordinateS1);
            board.setShip(submarine2, Orientation.HORIZONTAL, coordinateS2);
            board.setShip(submarine3, Orientation.HORIZONTAL, coordinateS3);
            board.setShip(destroyer1, Orientation.HORIZONTAL, coordinateD1);
            board.setShip(destroyer2, Orientation.HORIZONTAL, coordinateD2);
            board.setShip(destroyer3, Orientation.HORIZONTAL, coordinateD3);
            board.setShip(destroyer4, Orientation.HORIZONTAL, coordinateD4);
            board.setShip(destroyer5, Orientation.HORIZONTAL, coordinateD5);
        }catch (FieldException e){}
    }

    /**
     * test for throw of exception if ship is set outside of Board
     */
    @Test(expected = FieldException.class)
    public void setShipOutside()throws FieldException{
        Board board=new Board();
        Ship ship=new Submarine();
        Coordinate coordinate=new CoordinateImpl(0,0);
        board.setShip(ship, Orientation.HORIZONTAL, coordinate);
    }

    /**
     * checks if Ship can be set on another ship
     */
    @Test(expected = FieldException.class)
    public void setShipOnShip()throws FieldException{
        Board board=new Board();
        Ship ship1=new Submarine();
        Ship ship2=new Submarine();
        Coordinate coordinate1=new CoordinateImpl(1,1);
        Coordinate coordinate2=new CoordinateImpl(1,3);
        board.setShip(ship1, Orientation.HORIZONTAL,coordinate1);
        board.setShip(ship2, Orientation.HORIZONTAL,coordinate2);
    }

    /**
     * checks if ship can be set directly next to another ship
     */
    @Test(expected = FieldException.class)
    public void setShipNextToShip()throws FieldException{
        Board board=new Board();
        Ship ship1=new Submarine();
        Ship ship2=new Submarine();
        Coordinate coordinate1=new CoordinateImpl(1,1);
        Coordinate coordinate2=new CoordinateImpl(2,3);
        board.setShip(ship1, Orientation.HORIZONTAL,coordinate1);
        board.setShip(ship2, Orientation.HORIZONTAL,coordinate2);
    }

    /**
     * test for throw of exception if ship is set outside of Board
     */
    @Test(expected = FieldException.class)
    public void setShipOnBoarder()throws FieldException{
        Board board=new Board();
        Ship ship=new Submarine();
        Coordinate coordinate=new CoordinateImpl(9,1);
        board.setShip(ship, Orientation.HORIZONTAL,coordinate);
    }

    /**
     * test if ship can be set outside of the Board
     */
    @Test(expected = ShipException.class)
    public void setShipAgain(){
        Board board=new Board();
        Ship ship=new Submarine();
        Coordinate coordinate1=new CoordinateImpl(1,1);
        Coordinate coordinate2=new CoordinateImpl(3,1);
        try {
            board.setShip(ship, Orientation.HORIZONTAL, coordinate1);
            board.setShip(ship, Orientation.HORIZONTAL, coordinate2);
        }catch (FieldException e){}
    }

    /**
     * test if ship can be removed in case there is none
     */
    @Test(expected = ShipException.class)
    public void removeNoShip(){
        Board board=new Board();
        Coordinate coordinate=new CoordinateImpl(1,1);
        board.removeShip(coordinate);
    }

    /**
     * test if ship can be removed in case there is none
     */
    @Test(expected = FieldException.class)
    public void removeShipOutsideBoard(){
        Board board=new Board();
        Coordinate coordinate=new CoordinateImpl(0,0);
        board.removeShip(coordinate);
    }

    /**
     * test if ship can be removed
     */
    @Test
    public void removeShip(){
        Board board=new Board();
        Ship ship=new Submarine();
        Coordinate coordinate1=new CoordinateImpl(1,1);
        try {
            board.setShip(ship, Orientation.HORIZONTAL, coordinate1);
        }catch (FieldException e){}
        Coordinate coordinate2=new CoordinateImpl(3,1);
        board.removeShip(coordinate2);
        try {
            Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(coordinate1));
        }catch (Exception e){}
    }

    /**
     *tests if attack is registered correctly
     */
    @Test
    public void registerHit(){
        Board board=new Board();
        Ship ship=new Destroyer();
        Coordinate coordinate1=new CoordinateImpl(1,1);
        try {
            board.setShip(ship, Orientation.HORIZONTAL, coordinate1);
        }catch (FieldException e){}
        Coordinate coordinate2=new CoordinateImpl(1,2);
        try {
            Assert.assertTrue(
                    board.receiveAttack(coordinate2) == FieldStatus.HIT
                            && board.getFieldStatus(coordinate2) == FieldStatus.HIT
            );
        }catch(FieldException e){}
    }

    /**
     *tests if attack is registered correctly
     */
    @Test
    public void registerSunk(){
        Board board=new Board();
        Ship ship=new Destroyer();
        Coordinate coordinate1=new CoordinateImpl(1,1);
        try {
            board.setShip(ship, Orientation.HORIZONTAL, coordinate1);
        }catch (FieldException e){}
        Coordinate coordinate2=new CoordinateImpl(2,1);
        board.receiveAttack(coordinate1);
        board.receiveAttack(coordinate2);
        try {
            Assert.assertEquals(board.getFieldStatus(coordinate2), FieldStatus.SUNK);
        }catch (FieldException e){}
    }

    /**
     * tests if Ship is registered as set
     */
    @Test
    public void getShipField(){
        Board board = new Board();
        Ship ship=new Destroyer();
        Coordinate coordinate1=new CoordinateImpl(1,1);
        Coordinate coordinate2=new CoordinateImpl(2,1);
        try {
            board.setShip(ship, Orientation.HORIZONTAL, coordinate1);
        }catch (Exception e){}
        try {
            Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(coordinate2));
        } catch(Exception e){}
    }
}