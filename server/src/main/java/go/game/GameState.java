package go.game;

public class GameState {
    final int size;
    final int[][] board;
    final int turn;
    final int player1Captures;
    final int player2Captures;

    public GameState(int size, int[][] board, int turn, int player1Captures, int player2Captures) {
        this.size = size;
        this.board = board;
        this.turn = turn;
        this.player1Captures = player1Captures;
        this.player2Captures = player2Captures;
    }
}
