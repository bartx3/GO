package go.client;

import go.client.UI.ConsoleUI;
import go.client.UI.GUI.GUI;
import go.client.UI.UI;
import go.communications.Request;
import go.communications.SocketFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Client extends Application {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;
    private static System.Logger logger = System.getLogger("ClientLogger");
    private static SocketFacade server;
    @Override
    public void start(Stage stage) throws IOException {
        if (server.getSocket().isClosed()) {
            logger.log(System.Logger.Level.ERROR, "Could not connect to server");
            return;
        }
        UI ui = new ConsoleUI(); //new GUI(stage);
        LoginHandler loginHandler = new LoginHandler(ui, server);
        Thread loginthread = new Thread(loginHandler);
        loginthread.start();
        try {
            loginthread.join();
        } catch (InterruptedException e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
            logger.log(System.Logger.Level.ERROR, "Interrupted while waiting for login thread");
            return;
        }
        if (loginHandler.name == null) {
            logger.log(System.Logger.Level.ERROR, "Could not log in");
            return;
        }
        while (true) {
            Request request = ui.getCommand();
            server.send(request);
            if (request.command.equals("exit")) {
                break;
            }
        }

    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            //socket.connect(socket.getRemoteSocketAddress());
            if (!socket.isConnected()) {
                logger.log(System.Logger.Level.ERROR, "Could not connect to server");
                return;
            }
            server = new SocketFacade(socket);
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
            return;
        }
        launch();
    }
}