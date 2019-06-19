package input;

import boards.*;
import boards.fields.*;
import boards.coordinates.*;
import exceptions.*;
import gameModules.Game;
import gameModules.Display;
import output.OutputSymbols;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class CommunicationInstance extends Thread implements Communication {
    private static ConnectionImpl CONNECTIONImpl;
    private static DataOutputStream OUTPUT;
    private static DataInputStream INPUT;
    private static PlayerBoard PLAYER_BOARD = Game.getPlayerBoard();
    private static EnemyBoard ENEMY_BOARD = Game.getEnemyBoard();
    private static boolean IS_SERVER;
    private static Coordinate LAST_SHOT;

    static void setLastShot(Coordinate lastShot) {
        LAST_SHOT = lastShot;
    }

    static DataOutputStream getOUTPUT() {
        return OUTPUT;
    }

    static boolean isIsServer() {
        return IS_SERVER;
    }

    /**
     * creates ServerInstance
     *
     * @param port is the port that should be opened
     */
    public CommunicationInstance(int port) {
        IS_SERVER = true;
        CONNECTIONImpl = new ConnectionImpl(port);
        CONNECTIONImpl.start();
        CommunicationInstanceInitialization(CONNECTIONImpl);
    }

    /**
     * creates clientInstance
     *
     * @param ip   of the server that should be connected to
     * @param port the port of the server
     */
    public CommunicationInstance(String ip, int port) {
        IS_SERVER = false;
        CONNECTIONImpl = new ConnectionImpl(port, ip);
        CONNECTIONImpl.start();
        CommunicationInstanceInitialization(CONNECTIONImpl);
    }

    private void CommunicationInstanceInitialization(ConnectionImpl connectionImpl) {
        while (true) {
            try {
                OUTPUT = new DataOutputStream(connectionImpl.getOutputStream());
                INPUT = new DataInputStream(connectionImpl.getInputStream());
                break;
            } catch (IOException e) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    /**
     * Starts own thread that is responsible for handling incoming data
     */
    @Override
    public synchronized void run() {
        try {
            while (PLAYER_BOARD.getGameStatus() != GameStatus.LOST
                    && PLAYER_BOARD.getGameStatus() != GameStatus.WON) {
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
                CONNECTIONImpl.close();
            } catch (IOException ignored) {
            }
            Display.displayMessage("ConnectionImpl error!");
        }
    }

    @Override
    public void result() throws CommunicationException {
        try {
            String result = INPUT.readUTF().toUpperCase();
            switch (result) {
                case "SHOTWATER":
                    ENEMY_BOARD.setFieldStatus(LAST_SHOT, FieldStatus.SHOTWATER);
                    ENEMY_BOARD.setGameStatus(GameStatus.ATTACK);
                    PLAYER_BOARD.setGameStatus(GameStatus.RECEIVE);
                    break;
                case "HIT":
                    ENEMY_BOARD.setFieldStatus(LAST_SHOT, FieldStatus.HIT);
                    break;
                case "SUNK":
                    ENEMY_BOARD.setFieldStatus(LAST_SHOT, FieldStatus.SUNK);
                    if (ENEMY_BOARD.allShipsSunk()) {
                        PLAYER_BOARD.setGameStatus(GameStatus.WON);
                    }
                    break;
                default:
                    throw new CommunicationException();
            }
        } catch (IOException | FieldException | StatusException | DisplayException e) {
            throw new CommunicationException();
        }
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
                PLAYER_BOARD.setGameStatus(GameStatus.ATTACK);
                ENEMY_BOARD.setGameStatus(GameStatus.RECEIVE);
            }
        } catch (IOException | InputException | FieldException | StatusException | DisplayException e) {
            throw new CommunicationException();
        }
    }

    @Override
    public void ready() throws CommunicationException {
        try {
            if (ENEMY_BOARD.getGameStatus() == GameStatus.PREPARATION) {
                ENEMY_BOARD.setGameStatus(GameStatus.READY);
            } else throw new CommunicationException();
            if (ENEMY_BOARD.getGameStatus() == GameStatus.READY
                    && PLAYER_BOARD.getGameStatus() == GameStatus.READY
                    && CONNECTIONImpl.isServer()) {
                int randomStart = ThreadLocalRandom.current().nextInt(0, 1 + 1);
                if (randomStart == 0) {
                    ENEMY_BOARD.setGameStatus(GameStatus.ATTACK);
                    PLAYER_BOARD.setGameStatus(GameStatus.RECEIVE);
                    OUTPUT.writeUTF("attack");
                } else {
                    ENEMY_BOARD.setGameStatus(GameStatus.RECEIVE);
                    PLAYER_BOARD.setGameStatus(GameStatus.ATTACK);
                    OUTPUT.writeUTF("receive");
                }
            }
        } catch (StatusException | IOException | DisplayException e) {
            throw new CommunicationException();
        }
    }

    @Override
    public void revoke() throws CommunicationException {
        try {
            if (PLAYER_BOARD.getGameStatus() == GameStatus.PREPARATION
                    && ENEMY_BOARD.getGameStatus() == GameStatus.READY) {
                ENEMY_BOARD.setGameStatus(GameStatus.PREPARATION);
            } else throw new CommunicationException();
        } catch (StatusException | DisplayException e) {
            throw new CommunicationException();
        }
    }

    @Override
    public void receive() throws CommunicationException {
        try {
            if (PLAYER_BOARD.getGameStatus() == GameStatus.READY
                    && ENEMY_BOARD.getGameStatus() == GameStatus.READY) {
                PLAYER_BOARD.setGameStatus(GameStatus.RECEIVE);
                ENEMY_BOARD.setGameStatus(GameStatus.ATTACK);
            } else throw new CommunicationException();
        } catch (StatusException | DisplayException e) {
            throw new CommunicationException();
        }
    }

    @Override
    public void attack() throws CommunicationException {
        try {
            if (PLAYER_BOARD.getGameStatus() == GameStatus.READY
                    && ENEMY_BOARD.getGameStatus() == GameStatus.READY) {
                PLAYER_BOARD.setGameStatus(GameStatus.ATTACK);
                ENEMY_BOARD.setGameStatus(GameStatus.RECEIVE);
            } else throw new CommunicationException();
        } catch (StatusException | DisplayException e) {
            throw new CommunicationException();
        }
    }
}
