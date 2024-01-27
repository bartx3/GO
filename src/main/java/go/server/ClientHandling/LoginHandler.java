package go.server.ClientHandling;

import go.communications.Credentials;
import go.communications.SocketFacade;
import go.server.DB.DBFacade;
import go.server.Server;

import java.net.SocketException;

import static go.server.Server.logger;

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
        logger.log(System.Logger.Level.INFO, "running login handler");
        do {
            try {
                Credentials credentials = (Credentials) socket.receive();
                Boolean success = (db.login(credentials) || db.register(credentials));
                socket.send(success);
                if (success)
                {
                    logger.log(System.Logger.Level.INFO, "client logged in");
                    this.name = credentials.getUsername();
                    break;
                }
                logger.log(System.Logger.Level.INFO, "client failed to log in");
            } catch (ClassCastException | SocketException e) {
                throw new RuntimeException(e);
            }
        } while (true);
        logger.log(System.Logger.Level.INFO, "client " + name + " logged in");
    }
}
