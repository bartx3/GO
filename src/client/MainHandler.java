package client;

import client.UI.UI;
import communications.Credentials;
import communications.SocketFacade;
import communications.loginException;

import java.net.Socket;

public class MainHandler implements Runnable {

    protected SocketFacade socket;
    protected UI ui;
    protected Credentials credentials;

    public MainHandler(Socket socket, UI ui) {
        this.socket = new SocketFacade(socket);
        this.ui = ui;
    }

    @Override
    public void run() {
        try {
            // try login until successful
            while (true) {
                try {
                    login();
                    ui.promptMessage("Login successful");
                    break;
                } catch (loginException e) {
                    ui.showErrorMessage("Login failed");
                }
            }
        }
        catch (Exception e) {       // Something happens to socket itself
            ui.showErrorMessage("Error happened while connecting to server");
        }
    }

    public void login() throws loginException, Exception
    {
        credentials = ui.getCredentials();
        socket.send(credentials);
        Object response = socket.receive();
        if (!(response instanceof Boolean) || !((Boolean) response))
            throw new loginException();
        else
            ui.promptMessage("Login successful");
    }
}
