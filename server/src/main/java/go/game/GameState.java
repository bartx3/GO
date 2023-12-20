package go.game;

public class GameState {
    final int size;
    final fieldState[][] board;
    final int turn;
    final int player1Captures;
    final int player2Captures;

    public GameState(int size) {
        this.size = size;
        this.board = new fieldState[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; ++j) {
                this.board[i][j] = fieldState.EMPTY;
            }
        }
        this.turn = 0;
        this.player1Captures = 0;
        this.player2Captures = 0;
    }

    public GameState(int size, fieldState[][] board, int turn, int player1Captures, int player2Captures) {
        this.size = size;
        this.board = board;
        this.turn = turn;
        this.player1Captures = player1Captures;
        this.player2Captures = player2Captures;
    }

    public int countScoreP1() {
        int score = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <size; ++j) {
                if (board[i][j] == fieldState.BLACK) {
                    score++;
                }
            }
        }
        return score + player1Captures;
    }

    public int countScoreP2() {
        int score = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <size; ++j) {
                if (board[i][j] == fieldState.WHITE) {
                    score++;
                }
            }
        }
        return score + player2Captures;
    }

    public enum fieldState {
        EMPTY,
        BLACK,
        WHITE
    }
}
