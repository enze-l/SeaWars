package tests;

import gameModules.*;
import input.Input;

import java.net.InetAddress;
import java.util.Arrays;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ClientTest {
    public static void main(String[] args) {
        GameInstance.newGame();
        try {
            String serverIP=Arrays.toString(InetAddress.getLocalHost().getAddress());
            CommunicationInstance comInstance = new CommunicationInstance( serverIP,12345);
            comInstance.start();
            for (; ; ) {
                try {
                    System.out.printf("%n%n%n");
                    DisplayInstance.displayGame();
                    Input.gameCommands();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
