package go.client.UI;

import go.communications.Packet;
import go.communications.Credentials;
import go.game.*;

/**
 * Facade for the UI
 * A simple one. Just for the sake of having one.
 */
public interface UI {
    void showGameState(GameState gameState);

    void showErrorMessage(String message);

    Credentials getCredentials();

    void promptMessage(String message);

    Move getMove();

    void showUserList(String[] games);

    void showWinner(String winner);

    Packet getCommand();

    boolean getConfirmation(String message);
}
