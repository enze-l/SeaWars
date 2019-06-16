package tests;

import boards.*;
import boards.coordinates.*;
import boards.fields.*;
import exceptions.*;
import input.Input;
import org.junit.Assert;
import org.junit.Test;
import gameModules.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class IOTest {

    @Test
    public void setShip() {
        GameInstance.newGame();
        Input i=new Input();
        try {
            ByteArrayInputStream input = new ByteArrayInputStream("set c a 1 s\n".getBytes());
            System.setIn(input);
            i.command();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            Assert.assertEquals(FieldStatus.SETSHIP, GameInstance.getPlayerBoard().getFieldStatus(new CoordinateImpl(1, 1)));
        } catch (FieldException ignored) {
        }
    }

    @Test
    public void setShipWithMoreWhiteSpace() {
        GameInstance.newGame();
        Input i=new Input();
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(" set c a  1 s\n".getBytes());
            System.setIn(input);
            i.command();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            Assert.assertEquals(FieldStatus.SETSHIP, GameInstance.getPlayerBoard().getFieldStatus(new CoordinateImpl(1, 1)));
        } catch (FieldException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = InputException.class)
    public void setShipTooMuchArguments() throws InputException {
        GameInstance.newGame();
        Input i=new Input();
        try {
            ByteArrayInputStream input = new ByteArrayInputStream((" set c a 1 s n\n").getBytes());
            System.setIn(input);
            i.command();
        } catch (IOException | StatusException | FieldException | ShipException|DisplayException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test(expected = InputException.class)
    public void setShipNoSpacing() throws InputException {
        GameInstance.newGame();
        Input i=new Input();
        try {
            ByteArrayInputStream input = new ByteArrayInputStream("setca1s\n".getBytes());
            System.setIn(input);
            i.command();
        } catch (IOException | StatusException | FieldException | ShipException|DisplayException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test(expected = InputException.class)
    public void setShipToFewCommands() throws InputException {
        GameInstance.newGame();
        Input i=new Input();
        try {
            ByteArrayInputStream input = new ByteArrayInputStream("set c a 1\n".getBytes());
            System.setIn(input);
            i.command();
        } catch (IOException | StatusException | FieldException | ShipException|DisplayException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test(expected = FieldException.class)
    public void setShipOutside() throws FieldException {
        GameInstance.newGame();
        Input i=new Input();
        try {
            ByteArrayInputStream input = new ByteArrayInputStream("set c i 1 s\n".getBytes());
            System.setIn(input);
            i.command();
        } catch (StatusException | InputException | IOException | ShipException|DisplayException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * checks if ship gets set at the right location and is turned horizontal
     */
    @Test
    public void setBattleshipHorizontal() {
        GameInstance.newGame();
        Input i=new Input();
        Coordinate coordinate0 = new CoordinateImpl(1, 2);
        Coordinate coordinate1 = new CoordinateImpl(2, 2);
        Coordinate coordinate2 = new CoordinateImpl(3, 2);
        Coordinate coordinate3 = new CoordinateImpl(4, 2);
        Coordinate coordinate4 = new CoordinateImpl(5, 2);
        Coordinate coordinate5 = new CoordinateImpl(6, 2);
        Coordinate coordinate6 = new CoordinateImpl(7, 2);
        try {
            ByteArrayInputStream input = new ByteArrayInputStream("set b b 2 e\n".getBytes());
            System.setIn(input);
            i.command();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            Assert.assertTrue(GameInstance.getPlayerBoard().getFieldStatus(coordinate0) == FieldStatus.WATER
                            && GameInstance.getPlayerBoard().getFieldStatus(coordinate1) == FieldStatus.SETSHIP
                            && GameInstance.getPlayerBoard().getFieldStatus(coordinate2) == FieldStatus.SETSHIP
                            && GameInstance.getPlayerBoard().getFieldStatus(coordinate3) == FieldStatus.SETSHIP
                            && GameInstance.getPlayerBoard().getFieldStatus(coordinate4) == FieldStatus.SETSHIP
                            && GameInstance.getPlayerBoard().getFieldStatus(coordinate5) == FieldStatus.SETSHIP
                            && GameInstance.getPlayerBoard().getFieldStatus(coordinate6) == FieldStatus.WATER
            );
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void setDestroyerVertically() {
        GameInstance.newGame();
        Input i=new Input();
        Coordinate coordinate0 = new CoordinateImpl(2, 1);
        Coordinate coordinate1 = new CoordinateImpl(2, 2);
        Coordinate coordinate2 = new CoordinateImpl(2, 3);
        Coordinate coordinate3 = new CoordinateImpl(2, 4);
        try {
            ByteArrayInputStream input = new ByteArrayInputStream("set d b 2 s\n".getBytes());
            System.setIn(input);
            i.command();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            Assert.assertTrue(GameInstance.getPlayerBoard().getFieldStatus(coordinate0) == FieldStatus.WATER
                            && GameInstance.getPlayerBoard().getFieldStatus(coordinate1) == FieldStatus.SETSHIP
                            && GameInstance.getPlayerBoard().getFieldStatus(coordinate2) == FieldStatus.SETSHIP
                            && GameInstance.getPlayerBoard().getFieldStatus(coordinate3) == FieldStatus.WATER
            );
        } catch (FieldException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void allShipsSet() {
        GameInstance.newGame();
        Input i=new Input();
        try {
            StringReader input = new StringReader(
                    "set b a 1 e\n" +
                            "set c c 1 e\n" +
                            "set c e 1 e\n" +
                            "set s g 1 e\n" +
                            "set s i 1 e\n" +
                            "set s a 8 e\n" +
                            "set d c 8 e\n" +
                            "set d e 8 e\n" +
                            "set d g 8 e\n" +
                            "set d i 8 e\n");
            BufferedReader reader = new BufferedReader(input);
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                    System.setIn(byteArray);
                    i.command();
                }
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        int[] expected = {0, 0, 0, 0};
        int[] actual = GameInstance.getPlayerBoard().shipsAvailable();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = ShipException.class)
    public void tooMuchShips() throws ShipException {
        GameInstance.newGame();
        Input i=new Input();
        StringReader input = new StringReader(
                "set b a 1 e\n" +
                        "set c c 1 e\n" +
                        "set c e 1 e\n" +
                        "set s g 1 e\n" +
                        "set s i 1 e\n" +
                        "set s a 8 e\n" +
                        "set d c 8 e\n" +
                        "set d e 8 e\n" +
                        "set d g 8 e\n" +
                        "set d i 8 e\n" +
                        "set d j 4 e");
        BufferedReader reader = new BufferedReader(input);
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                System.setIn(byteArray);
                i.command();
            }
        } catch (StatusException | InputException | FieldException | IOException|DisplayException ignored) {
        }
    }

    @Test(expected = ShipException.class)
    public void removeNoShip() throws ShipException {
        PlayerBoard board = new PlayerBoardImpl();
        Coordinate coordinate = new CoordinateImpl(1, 1);
        try {
            board.removeShip(coordinate);
        } catch (FieldException|DisplayException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void removeShip() {
        GameInstance.newGame();
        Input i=new Input();
        try {
            StringReader input = new StringReader(
                    "set b a 1 e\n" +
                            "remove a 1\n");
            BufferedReader reader = new BufferedReader(input);
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                    System.setIn(byteArray);
                    i.command();
                }
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            Assert.assertEquals(FieldStatus.WATER, GameInstance.getPlayerBoard().getFieldStatus(new CoordinateImpl(1, 1)));
        } catch (FieldException ignored) {
        }
    }

    @Test(expected = InputException.class)
    public void removeShipToFewArguments() throws InputException {
        GameInstance.newGame();
        Input i=new Input();
        StringReader input = new StringReader(
                "set b a 1 e\n" +
                        "remove a\n");
        BufferedReader reader = new BufferedReader(input);
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                System.setIn(byteArray);
                i.command();
            }
        } catch (StatusException | ShipException | FieldException | IOException|DisplayException ignored) { }
    }

    @Test(expected = InputException.class)
    public void removeShipToMuchArguments() throws InputException {
        GameInstance.newGame();
        Input i=new Input();
        StringReader input = new StringReader(
                "set b a 1 e\n" +
                        "remove a 1 b\n");
        BufferedReader reader = new BufferedReader(input);
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                System.setIn(byteArray);
                i.command();
            }
        } catch (StatusException | ShipException | FieldException | IOException|DisplayException ignored) { }
    }

    @Test(expected = InputException.class)
    public void removeShipWrongInformation() throws InputException {
        GameInstance.newGame();
        Input i=new Input();
        StringReader input = new StringReader(
                "set b a 1 e\n" +
                        "remove 1 1 \n");
        BufferedReader reader = new BufferedReader(input);
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                System.setIn(byteArray);
                i.command();
            }
        } catch (StatusException | ShipException | FieldException | IOException|DisplayException ignored) { }
    }

    @Test(expected = InputException.class)
    public void giberish() throws InputException {
        GameInstance.newGame();
        Input i=new Input();
        StringReader input = new StringReader(
                "asdf b a 1 e\n");
        BufferedReader reader = new BufferedReader(input);
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                System.setIn(byteArray);
                i.command();
            }
        } catch (StatusException | ShipException | FieldException | IOException|DisplayException ignored) { }
    }

    @Test
    public void getReady() {
        GameInstance.newGame();
        Input i=new Input();
        try {
            StringReader input = new StringReader(
                    "set b a 1 e\n" +
                            "set c c 1 e\n" +
                            "set c e 1 e\n" +
                            "set s g 1 e\n" +
                            "set s i 1 e\n" +
                            "set s a 8 e\n" +
                            "set d c 8 e\n" +
                            "set d e 8 e\n" +
                            "set d g 8 e\n" +
                            "set d i 8 e\n" +
                            "ready\n");
            BufferedReader reader = new BufferedReader(input);
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                    System.setIn(byteArray);
                    i.command();
                }
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            Assert.assertEquals(GameInstance.getPlayerBoard().getGameStatus(), GameStatus.READY);
        }catch (StatusException ignored){}
    }

    @Test (expected = InputException.class)
    public void toEarlyReady()throws InputException {
        GameInstance.newGame();
        Input i=new Input();
        try {
            StringReader input = new StringReader(
                    "set b a 1 e\n" +
                            "set c c 1 e\n" +
                            "set c e 1 e\n" +
                            "set s g 1 e\n" +
                            "set s i 1 e\n" +
                            "set s a 8 e\n" +
                            "set d c 8 e\n" +
                            "set d e 8 e\n" +
                            "set d g 8 e\n" +
                            "ready\n");
            BufferedReader reader = new BufferedReader(input);
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                    System.setIn(byteArray);
                    i.command();
                }
            } catch (FieldException|ShipException|DisplayException ignored) {
            }
        } catch (StatusException|IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            Assert.assertEquals(GameInstance.getPlayerBoard().getGameStatus(), GameStatus.READY);
        }catch (StatusException ignored){}
    }

    //doesn't work without connected board anymore
//    @Test
//    public void revokeReady() {
//        GameInstance.newGame();
//        Input i=new Input();
//        try {
//            StringReader input = new StringReader(
//                    "set b a 1 e\n" +
//                            "set c c 1 e\n" +
//                            "set c e 1 e\n" +
//                            "set s g 1 e\n" +
//                            "set s i 1 e\n" +
//                            "set s a 8 e\n" +
//                            "set d c 8 e\n" +
//                            "set d e 8 e\n" +
//                            "set d g 8 e\n" +
//                            "set d i 8 e\n" +
//                            "ready\n" +
//                            "revoke\n");
//            BufferedReader reader = new BufferedReader(input);
//            try {
//                //noinspection InfiniteLoopStatement
//                while (true) {
//                    ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
//                    System.setIn(byteArray);
//                    i.command();
//                }
//            } catch (Exception ignored) { }
//
//        } catch (Exception e) {
//            System.out.println(e.getLocalizedMessage());
//        }
//        try {
//            Assert.assertEquals(GameStatus.PREPARATION, GameInstance.getPlayerBoard().getGameStatus());
//        }catch (StatusException ignored){}
//    }

    @Test (expected = InputException.class)
    public void revokeWithoutReady()throws InputException {
        GameInstance.newGame();
        Input i=new Input();
        try {
            StringReader input = new StringReader(
                    "set b a 1 e\n" +
                            "set c c 1 e\n" +
                            "set c e 1 e\n" +
                            "set s g 1 e\n" +
                            "set s i 1 e\n" +
                            "set s a 8 e\n" +
                            "set d c 8 e\n" +
                            "set d e 8 e\n" +
                            "set d g 8 e\n" +
                            "revoke\n");
            BufferedReader reader = new BufferedReader(input);
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    ByteArrayInputStream byteArray = new ByteArrayInputStream(reader.readLine().getBytes());
                    System.setIn(byteArray);
                    i.command();
                }
            } catch (FieldException|ShipException|DisplayException ignored) {
            }
        } catch (StatusException|IOException e) {
            System.out.println(e.getLocalizedMessage());
        }
        try {
            Assert.assertEquals(GameInstance.getPlayerBoard().getGameStatus(), GameStatus.PREPARATION);
        }catch (StatusException ignored){}
    }

}
