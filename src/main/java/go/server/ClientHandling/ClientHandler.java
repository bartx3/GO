package go.server.ClientHandling;

import go.communications.SocketFacade;

import java.net.Socket;
import java.net.SocketException;

public class ClientHandler extends Thread {
    SocketFacade socket;
    public ClientHandler(Socket socket) throws SocketException {
        this.socket = new SocketFacade(socket);
    }

    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
