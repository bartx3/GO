package go.client.UI.GUI;

import go.client.UI.UI;
import go.communications.Credentials;
import go.communications.Request;
import go.game.GameState;
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
        return null;
    }

    @Override
    public void promptMessage(String message) {
    }

    @Override
    public go.game.Move getMove(GameState gameState) {
        return null;
    }

    @Override
    public void showGameList(String[] games) {

    }

    @Override
    public void showWinner(String winner) {

    }

    @Override
    public Request getCommand() {
        return null;
    }

}
