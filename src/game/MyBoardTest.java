package game;

import org.junit.Assert;
import org.junit.Test;

public class MyBoardTest {

    /**
     * checks for the ability to create a Board
     */
    @Test
    public void createBoard(){
        MyBoard board = new Board();
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
        MyBoard board = new Board();
        try {
            Assert.assertEquals(GameStatus.PREPARATION, board.getStatus());
        }catch (StatusException e){}
    }

    /**
     * checks if a null Status can be set for the Board. It should be not possible, hence the exception
     */
    @Test(expected = StatusException.class)
    public void setNullStatus() throws StatusException{
        MyBoard board=new Board();
        board.setStatus(null);
    }

    /**
     * checks if GameStatus "READY" can be set and retrieved
     */
    @Test
    public void getReadyStatus(){
        MyBoard board = new Board();
        try {
            board.setStatus(GameStatus.READY);
        }catch (StatusException e){}
        try {
            Assert.assertEquals(GameStatus.READY, board.getStatus());
        }catch (StatusException e){}
    }

    /**
     * checks if GameStatus "ATTACK" can be set and retrieved
     */
    @Test
    public void getAttackStatus(){
        MyBoard board = new Board();
        try{
            board.setStatus(GameStatus.ATTACK);
        }catch (StatusException e){}
        try {
            Assert.assertEquals(GameStatus.ATTACK, board.getStatus());
        }catch (StatusException e){}
    }

    /**
     * checks if GameStatus "RECEIVE" can be set and retrieved
     */
    @Test
    public void getReceiveStatus(){
        MyBoard board = new Board();
        try{
            board.setStatus(GameStatus.RECEIVE);
        }catch (StatusException e){}
        try {
            Assert.assertEquals(GameStatus.RECEIVE, board.getStatus());
        }catch (StatusException e){}
    }

    /**
     * checks if GameStatus "OVER" can be set and retrieved
     */
    @Test
    public void getOverStatus(){
        MyBoard board = new Board();
        try{
            board.setStatus(GameStatus.OVER);
        }catch (StatusException e){}
        try {
            Assert.assertEquals(GameStatus.OVER, board.getStatus());
        }catch (StatusException e){}
    }

    /**
     * checks if a Field gets the Status "WATER" assigned automatically
     */
    @Test
    public void fieldStatusWater(){
        MyBoard board=new Board();
        Coordinate coordinate=new CoordinateImpl(1,1);
        try {
            Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(coordinate));
        }catch(FieldException e){}
    }

    @Test(expected = FieldException.class)
    public void noField() throws FieldException {
        MyBoard board=new Board();
        Coordinate coordinate=new CoordinateImpl(0,0);
        board.getFieldStatus(coordinate);
    }

    /**
     *checks if ship gets set at the right location and is turned horizontal
     */
    @Test
    public void setBattleshipHorizontal(){
        MyBoard board=new Board();
        Coordinate coordinate0=new CoordinateImpl(1,2);
        Coordinate coordinate1=new CoordinateImpl(2,2);
        Coordinate coordinate2=new CoordinateImpl(3,2);
        Coordinate coordinate3=new CoordinateImpl(4,2);
        Coordinate coordinate4=new CoordinateImpl(5,2);
        Coordinate coordinate5=new CoordinateImpl(6,2);
        Coordinate coordinate6=new CoordinateImpl(7,2);
        try {
            board.setShip(ShipType.BATTLESHIP, coordinate1, Orientation.HORIZONTAL);
        }catch (SeaWarException e){}
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
    public void setBattleshipVertically(){
        MyBoard board=new Board();
        Coordinate coordinate0=new CoordinateImpl(2,1);
        Coordinate coordinate1=new CoordinateImpl(2,2);
        Coordinate coordinate2=new CoordinateImpl(2,3);
        Coordinate coordinate3=new CoordinateImpl(2,4);
        Coordinate coordinate4=new CoordinateImpl(2,5);
        Coordinate coordinate5=new CoordinateImpl(2,6);
        Coordinate coordinate6=new CoordinateImpl(2,7);
        try {
            board.setShip(ShipType.BATTLESHIP, coordinate1, Orientation.VERTICAL);
        } catch (SeaWarException e){}
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

    @Test
    public void setDestroyerVertically(){
        MyBoard board=new Board();
        Coordinate coordinate0=new CoordinateImpl(2,1);
        Coordinate coordinate1=new CoordinateImpl(2,2);
        Coordinate coordinate2=new CoordinateImpl(2,3);
        Coordinate coordinate3=new CoordinateImpl(2,4);
        try {
            board.setShip(ShipType.DESTROYER, coordinate1, Orientation.VERTICAL);
        } catch (SeaWarException e){}
        try {
            Assert.assertTrue(
                    board.getFieldStatus(coordinate0) == FieldStatus.WATER
                            && board.getFieldStatus(coordinate1) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate2) == FieldStatus.SHIP
                            && board.getFieldStatus(coordinate3) == FieldStatus.WATER
            );
        }catch (FieldException e){}
    }

    /**
     * Tests if on an empty board all ships are registered as available
     */
    @Test
    public void allShipsAvailable(){
        MyBoard board=new Board();
        int[] expected={1,2,3,4};
        int[] actual=board.shipsAvailable();
        Assert.assertArrayEquals(expected, actual);
    }

    /**
     * Test if all ships are registered as set
    */
    @Test
    public void allShipsSet(){
        MyBoard board=new Board();
        try {
            board.setShip(ShipType.BATTLESHIP, new CoordinateImpl(1,1), Orientation.HORIZONTAL);
            board.setShip(ShipType.CRUISER, new CoordinateImpl(1,3), Orientation.HORIZONTAL);
            board.setShip(ShipType.CRUISER, new CoordinateImpl(1,5), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(1,7), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(1,9), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(8,1), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8,3), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8,5), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8,7), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8,9), Orientation.HORIZONTAL);
        }catch (SeaWarException e){}
        int[] expected={0,0,0,0};
        int[] actual=board.shipsAvailable();
        Assert.assertArrayEquals(expected, actual);
        //
    }

    /**
     * Tests if Exception is thrown in the case of trying to set too much ships on the board
     */
    @Test(expected = ShipException.class)
    public void tooMuchShips() throws ShipException{
        MyBoard board=new Board();
        try {
            board.setShip(ShipType.BATTLESHIP, new CoordinateImpl(1,1), Orientation.HORIZONTAL);
            board.setShip(ShipType.CRUISER, new CoordinateImpl(1,3), Orientation.HORIZONTAL);
            board.setShip(ShipType.CRUISER, new CoordinateImpl(1,5), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(1,7), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(1,9), Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, new CoordinateImpl(8,1), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8,3), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8,5), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8,7), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(8,9), Orientation.HORIZONTAL);
            board.setShip(ShipType.DESTROYER, new CoordinateImpl(4,9), Orientation.HORIZONTAL);
        }catch (FieldException e){}
    }

    /**
     * test for throw of exception if ship is set outside of Board
     */
    @Test(expected = FieldException.class)
    public void setShipOutside()throws FieldException{
        MyBoard board=new Board();
        Coordinate coordinate=new CoordinateImpl(0,0);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate, Orientation.HORIZONTAL);
        }catch (ShipException e){}
    }

    /**
     * checks if Ship can be set on another ship
     */
    @Test(expected = ShipException.class)
    public void setShipOnShip()throws ShipException{
        MyBoard board=new Board();
        Coordinate coordinate1=new CoordinateImpl(2,2);
        Coordinate coordinate2=new CoordinateImpl(4,2);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate1, Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, coordinate2, Orientation.HORIZONTAL);
        }catch (FieldException e){}
    }

    /**
     * checks if ship can be set directly next to another ship
     */
    @Test(expected = ShipException.class)
    public void setShipNextToShip()throws ShipException{
        MyBoard board=new Board();
        Coordinate coordinate1=new CoordinateImpl(2,2);
        Coordinate coordinate2=new CoordinateImpl(3,3);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate1, Orientation.HORIZONTAL);
            board.setShip(ShipType.SUBMARINE, coordinate2, Orientation.HORIZONTAL);
        }catch (FieldException e){}
    }

    /**
     * test for throw of exception if ship is set outside of Board
     */
    @Test(expected = FieldException.class)
    public void setShipOnBoarder()throws FieldException{
        MyBoard board=new Board();
        Coordinate coordinate=new CoordinateImpl(9,1);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate, Orientation.HORIZONTAL);
        }catch (ShipException e){}
    }

    /**
     * test if ship can be set near the boarder of the field
     */
    @Test
    public void setShipNearBoarder(){
        MyBoard board=new Board();
        Coordinate coordinate=new CoordinateImpl(9,1);
        try {
            board.setShip(ShipType.DESTROYER, coordinate, Orientation.HORIZONTAL);
        }catch (SeaWarException e){}
        try {
            Assert.assertTrue(
                    board.getFieldStatus(new CoordinateImpl(8,1)) == FieldStatus.WATER
                            && board.getFieldStatus(new CoordinateImpl(9,1)) == FieldStatus.SHIP
                            && board.getFieldStatus(new CoordinateImpl(10,1)) == FieldStatus.SHIP
                            && board.getFieldStatus(new CoordinateImpl(10,2)) == FieldStatus.WATER
            );
        }catch (FieldException e){}
    }
    /**
     * test if ship can be removed in case there is none
     */
    @Test(expected = ShipException.class)
    public void removeNoShip() throws ShipException{
        MyBoard board=new Board();
        Coordinate coordinate=new CoordinateImpl(1,1);
        try {
            board.removeShip(coordinate);
        }catch (FieldException e){}
    }

    /**
     * test if ship can be removed in case there is none
     */
    @Test(expected = FieldException.class)
    public void removeShipOutsideBoard()throws FieldException{
        MyBoard board=new Board();
        Coordinate coordinate=new CoordinateImpl(0,0);
        try {
            board.removeShip(coordinate);
        }catch (ShipException e){}
    }

    /**
     * test if ship can be removed
     */
    @Test
    public void removeShip(){
        MyBoard board=new Board();
        Coordinate coordinate1=new CoordinateImpl(1,2);
        try {
            board.setShip(ShipType.SUBMARINE, coordinate1, Orientation.HORIZONTAL);
        } catch (SeaWarException e) { }
        Coordinate coordinate2 = new CoordinateImpl(3, 2);
        try {
            board.removeShip(coordinate2);
            Assert.assertEquals(FieldStatus.WATER, board.getFieldStatus(coordinate1));
        } catch (SeaWarException e) { }
    }

    /**
     *tests if attack is registered correctly
     */
    @Test
    public void registerHit(){
        MyBoard board=new Board();
        Coordinate coordinate1=new CoordinateImpl(2,2);
        Coordinate coordinate2=new CoordinateImpl(3,2);
        try {
            board.setShip(ShipType.DESTROYER, coordinate1, Orientation.HORIZONTAL);
        }catch (SeaWarException e){}
        try {
            Assert.assertSame(FieldStatus.HIT, board.receiveAttack(coordinate2));
        }catch (FieldException e){}
    }

    /**
     *tests if attack is registered correctly
     */
    @Test
    public void registerSunk(){
        MyBoard board=new Board();
        Coordinate coordinate1=new CoordinateImpl(1,1);
        try {
            board.setShip(ShipType.DESTROYER, coordinate1, Orientation.HORIZONTAL);
        }catch (SeaWarException e){}
        Coordinate coordinate2=new CoordinateImpl(2,1);
        try {
            board.receiveAttack(coordinate1);
            board.receiveAttack(coordinate2);
        }catch (FieldException e){}
        try {
            Assert.assertEquals(board.getFieldStatus(coordinate2), FieldStatus.SUNK);
        }catch (FieldException e){}
    }

    /**
     * tests if Ship is registered as set
     */
    @Test
    public void getShipField(){
        MyBoard board = new Board();
        Coordinate coordinate1=new CoordinateImpl(1,1);
        Coordinate coordinate2=new CoordinateImpl(2,1);
        try {
            board.setShip(ShipType.DESTROYER, coordinate1, Orientation.HORIZONTAL);
        }catch (SeaWarException e){}
        try {
            Assert.assertEquals(FieldStatus.SHIP, board.getFieldStatus(coordinate2));
        } catch(FieldException e){}
    }

    @Test
    public void getBoardStatus(){
        boolean allWater=true;
        MyBoard board=new Board();
        FieldStatus[][] fieldStatuses=board.getFields();
        for (int column=0; column<fieldStatuses.length; column++){
            for(int row=0; row<fieldStatuses[column].length; row++){
                if(fieldStatuses[column][row]!=FieldStatus.WATER){
                    allWater=false;
                }
            }
        }
        Assert.assertTrue(allWater);
    }
}