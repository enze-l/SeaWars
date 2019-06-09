package input;

import coordinates.*;
import boards.GameStatus;
import boards.PlayerBoard;
import output.OutputSymbols;
import ships.*;
import exceptions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Input {
    public static void gameCommands(PlayerBoard board)
            throws IOException, StatusException, InputException, FieldException, ShipException {
        InputStreamReader userInput = new InputStreamReader(System.in);
        BufferedReader userInputBuffer = new BufferedReader(userInput);
        String[] commandString=userInputBuffer.readLine().trim().toUpperCase().split(" ");
        String command= commandString[0];
        String[] parameters = Arrays.copyOfRange(commandString, 1, commandString.length);
        if (commandString[0].equals("")) System.err.println("You have to give me some command!");
        switch (board.getStatus()) {
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
                    default:
                        throw new InputException("command not available!");
                }
                break;
            case RECEIVE:
                switch (command) {
                    case "LEGEND":
                        //legend needs to get implemented
                        System.out.println("legend");
                        break;
                    case "SHOT":
                        throw new InputException("You have to wait till it is your turn!");
                    default:
                        throw new InputException("command not available!");
                }
                break;
            case OVER:
                switch (command) {
                    case "LEGEND":
                        //legend needs to get implemented
                        System.out.println("legend");
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
    }

    private static void set(PlayerBoard board, String[] parameters) throws InputException, FieldException, ShipException{
        if(parameters.length<4)throw new InputException("To few information for the position!");
        if(parameters.length>4)throw new InputException("To much information for the position!");
        String chosenShip = parameters[0];
        String letter = parameters[1];
        ShipType shipType;
        switch (chosenShip) {
            case ("B"):
                shipType = ShipType.BATTLESHIP;
                break;
            case ("C"):
                shipType = ShipType.CRUISER;
                break;
            case ("S"):
                shipType = ShipType.SUBMARINE;
                break;
            case ("D"):
                shipType = ShipType.DESTROYER;
                break;
            default:
                throw new InputException("We don't have ships of that type!");
        }
        int yCoordinate=OutputSymbols.getNumber(letter.charAt(0));
        int xCoordinate = Integer.parseInt(parameters[2]);
        String direction = parameters[3];
        Orientation orientation;
        switch (direction) {
            case ("N"):
                yCoordinate = yCoordinate - (ShipInfo.getLength(shipType) - 1);
            case ("S"):
                orientation = Orientation.VERTICAL;
                break;
            case ("W"):
                xCoordinate = xCoordinate - (ShipInfo.getLength(shipType) - 1);
            case ("E"):
                orientation = Orientation.HORIZONTAL;
                break;
            default:
                throw new InputException("I don't know that direction!");
        }
        board.setShip(shipType, new CoordinateImpl(xCoordinate, yCoordinate), orientation);

    }

    private static void removeShip(PlayerBoard board, String[] parameters) throws InputException, FieldException, ShipException {
        if(parameters.length<2)throw new InputException("To few information for the position!");
        if(parameters.length>2)throw new InputException("To much information for the position!");
        String letter = parameters[0];
        int yCoordinate= OutputSymbols.getNumber(letter.charAt(0));
        int xCoordinate = Integer.parseInt(parameters[1]);
        board.removeShip(new CoordinateImpl(xCoordinate, yCoordinate));
    }
}