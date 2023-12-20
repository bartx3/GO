package go.ui;

import go.game.GameState;
import go.game.Move;

public class ConsoleUI implements BasicUIFacade{


    @Override
    public void showGameState(GameState gameState) {
        System.out.print("\033[H\033[2J"); // clear screen
        System.out.flush();
        System.out.println("Turn: " + gameState.turn);
    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Move getMove() {
        return null;
    }

    @Override
    public void showUserList(String[] games) {

    }

    @Override
    public void showWinner(String winner) {

    }
}
