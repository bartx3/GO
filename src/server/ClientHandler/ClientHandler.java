package server.ClientHandler;

import communications.Credentials;
import communications.SocketFacade;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class ClientHandler implements Runnable{
    protected SocketFacade socket;
    protected Credentials credentials;
    public ClientHandler(Socket client) {
        this.socket = new SocketFacade(client);
    }

    @Override
    public void run() {
        LoginHandler loginHandler = new LoginHandler(socket);
        loginHandler.start();
        try {
            loginHandler.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        credentials = loginHandler.getCredentials();
    }
}
