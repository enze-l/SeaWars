package input;

import boards.coordinates.*;
import boards.*;
import output.OutputSymbols;
import boards.ships.*;
import exceptions.*;
import gameModules.*;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Input {
    private PlayerBoard playerBoard;
    private EnemyBoard enemyBoard;

    public Input() {
        this.playerBoard = GameInstance.getPlayerBoard();
        this.enemyBoard = GameInstance.getEnemyBoard();
    }

    public void command()
            throws IOException, StatusException, InputException, FieldException, ShipException {
        InputStreamReader userInput = new InputStreamReader(System.in);
        BufferedReader userInputBuffer = new BufferedReader(userInput);
        String[] commandString = userInputBuffer.readLine().trim().toUpperCase().split(" +");
        String command = commandString[0];
        String[] parameters = Arrays.copyOfRange(commandString, 1, commandString.length);
        if (commandString[0].equals("")) System.err.println("You have to give me some command!");
        switch (command) {
            case "LEGEND":
                //legend needs to get implemented
                System.out.println("legend");
                break;
            case "SET":
                set(parameters);
                break;
            case "REMOVE":
                removeShip(parameters);
                break;
            case "READY":
                ready();
                break;
            case "REVOKE":
                revoke();
                break;
            case "SHOOT":
                shoot(parameters);
                break;
            case "CONTINUE":
                //Successive rounds have to be implemented
                System.out.println("continue");
                break;
            case "LEAVE":

                break;
            default:
                throw new InputException("command not available!");
        }
    }


    private void revoke() throws IOException, StatusException, InputException {
        if (playerBoard.getStatus() == GameStatus.READY
                && enemyBoard.getStatus() == GameStatus.PREPARATION) {
            playerBoard.setStatus(GameStatus.PREPARATION);
            CommunicationInstance.getOUTPUT().writeUTF("revoke");
        } else if (playerBoard.getStatus() == GameStatus.PREPARATION) {
            throw new InputException("You are not ready yet!");
        } else {
            throw new InputException("You cannot reverse your status anymore");
        }
    }

    private void ready() throws StatusException, InputException, IOException {
        if (playerBoard.allShipsSet()) {
            playerBoard.setStatus(GameStatus.READY);
            CommunicationInstance.getOUTPUT().writeUTF("ready");
            if (enemyBoard.getStatus() == GameStatus.READY
                    && playerBoard.getStatus() == GameStatus.READY
                    && CommunicationInstance.isIsServer()) {
                int randomStart = ThreadLocalRandom.current().nextInt(0, 1 + 1);
                if (randomStart == 0) {
                    enemyBoard.setStatus(GameStatus.ATTACK);
                    playerBoard.setStatus(GameStatus.RECEIVE);
                    CommunicationInstance.getOUTPUT().writeUTF("attack");
                } else {
                    enemyBoard.setStatus(GameStatus.RECEIVE);
                    playerBoard.setStatus(GameStatus.ATTACK);
                    CommunicationInstance.getOUTPUT().writeUTF("receive");
                }
            }
        } else throw new InputException("You first have to get all ships into position!");
    }

    private void shoot(String[] parameters) throws IOException, StatusException, InputException {
        if (playerBoard.getStatus() == GameStatus.ATTACK) {
            char yCoordinate = parameters[0].charAt(0);
            int xCoordinate = Integer.parseInt(parameters[1]);
            CommunicationInstance.getOUTPUT().writeUTF("shoot");
            CommunicationInstance.getOUTPUT().writeChar(yCoordinate);
            CommunicationInstance.getOUTPUT().writeInt(xCoordinate);
            CommunicationInstance.setLastShot(new CoordinateImpl(xCoordinate, OutputSymbols.getNumber(yCoordinate)));
        } else throw new InputException("It is not your turn to shoot yet!");
    }

    private void set(String[] parameters) throws InputException, FieldException, ShipException {
        if (parameters.length < 4) throw new InputException("To few information for the position!");
        if (parameters.length > 4) throw new InputException("To much information for the position!");
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
        int yCoordinate = OutputSymbols.getNumber(letter.charAt(0));
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
        this.playerBoard.setShip(shipType, new CoordinateImpl(xCoordinate, yCoordinate), orientation);

    }

    private void removeShip(String[] parameters) throws InputException, FieldException, ShipException {
        if (parameters.length < 2) throw new InputException("To few information for the position!");
        if (parameters.length > 2) throw new InputException("To much information for the position!");
        String letter = parameters[0];
        int yCoordinate = OutputSymbols.getNumber(letter.charAt(0));
        int xCoordinate = Integer.parseInt(parameters[1]);
        this.playerBoard.removeShip(new CoordinateImpl(xCoordinate, yCoordinate));
    }
}