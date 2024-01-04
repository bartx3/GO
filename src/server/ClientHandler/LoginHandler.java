package server.ClientHandler;

import communications.Credentials;
import communications.SocketFacade;
import server.DB.DBFacade;
import server.Server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginHandler extends Thread{
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
                        break;
                    }
                } catch (RuntimeException e) {
                    Logger.getLogger("ServerLogger").log(Level.SEVERE, e.getMessage());
                }
            }
        }
        catch (Exception e) {       // Something happens to socket itself
            Logger.getLogger("ServerLogger").log(Level.SEVERE, e.getMessage());
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
