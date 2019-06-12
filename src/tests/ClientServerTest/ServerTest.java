package tests.ClientServerTest;

import connection.Connection;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ServerTest {
    public static void main(String[] args) {
        Connection server = new Connection(12345);
        try {
            server.start();
            Thread.sleep(10000);
            server.checkConnected();
        } catch (IOException |InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
