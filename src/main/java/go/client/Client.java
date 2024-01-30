package go.client;

import go.client.UI.GUI.GUI;
import go.client.UI.UI;
import go.client.comandStrategies.CommandStrategy;
import go.client.comandStrategies.CommandStrategyFactory;
import go.communications.Request;
import go.communications.SocketFacade;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Client extends Application {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;
    public static System.Logger logger = System.getLogger("ClientLogger");
    private static SocketFacade server;

    @Override
    public void start(Stage stage) throws IOException {

        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

            server = new SocketFacade(socket);
            if (!socket.isConnected()) {
                logger.log(System.Logger.Level.ERROR, "Could not connect to server");
                return;
            }

        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
            return;
        }

        UI ui = new GUI(stage); //new GUI(stage);
        CommandStrategyFactory csf = new CommandStrategyFactory();
        if (server.getSocket().isClosed()) {
            ui.showErrorMessage("Could not connect to server");
            return;
        }

        LoginHandler loginHandler = new LoginHandler(ui, server);
        loginHandler.run();

        if (loginHandler.name == null) {
            logger.log(System.Logger.Level.ERROR, "Could not log in");
            return;
        }
        logger.log(System.Logger.Level.INFO,loginHandler.name);

        while (true) {


            Request request = ui.getCommand();
            if (request == null) {
                continue;
            }
            if (request.command.equals("exit")) {
                break;
            }
            CommandStrategy commandStrategy = csf.getCommandStrategy(request.command);
            if (commandStrategy == null) {
                ui.showErrorMessage("Invalid command");
                continue;
            }
            commandStrategy.apply(server, request.args, ui);
        }


    }

    public static void main(String[] args){
        launch();

    }
}