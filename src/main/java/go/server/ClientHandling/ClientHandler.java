package go.server.ClientHandling;

import go.communications.Request;
import go.communications.SocketFacade;

import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler extends Thread {
    SocketFacade socket;

    String name;
    public ClientHandler(Socket socket) throws SocketException {
        this.socket = new SocketFacade(socket);
    }

    @Override
    public void run() {
        try {
            LoginHandler loginHandler = new LoginHandler(socket);
            Thread loginthread = new Thread(loginHandler).start();
            loginthread.join();

            while (true) {
                Serializable srec = socket.receive();
                if (!(srec instanceof Request)) {
                    throw new RuntimeException("Received object is not a Request");
                }
                Request request = (Request) srec;


            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
