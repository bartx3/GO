package go.ui;

import go.game.GameState;
import go.game.Move;

import java.util.Set;

/**
 * Facade for the UI
 * A simple one. Just for the sake of having one.
 */
public interface BasicUIFacade {
    void showGameState(GameState gameState);

    void showErrorMessage(String message);

    String getLine();

    void putLine(String message);

    Move getMove();

    void showUserList(String[] games);

    void showWinner(String winner);
}
