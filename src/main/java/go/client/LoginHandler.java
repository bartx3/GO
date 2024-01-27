package go.client;

import go.client.UI.UI;
import go.communications.Credentials;
import go.communications.SocketFacade;

import java.net.SocketException;

import static go.client.Client.logger;

public class LoginHandler implements Runnable {
    String name;
    UI ui;
    SocketFacade server;
    public LoginHandler(UI ui, SocketFacade server) {
        this.ui = ui;
        this.server = server;
        this.name = null;
    }
    @Override
    public void run() {
        logger.log(System.Logger.Level.INFO, "Running login");
        Boolean success = false;
        do {
            Credentials credentials = ui.getCredentials();
            try {
                server.send(credentials);
                success = (Boolean) server.receive();
                if (!success) {
                    ui.promptMessage("Login failed, try again");
                } else {
                    name = credentials.getUsername();
                }
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }

        } while (!success);

        ui.promptMessage("Login as " + name + " successful");
    }

    public String getName() {
        return name;
    }
}
