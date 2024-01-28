package go.client.UI.GUI;

import go.client.UI.UI;
import go.communications.Credentials;
import go.communications.Request;
import javafx.stage.Stage;

public class GUI implements UI {
    Stage stage;

    public GUI(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void showGameState(go.game.GameState gameState) {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public Credentials getCredentials() {
        return new Logging().getCredentials();
    }

    @Override
    public void promptMessage(String message) {

    }

    @Override
    public void showWinner(String winner) {

    }

    @Override
    public Request getCommand() {
        return null;
    }
}
