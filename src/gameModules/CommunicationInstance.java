package gameModules;

import boards.*;
import boards.fields.*;
import boards.coordinates.*;
import exceptions.*;
import input.*;
import output.OutputSymbols;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class CommunicationInstance extends Thread implements Communication {
    private static Connection CONNECTION;
    private static DataOutputStream OUTPUT;
    private static DataInputStream INPUT;
    private static PlayerBoard PLAYER_BOARD = GameInstance.getPlayerBoard();
    private static EnemyBoard ENEMY_BOARD = GameInstance.getEnemyBoard();
    private static boolean IS_SERVER;
    private static Coordinate LAST_SHOT;

    @SuppressWarnings("unused")
    private CommunicationInstance() {
    }

    public CommunicationInstance(int port) {
        IS_SERVER = true;
        CONNECTION = new Connection(port);
        CONNECTION.start();
        CommunicationInstanceInitialization(CONNECTION);
    }

    public CommunicationInstance(String ip, int port) {
        IS_SERVER = false;
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
            while (PLAYER_BOARD.getStatus() != GameStatus.OVER) {
                String command = INPUT.readUTF();
                switch (command) {
                    case "ready":
                        ready();
                        break;
                    case "revoke":
                        revoke();
                        break;
                    case "shoot":
                        shoot();
                        break;
                    case "giveup":
                        giveUp();
                        break;
                    case "attack":
                        attack();
                        break;
                    case "receive":
                        receive();
                        break;
                    case "result":
                        result();
                        break;
                }
            }
        } catch (Exception e) {
            try {
                CONNECTION.close();
            } catch (IOException ignored) {
            }
            System.out.println("Connection error!");
        }
    }

    private void result() throws CommunicationException {
        try {
            String result = INPUT.readUTF().toUpperCase();
            switch (result) {
                case "SHOTWATER":
                    ENEMY_BOARD.setFieldStatus(LAST_SHOT, FieldStatus.SHOTWATER);
                    ENEMY_BOARD.setStatus(GameStatus.ATTACK);
                    PLAYER_BOARD.setStatus(GameStatus.RECEIVE);
                    break;
                case "HIT":
                    ENEMY_BOARD.setFieldStatus(LAST_SHOT, FieldStatus.HIT);
                    break;
                case "SUNK":
                    ENEMY_BOARD.setFieldStatus(LAST_SHOT, FieldStatus.SUNK);
                    break;
                default:
                    throw new CommunicationException();
            }
        } catch (IOException | FieldException | StatusException e) {
            throw new CommunicationException();
        }
    }

    public static void setLastShot(Coordinate lastShot) {
        LAST_SHOT = lastShot;
    }

    public static DataOutputStream getOUTPUT() {
        return OUTPUT;
    }

    @Override
    public void shoot() throws CommunicationException {
        try {
            char yCoordinate = INPUT.readChar();
            int xCoordinate = INPUT.readInt();
            Coordinate coordinate = new CoordinateImpl(xCoordinate, OutputSymbols.getNumber(yCoordinate));
            FieldStatus attackResult = PLAYER_BOARD.receiveAttack(coordinate);
            OUTPUT.writeUTF("result");
            OUTPUT.writeUTF(attackResult.toString().toLowerCase());
            if (attackResult == FieldStatus.SHOTWATER) {
                PLAYER_BOARD.setStatus(GameStatus.ATTACK);
                ENEMY_BOARD.setStatus(GameStatus.RECEIVE);
            }
        } catch (IOException | InputException | FieldException | StatusException e) {
            throw new CommunicationException();
        }
    }

    @Override
    public void giveUp() {

    }


    @Override
    public void ready() throws CommunicationException {
        try {
            if (ENEMY_BOARD.getStatus() == GameStatus.PREPARATION) {
                ENEMY_BOARD.setStatus(GameStatus.READY);
            } else throw new CommunicationException();
            if (ENEMY_BOARD.getStatus() == GameStatus.READY
                    && PLAYER_BOARD.getStatus() == GameStatus.READY
                    && CONNECTION.isServer()) {
                int randomStart = ThreadLocalRandom.current().nextInt(0, 1 + 1);
                if (randomStart == 0) {
                    ENEMY_BOARD.setStatus(GameStatus.ATTACK);
                    PLAYER_BOARD.setStatus(GameStatus.RECEIVE);
                    OUTPUT.writeUTF("attack");
                } else {
                    ENEMY_BOARD.setStatus(GameStatus.RECEIVE);
                    PLAYER_BOARD.setStatus(GameStatus.ATTACK);
                    OUTPUT.writeUTF("receive");
                }
            }
        } catch (StatusException | IOException e) {
            throw new CommunicationException();
        }
    }

    @Override
    public void revoke() throws CommunicationException {
        try {
            if (PLAYER_BOARD.getStatus() == GameStatus.PREPARATION
                    && ENEMY_BOARD.getStatus() == GameStatus.READY) {
                ENEMY_BOARD.setStatus(GameStatus.PREPARATION);
            } else throw new CommunicationException();
        } catch (StatusException e) {
            throw new CommunicationException();
        }
    }

    @Override
    public void receive() throws CommunicationException {
        try {
            if (PLAYER_BOARD.getStatus() == GameStatus.READY
                    && ENEMY_BOARD.getStatus() == GameStatus.READY) {
                PLAYER_BOARD.setStatus(GameStatus.RECEIVE);
                ENEMY_BOARD.setStatus(GameStatus.ATTACK);
            } else throw new CommunicationException();
        } catch (StatusException e) {
            throw new CommunicationException();
        }
    }

    @Override
    public void attack() throws CommunicationException {
        try {
            if (PLAYER_BOARD.getStatus() == GameStatus.READY
                    && ENEMY_BOARD.getStatus() == GameStatus.READY) {
                PLAYER_BOARD.setStatus(GameStatus.ATTACK);
                ENEMY_BOARD.setStatus(GameStatus.RECEIVE);
            } else throw new CommunicationException();
        } catch (StatusException e) {
            throw new CommunicationException();
        }
    }

    public static boolean isIsServer() {
        return IS_SERVER;
    }
}
