package input;

import gameModules.Display;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class ConnectionImpl extends Thread implements Connection {
    private final int port;
    private final String remoteIP;

    boolean isServer() {
        return asServer;
    }

    private final boolean asServer;
    private Socket socket;

    private boolean threadRunning = false;

    public ConnectionImpl(int port) {
        this.port = port;
        this.asServer = true;
        this.remoteIP = null;
    }

    public ConnectionImpl(int port, String ip) {
        this.port = port;
        this.asServer = false;
        this.remoteIP = ip;
    }

    @Override
    public void run() {
        this.threadRunning = true;
        try {
            if (this.asServer)
                this.socket = getServerSocket();
            else {
                this.socket = getClientSocket();
            }
        } catch (IOException e) {
            Display.displayMessage(e.getMessage());
        }
    }

    public boolean isRunning(){
        return this.threadRunning;
    }

    public void close() throws IOException {
        if (this.socket != null) {
            this.socket.close();
        }
    }

    public void checkConnected() throws IOException {
        if (this.socket == null) {
            throw new IOException("No ConnectionImpl found");
        }
    }

    public InputStream getInputStream() throws IOException {
        this.checkConnected();
        return this.socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        this.checkConnected();
        return this.socket.getOutputStream();
    }

    private Socket getServerSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        return serverSocket.accept();
    }

    private Socket getClientSocket() {
        for (; ; ) {
            try {
                return new Socket(remoteIP, port);
            } catch (IOException e) {
                try {
                    int WAIT_SECOND = 1000;
                    Thread.sleep(WAIT_SECOND);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
