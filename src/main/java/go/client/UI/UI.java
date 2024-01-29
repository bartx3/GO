package go.client.UI;

import go.communications.Credentials;
import go.communications.Request;
import go.game.*;

/**
 * Facade for the UI
 * A simple one. Just for the sake of having one.
 */
public interface UI {
    /**
     * Shows the game state
     * @param gameState the game state to show
     */
    void showGameState(GameState gameState);

    void showErrorMessage(String message);

    /**
     * Gets the credentials from the user
     * @return the credentials
     */
    Credentials getCredentials();

    /**
     * Prompts the user for a message
     * @param message the message to prompt
     */
    void promptMessage(String message);

    Move getMove(GameState gameState);

    void showGameList(String[] games);

    void showWinner(String winner);

    /**
     * Gets the command from the user. We can treat it as "display main menu"
     * @return the command
     */
    Request getCommand();

    void updateBoard(GameState gameState);
}
