package input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author s0568823 - Leon Enzenberger
 */
public interface Connection {
    /**
     * @return true if a connection has been successfully been initialized
     */
    boolean isRunning();

    /**
     * starts the process of connecting
     */
    void run();

    /**
     * closes the current connection
     * @throws IOException if an error is thrown by the connection
     */
    void close() throws IOException;

    /**
     * doesn't do anything unless the connection was closed (potentially unintentional)
     * @throws IOException If the connection isn't any longer established
     */
    void checkConnected() throws IOException;

    /**
     * @return the InputStream of the connection
     * @throws IOException if an error is thrown by the connection
     */
    InputStream getInputStream() throws IOException;

    /**
     * @return the OutputStream of the connection
     * @throws IOException if an error is thrown by the connection
     */
    OutputStream getOutputStream()throws IOException;
}
