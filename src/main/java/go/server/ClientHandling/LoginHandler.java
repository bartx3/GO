package go.server.ClientHandling;

import go.communications.Credentials;
import go.communications.SocketFacade;
import go.server.DB.DBFacade;

import java.net.SocketException;

public class LoginHandler implements Runnable {
    SocketFacade socket;
    String name;

    DBFacade db = go.server.Server.getDB();

    public LoginHandler(SocketFacade socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        do {
            try {
                Credentials credentials = (Credentials) socket.receive();
                db.login(credentials);
            } catch (ClassCastException | SocketException e) {
                throw new RuntimeException(e);
            }
        } while (true);
    }
}
