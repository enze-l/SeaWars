package gameModules;

import boards.*;
import boards.fields.FieldStatus;
import connection.Communication;
import connection.Connection;
import coordinates.*;
import exceptions.*;
import output.OutputSymbols;

import java.io.*;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class CommunicationInstance extends Thread implements Communication {
    private static Connection CONNECTION;
    private static DataOutputStream OUTPUT;
    private static DataInputStream INPUT;

    private CommunicationInstance() {
    }

    public CommunicationInstance(int port) {
        CONNECTION = new Connection(port);
        CONNECTION.start();
        CommunicationInstanceInitialization(CONNECTION);
    }

    public CommunicationInstance(String ip, int port) {
        CONNECTION = new Connection(port, ip);
        CONNECTION.start();
        CommunicationInstanceInitialization(CONNECTION);
    }

    private void CommunicationInstanceInitialization(Connection connection) {
        while (true) {
            try {
                OUTPUT = new DataOutputStream(connection.getOutputStream());
                INPUT = new DataInputStream(connection.getInputStream());
                break;
            } catch (IOException e) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    @Override
    public synchronized void run() {
        try {
            while (GameInstance.getPlayerBoard().getStatus() != GameStatus.OVER) {
                String command = INPUT.readUTF();
                switch (command) {
                    case "ready":

                        break;
                    case "revoke":

                        break;
                    case "start":

                        break;
                    case "receive":

                        break;
                    case "shot":

                        break;
                    case "giveup":

                        break;
                }
            }
        } catch (Exception e) {
            try {
                CONNECTION.close();
            } catch (IOException ignored) { }
            System.out.println("Es ist ein Fehler in der Verbindung aufgetreten!");
        }
    }


    @Override
    public void shot() {

    }

    @Override
    public void giveUp() {

    }

    @Override
    public void receive() {

    }

    @Override
    public void ready() {

    }


    @Override
    public FieldStatus attackEnemy(Coordinate coordinate) throws InputException, IOException {
        FieldStatus attackedFieldStatus;
        int xCoordinate = coordinate.getYCoordinate();
        char yCoordinate = OutputSymbols.getAlphabet(coordinate.getYCoordinate());
        OUTPUT.write((yCoordinate + " " + xCoordinate).getBytes());
        while (true) {
            try {
                String attackFeedback = INPUT.readUTF().toUpperCase();
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
        return attackedFieldStatus;
    }
}
