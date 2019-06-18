package tests.ClientServerTest;

import input.ConnectionImpl;

import java.io.IOException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ClientTest {
    public static void main(String[] args) {
        ConnectionImpl client = new ConnectionImpl(12345, "127.0.0.1");
        try {
            client.start();
            Thread.sleep(10000);
            client.checkConnected();
        } catch (IOException|InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

}
