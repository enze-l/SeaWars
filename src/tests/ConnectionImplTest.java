package tests;

import input.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ConnectionImplTest {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void connect(){
        String serverIP="127.0.0.1";
        int port=12345;
        try {
            ConnectionImpl server=new ConnectionImpl(port);
            ConnectionImpl client=new ConnectionImpl(port, serverIP);
            server.start();
            client.start();
            Thread.sleep(1000);
            server.isRunning();
            client.isRunning();
            server.checkConnected();
            client.checkConnected();
            server.close();
            client.close();
        }catch (UnknownHostException| InterruptedException e){
            System.err.println(e.getLocalizedMessage());
        }
        catch (IOException e){
            Assert.fail();
        }
    }
}
