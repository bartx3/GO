package go.server.ClientHandling;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Accepter extends Thread {

    System.Logger logger = System.getLogger("Accepter");
    int port;
    boolean running = true; //do wyłączenia wątku
    ServerSocket serverSocket;

    public Accepter(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    public void stopAccepting() {
        running = false;
    }

    public void run() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
                logger.log(System.Logger.Level.INFO, "New client connected");
            } catch (IOException e) {
                logger.log(System.Logger.Level.ERROR, e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

}
