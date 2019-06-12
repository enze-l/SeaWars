package tests;

import gameModules.*;
import input.Input;

import java.net.InetAddress;
import java.util.Arrays;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ComInstanceCLient {
    public static void main(String[] args) {
        GameInstance.newGame();
        try {
            String serverIP="127.0.0.1";
            CommunicationInstance comInstance = new CommunicationInstance( serverIP,12345);
            comInstance.start();
            Thread.sleep(10000);
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
