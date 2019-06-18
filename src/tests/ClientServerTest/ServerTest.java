package tests.ClientServerTest;

import input.ConnectionImpl;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ServerTest {
    public static void main(String[] args) {
        ConnectionImpl server = new ConnectionImpl(12345);
        try {
            server.start();
            Thread.sleep(10000);
            server.checkConnected();
        } catch (IOException |InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
