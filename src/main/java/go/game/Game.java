package go.game;

import java.util.ArrayList;


public class Game implements java.io.Serializable {
    private final long id;
    private final int size;

    public int getSize() {
        return size;
    }

    public Long getId() {
        return id;
    }


    public enum FinalState {
        NOT_FINISHED,
        PLAYER1_WON,
        PLAYER2_WON,
        DRAW
    }
    public final String player1;
    public final String player2;
    private FinalState winner = FinalState.NOT_FINISHED;
    public ArrayList<GameState> gameStates;

    public FinalState getWinner() {
        return winner;
    }

    public Game(long id, String player1, String player2, int size) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.size = size;
    }

    public void addGameState(GameState gameState) throws Exception {
        if (this.size != gameState.size) {
            throw new Exception("Game size mismatch");
        }
        if (gameState.finished) {
            if (gameState.getWinner() == Colour.BLACK) {
                winner = FinalState.PLAYER1_WON;
            } else if (gameState.getWinner() == Colour.WHITE) {
                winner = FinalState.PLAYER2_WON;
            } else {
                winner = FinalState.DRAW;
            }
        }
        gameStates.add(gameState);
    }

    public GameState getGameState(int index) {
        try {
            return gameStates.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public void init() {
        gameStates = new ArrayList<>();
        Colour[][] board = new Colour[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; ++j) {
                board[i][j] = Colour.EMPTY;
            }
        }
        gameStates.add(new GameState(size, board, 0, 0, 0, false, Colour.BLACK, false));
    }

    public int getGameStateCount() {
        return gameStates.size();
    }
}
