package go.client;

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
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
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