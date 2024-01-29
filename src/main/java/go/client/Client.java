package go.client;

import go.client.UI.GUI.GUI;
import go.client.UI.GUI.Welcome;
import go.client.UI.UI;
import go.client.comandStrategies.CommandStrategyFactory;
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
    public static System.Logger logger = System.getLogger("ClientLogger");
    private static SocketFacade server;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Client.class.getResource("/welcome.fxml"));
        Scene scene = new Scene(loader.load());
        Welcome welcome = loader.getController();
        welcome.setStage(stage);
        welcome.setScene(scene);
        stage.setScene(scene);
        stage.show();
        //SceneManager.setScene("UI/GUI/welcome.fxml");

        try {


            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            //socket.connect(socket.getRemoteSocketAddress());
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
        logger.log(System.Logger.Level.INFO,loginHandler.name);

        /*while (true) {


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
        }*/


    }

    public static void main(String[] args){
        launch();

    }
}