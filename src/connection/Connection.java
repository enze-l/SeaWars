package connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author s0568823 - Leon Enzenberger
 */
public class Connection extends Thread {
    private final int port;
    private final boolean asServer;
    private Socket socket=null;

    private final String remoteIP;

    private boolean threadRunning = false;
    private boolean fatalError = false;

    private  final int WAIT_SECOND_MS=1000;

    private String connectionStatus="";

    public Connection(int port, boolean asServer){
        this.port=port;
        this.asServer=asServer;
        this.remoteIP=null;
    }

    public Connection(int port, boolean asServer, String ip){
        this.port=port;
        this.asServer=asServer;
        this.remoteIP=ip;
    }

    @Override
    public void run(){
        this.threadRunning=true;
        try {
            if (this.asServer)
                this.socket=getServerSocket();
            else{
                this.socket=getClientSocket();
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
            this.fatalError = true;
        }
    }

    public void close() throws IOException {
        if (this.socket != null) {
            this.socket.close();
        }
    }

    public void waitForConnection() throws IOException {
        if (!this.threadRunning) {
            try {
                Thread.sleep(WAIT_SECOND_MS);
            } catch (InterruptedException ignored) {}

            if (!this.threadRunning) {
                throw new IOException("Connection not initialized!");
            }
        }
        while (!this.fatalError && this.socket == null) {
            try {
                Thread.sleep(WAIT_SECOND_MS);
            } catch (InterruptedException ex) {
                // ignore
            }
        }
    }

    public void checkConnected() throws IOException {
        if (this.socket == null) {
            throw new IOException("No Connection found");
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

    private Socket getServerSocket()throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        return serverSocket.accept();
    }

    private Socket getClientSocket(){
        for(;;){
            try{
                return new Socket(remoteIP, port);
            }catch (IOException e){
                try {
                    Thread.sleep(WAIT_SECOND_MS);
                } catch (InterruptedException ignored){}
            }
        }
    }
}
