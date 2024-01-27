package go.game;

public class GameState implements java.io.Serializable {
    final int size;
    public int getSize() {
        return size;
    }
    final Colour[][] board;

    public Colour[][] getBoard() {
        return board;
    }

    final int turn;

    public final boolean finished;

    public int getTurn() {
        return turn;
    }

    final int player1Captures;
    final int player2Captures;
    final Colour activeplayer;

    public Colour getActivePlayer() {
        return activeplayer;
    }

    public GameState(int size) {
        this.size = size;
        this.board = new Colour[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; ++j) {
                this.board[i][j] = Colour.EMPTY;
            }
        }
        this.turn = 0;
        this.player1Captures = 0;
        this.player2Captures = 0;
        this.finished = false;
        this.activeplayer = Colour.EMPTY;
    }

    public GameState(int size, Colour[][] board, int turn, int player1Captures, int player2Captures, Colour activeplayer) {
        this.size = size;
        this.board = board;
        this.turn = turn;
        this.player1Captures = player1Captures;
        this.player2Captures = player2Captures;
        this.finished = false;
        this.activeplayer = activeplayer;
    }

    public GameState(int size, Colour[][] board, int turn, int player1Captures, int player2Captures, boolean finished, Colour activeplayer) {
        this.size = size;
        this.board = board;
        this.turn = turn;
        this.player1Captures = player1Captures;
        this.player2Captures = player2Captures;
        this.finished = finished;
        this.activeplayer = Colour.EMPTY;
    }


    //niedokładne
    public int countScorePlayer(Colour player) {
        int score = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <size; ++j) {
                if (board[i][j] == player) {
                    score++;
                }
            }
        }
        return score;
    }

    public int countScoreP1() {
        int score = countScorePlayer(Colour.BLACK);
        return score + player1Captures;
    }

    public int countScoreP2() {
        int score = countScorePlayer(Colour.WHITE);
        return score + player2Captures;
    }
}
