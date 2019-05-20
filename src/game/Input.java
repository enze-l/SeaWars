package game;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.Arrays;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Input {
    public static void input(MyBoard board) throws InputException{
        InputStreamReader userInput= new InputStreamReader(System.in);
        BufferedReader userInputBuffer=new BufferedReader(userInput);
        String[] commandString=null;
        String command=null;
        String[] parameters=null;
        try {
            commandString = userInputBuffer.readLine().trim().toUpperCase().split(" ");
            command = commandString[0];
            parameters=Arrays.copyOfRange(commandString, 1, 5);
        }catch (Exception e){
            System.err.println("This is not a valid command!");
        }
        if (commandString[0]=="") System.err.println("You have to give me some command!");
        try {
            switch (board.getStatus()){
                case PREPARATION:
                    switch (command) {
                        case "LEGEND":
                            //legend needs to get implemented
                            System.out.println("legend");
                            break;
                        case "SET":
                            set(board, parameters);
                            break;
                        case "REMOVE":
                            removeShip(board, parameters);
                            break;
                        case "READY":
                            if (board.allShipsSet()) {
                                board.setStatus(GameStatus.READY);
                            } else throw new InputException("You first have to get all ships into position!");
                            break;
                        default:
                            throw new InputException("command not available!");
                    }
                    break;
                case READY:
                    switch (command) {
                        case "LEGEND":
                            //legend needs to get implemented
                            System.out.println("legend");
                            break;
                        case "REVOKE":
                            board.setStatus(GameStatus.PREPARATION);
                            System.out.println("revoke");
                            break;
                        case "SET":
                        case "REMOVE":
                            throw new InputException("You have to revoke your status first in order to do that");
                        default:
                            throw new InputException("command not available!");
                    }
                    break;
                case ATTACK:
                    switch (command) {
                        case "LEGEND":
                            //legend needs to get implemented
                            System.out.println("legend");
                            break;
                        case "SHOT":
                            //shot has to be implemented
                            System.out.println("shot");
                            break;
                        case "LAST":
                            //history has to be implemented
                            System.out.println("last");
                            break;
                        default:
                            throw new InputException("command not available!");
                    }
                    break;
                case RECEIVE:
                    switch (command){
                        case "LEGEND":
                            //legend needs to get implemented
                            System.out.println("legend");
                            break;
                        case "LAST":
                            //history has to be implemented
                            System.out.println("last");
                            break;
                        case "SHOT":
                            throw  new InputException("You have to wait till it is your turn!");
                        default:
                            throw new InputException("command not available!");
                    }
                    break;
                case OVER:
                    switch (command){
                        case "LEGEND":
                            //legend needs to get implemented
                            System.out.println("legend");
                            break;
                        case "LAST":
                            //history has to be implemented
                            System.out.println("last");
                            break;
                        case "CONTINUE":
                            //Successive rounds have to be implemented
                            System.out.println("continue");
                            break;
                        default:
                            throw new InputException("command not available!");
                    }
                    break;
            }
        }catch (StatusException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void set(MyBoard board, String[] parameters)throws InputException{
        String chosenShip=parameters[0];
        String letter=parameters[1];
        ShipType shipType=null;
        switch (chosenShip){
            case ("B"):
                shipType=ShipType.BATTLESHIP;
                break;
            case ("C"):
                shipType=ShipType.CRUISER;
                break;
            case ("S"):
                shipType=ShipType.SUBMARINE;
                break;
            case ("D"):
                shipType = ShipType.DESTROYER;
                break;
            default:
                throw new InputException("We don't have ships of that type!");
        }
        int yCoordinate = 0;
        switch (letter) {
            case ("A"):
                yCoordinate=1;
                break;
            case ("B"):
                yCoordinate=2;
                break;
            case ("C"):
                yCoordinate=3;
                break;
            case ("D"):
                yCoordinate=4;
                break;
            case ("E"):
                yCoordinate=5;
                break;
            case ("F"):
                yCoordinate=6;
                break;
            case ("G"):
                yCoordinate=7;
                break;
            case ("H"):
                yCoordinate=8;
                break;
            case ("I"):
                yCoordinate=9;
                break;
            case ("J"):
                yCoordinate=10;
                break;
            default:
                throw new InputException("Were do you wan to go again?");
        }
        int xCoordinate=Integer.parseInt(parameters[2]);
        String direction=parameters[3];
        Orientation orientation=null;
        switch (direction){
            case ("N"):
                yCoordinate=yCoordinate-(ShipInfo.getLength(shipType)-1);
            case ("S"):
                orientation=Orientation.VERTICAL;
                break;
            case ("W"):
                xCoordinate=xCoordinate-(ShipInfo.getLength(shipType)-1);
            case ("E"):
                orientation=Orientation.HORIZONTAL;
                break;
            default:
                throw new InputException("I don't know that direction!");
        }
        try {
            board.setShip(shipType, new CoordinateImpl(xCoordinate, yCoordinate), orientation);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static void removeShip(MyBoard board, String[] parameters)throws InputException{
        String letter=parameters[0];
        int yCoordinate=0;
        switch (letter){
            case ("A"):
                yCoordinate=1;
                break;
            case ("B"):
                yCoordinate=2;
                break;
            case ("C"):
                yCoordinate=3;
                break;
            case ("D"):
                yCoordinate=4;
                break;
            case ("E"):
                yCoordinate=5;
                break;
            case ("F"):
                yCoordinate=6;
                break;
            case ("G"):
                yCoordinate=7;
                break;
            case ("H"):
                yCoordinate=8;
                break;
            case ("I"):
                yCoordinate=9;
                break;
            case ("J"):
                yCoordinate=10;
                break;
            default:
                throw new InputException("Were do you wan to go again?");
        }
        int xCoordinate=Integer.parseInt(parameters[1]);
        try {
            board.removeShip(new CoordinateImpl(xCoordinate, yCoordinate));
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
