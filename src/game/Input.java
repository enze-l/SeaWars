package game;

import jdk.swing.interop.SwingInterOpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Input {
    public static void input(MyBoard board){
        InputStreamReader userInput= new InputStreamReader(System.in);
        BufferedReader userInputBuffer=new BufferedReader(userInput);
        String commandString=null;
        String command=null;
        String parameters=null;
        try {
            commandString = userInputBuffer.readLine().trim().toUpperCase();
            command = commandString.substring(0,commandString.indexOf(' '));
            parameters=commandString.substring(commandString.indexOf(' ')+1).replaceAll("\\s+","");
        }catch (Exception e){
            System.err.println("StringError");
        }
        if (commandString.length()<1) System.err.println("not working!");
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
                    }
                    break;
                case READY:
                    switch (command) {
                        case "LEGEND":
                            //legend needs to get implemented
                            System.out.println("legend");
                            break;
                        case "REVOKE":
                            //main menu has to get implemented
                            System.out.println("revoke");
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
                    }
                    break;
            }
        }catch (StatusException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void set(MyBoard board, String parameters){
        char chosenShip=parameters.charAt(0);
        char letter=parameters.charAt(1);
        ShipType shipType=null;
        switch (chosenShip){
            case ('B'):
                shipType=ShipType.BATTLESHIP;
                break;
            case ('C'):
                shipType=ShipType.CRUISER;
                break;
            case ('S'):
                shipType=ShipType.SUBMARINE;
                break;
            case ('D'):
                shipType=ShipType.DESTROYER;
                break;
        }
        int yCoordinate=0;
        switch (letter){
            case ('A'):
                yCoordinate=1;
                break;
            case ('B'):
                yCoordinate=2;
                break;
            case ('C'):
                yCoordinate=3;
                break;
            case ('D'):
                yCoordinate=4;
                break;
            case ('E'):
                yCoordinate=5;
                break;
            case ('F'):
                yCoordinate=6;
                break;
            case ('G'):
                yCoordinate=7;
                break;
            case ('H'):
                yCoordinate=8;
                break;
            case ('I'):
                yCoordinate=9;
                break;
            case ('J'):
                yCoordinate=10;
                break;
        }
        int xCoordinate=Character.getNumericValue(parameters.charAt(2));
        char direction=parameters.charAt(3);
        Orientation orientation=null;
        switch (direction){
            case ('N'):
            case ('S'):
                orientation=Orientation.VERTICAL;
                break;
            case ('W'):
            case ('E'):
                orientation=Orientation.HORIZONTAL;
                break;
        }
        try {
            board.setShip(shipType, new CoordinateImpl(xCoordinate, yCoordinate), orientation);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private static void removeShip(MyBoard board, String parameters){
        char letter=parameters.charAt(0);
        int yCoordinate=0;
        switch (letter){
            case ('A'):
                yCoordinate=1;
                break;
            case ('B'):
                yCoordinate=2;
                break;
            case ('C'):
                yCoordinate=3;
                break;
            case ('D'):
                yCoordinate=4;
                break;
            case ('E'):
                yCoordinate=5;
                break;
            case ('F'):
                yCoordinate=6;
                break;
            case ('G'):
                yCoordinate=7;
                break;
            case ('H'):
                yCoordinate=8;
                break;
            case ('I'):
                yCoordinate=9;
                break;
            case ('J'):
                yCoordinate=10;
                break;
        }
        int xCoordinate=Character.getNumericValue(parameters.charAt(1));
        try {
            board.removeShip(new CoordinateImpl(xCoordinate, yCoordinate));
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
