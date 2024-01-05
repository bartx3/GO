package server.ClientHandler;

import communications.Credentials;
import communications.SocketFacade;
import server.Server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class ClientHandler implements Runnable{
    System.Logger logger = System.getLogger(ClientHandler.class.getName());
    protected SocketFacade socket;
    protected Credentials credentials;
    public ClientHandler(Socket client) {
        this.socket = new SocketFacade(client);
    }

    @Override
    public void run() {
        LoginHandler loginHandler = new LoginHandler(socket);
        try {
            loginHandler.start();
            loginHandler.join();
        } catch (InterruptedException e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }
        credentials = loginHandler.getCredentials();
        if (credentials == null) {
            logger.log(System.Logger.Level.ERROR, "Login failed");
            return;
        }

        SessionData sessionData = new SessionData(socket, credentials.username);
        Server.usersOnline.put(credentials.username, sessionData);

    }
}
