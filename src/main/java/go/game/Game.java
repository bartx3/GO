package go.game;

import java.util.ArrayList;


public class Game {
    public final long id;

    public enum FinalState {
        NOT_FINISHED,
        PLAYER1_WON,
        PLAYER2_WON,
        DRAW
    }
    public final String player1;
    public final String player2;
    public FinalState winner = FinalState.NOT_FINISHED;
    public ArrayList<GameState> gameStates;

    public Game(long id, String player1, String player2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
    }

    public void addGameState(GameState gameState) {
        gameStates.add(gameState);
    }

    public GameState getGameState(int index) {
        try {
            return gameStates.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public int getGameStateCount() {
        return gameStates.size();
    }
}
