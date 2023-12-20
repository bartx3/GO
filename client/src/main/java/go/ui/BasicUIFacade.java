package go.ui;

import go.game.GameState;
import go.game.Move;

/**
 * Facade for the UI
 * A simple one. Just for the sake of having one.
 */
public interface BasicUIFacade {
    void showGameState(GameState gameState);

    void showErrorMessage(String message);

    String getName();

    Move getMove();

    void showUserList(String[] games);

    void showWinner(String winner);
}
