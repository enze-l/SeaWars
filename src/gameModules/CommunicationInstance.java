package gameModules;

import boards.*;
import boards.fields.FieldStatus;
import connection.Connection;
import coordinates.*;
import exceptions.*;
import output.OutputSymbols;

import java.awt.*;
import java.io.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class CommunicationInstance extends Thread {
    private static Connection CONNECTION;
    private static OutputStream OUTPUT;
    private static BufferedReader INPUT_BUFFER;
    private static int REFRESH_RATE;
    private static boolean CONNECTION_IN_USE;

    private CommunicationInstance(){}

    public CommunicationInstance(int port) throws IOException {
        CONNECTION = new Connection(port);
        CONNECTION.start();
        CommunicationInstanceInitialization(CONNECTION);
    }

    public CommunicationInstance(String ip, int port) throws IOException {
        CONNECTION = new Connection(port, ip);
        CONNECTION.start();
        CommunicationInstanceInitialization(CONNECTION);
    }

    private void CommunicationInstanceInitialization(Connection connection){
        while (true) {
            try {
                OUTPUT = connection.getOutputStream();
                InputStream input = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(input);
                INPUT_BUFFER = new BufferedReader(inputStreamReader);
                break;
            } catch (IOException e) {
                try {
                    Thread.sleep(50);
                }catch (InterruptedException ignored){}
            }
        }
        CONNECTION_IN_USE = false;
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = env.getScreenDevices();
        for (GraphicsDevice device : devices) {
            int displayRefreshRate = device.getDisplayMode().getRefreshRate();
            if (displayRefreshRate > REFRESH_RATE) REFRESH_RATE = displayRefreshRate;
        }
    }

    @Override
    public synchronized void run() {
        //noinspection InfiniteLoopStatement
        while (GameInstance.getPlayerBoard().getStatus()!=GameStatus.OVER) {

        }
        Co
    }

    private void enemyInput(String commandString) throws StatusException, InputException, FieldException {
        PlayerBoard playerBoard = GameInstance.getPlayerBoard();
        EnemyBoard enemyBoard = GameInstance.getEnemyBoard();

        String[] commandArray = commandString.trim().toUpperCase().split(" ");
        String command = commandArray[0];

        if (command.equals("READY")) {
            if (enemyBoard.getStatus() != GameStatus.PREPARATION) throw new StatusException();
            enemyBoard.setStatus(GameStatus.READY);
        } else if (command.equals("REVOKE")) {
            if (enemyBoard.getStatus() != GameStatus.READY) throw new StatusException();
            enemyBoard.setStatus(GameStatus.PREPARATION);
        } else if (command.matches("\\D")) {
            if (playerBoard.getStatus() != GameStatus.RECEIVE) throw new StatusException();
            if (commandArray.length != 2) throw new InputException();
            int xCoordinate = OutputSymbols.getNumber(commandArray[0].charAt(0));
            int yCoordinate = Integer.parseInt(commandArray[1]);
            CoordinateImpl attackedField = new CoordinateImpl(xCoordinate, yCoordinate);
            playerBoard.receiveAttack(attackedField);
        }
    }

    public static synchronized FieldStatus attackEnemy(Coordinate coordinate) throws InputException, IOException {
        boolean attackSend = false;
        FieldStatus attackedFieldStatus = null;
        while (!attackSend) {
            if (!CONNECTION_IN_USE) {
                CONNECTION_IN_USE = true;
                int xCoordinate = coordinate.getYCoordinate();
                char yCoordinate = OutputSymbols.getAlphabet(coordinate.getYCoordinate());
                OUTPUT.write((yCoordinate + " " + xCoordinate).getBytes());
                attackSend = true;
                while (true) {
                    try {
                        String attackFeedback = INPUT_BUFFER.readLine().trim().toUpperCase();
                        switch (attackFeedback) {
                            case "SHOTWATER":
                                attackedFieldStatus = FieldStatus.SHOTWATER;
                                break;
                            case "HIT":
                                attackedFieldStatus = FieldStatus.HIT;
                                break;
                            case "SUNK":
                                attackedFieldStatus = FieldStatus.SUNK;
                                break;
                            default:
                                throw new IOException("Unidentified Network response");
                        }
                        break;
                    } catch (IOException e) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
                CONNECTION_IN_USE = false;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {
                }
            }
        }
        return attackedFieldStatus;
    }
}
