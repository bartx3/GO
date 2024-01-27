package go.server.ClientHandling;

import go.communications.Credentials;
import go.communications.SocketFacade;
import go.server.DB.DBFacade;
import go.server.Server;

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
        Server.logger.log(System.Logger.Level.INFO, "running login handler");
        do {
            try {
                Credentials credentials = (Credentials) socket.receive();
                if (db.login(credentials) || db.register(credentials))
                {
                    this.name = credentials.getUsername();
                    break;
                }
            } catch (ClassCastException | SocketException e) {
                throw new RuntimeException(e);
            }
        } while (true);
        Server.logger.log(System.Logger.Level.INFO, "client " + name + " logged in");
    }
}
