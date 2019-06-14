package tests.ComInstance;

import gameModules.*;
import input.Input;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ComInstanceClient {
    public static void main(String[] args) {
        GameInstance.newGame();
        try {
            String serverIP="127.0.0.1";
            CommunicationInstance comInstance = new CommunicationInstance( serverIP,12345);
            comInstance.start();
            DisplayInstance d=new DisplayInstance();
            d.start();
            Input i=new Input();
            //noinspection InfiniteLoopStatement
            for (; ; ) {
                try {
                    i.command();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
