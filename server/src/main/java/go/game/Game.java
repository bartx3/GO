package go.game;
import java.util.ArrayList;


public class Game {
    public final long id;
    public final long player1;
    public final long player2;

    public ArrayList<GameState> gameStates;

    public Game(long id, long player1, long player2) {
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
