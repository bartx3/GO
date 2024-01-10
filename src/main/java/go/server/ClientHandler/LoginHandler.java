package go.server.ClientHandler;

import go.server.Server;
import go.communications.Credentials;
import go.communications.SocketFacade;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.SocketException;

public class LoginHandler implements Runnable{
    protected SocketFacade socket;
    protected Credentials credentials;


    public Credentials getCredentials() {
        return credentials;
    }

    public LoginHandler(SocketFacade socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    if (trylogin()) {
                        Server.usersOnline.put(credentials.username, new SessionData(socket, credentials.username));
                        Server.usersOnline.get(credentials.username).start();
                        break;
                    }
                } catch (RuntimeException e) {
                    Logger.getLogger("ServerLogger").log(Level.SEVERE, e.getMessage());
                }
            }
            while (true){} // waiting for now
        }
        catch (Exception e) {       // Something happens to socket itself
            Logger.getLogger("ServerLogger").log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    boolean trylogin() throws Exception {
        Object received = socket.receive();
        if (received instanceof Credentials) {
            credentials = (Credentials) received;
            boolean logged = login(credentials);
            socket.send(logged);
            return logged;
        }
        throw new RuntimeException("Unexpected object received");
    }

    boolean login(Credentials credentials) {
        if (Server.usersOnline.containsKey(credentials.username)) {
            return false;
        }
        return Server.db.login(credentials) || Server.db.register(credentials);
    }
}
