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
    final Colour winner;

    final boolean passed;

    public Colour getActivePlayer() {
        return activeplayer;
    }

    public GameState(int size) {
        this.size = size;
        this.board = new Colour[size][size];
        this.winner = Colour.EMPTY;
        this.passed = false;
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

    public GameState(int size, Colour[][] board)
    {
        this.size = size;
        this.board = board;
        this.winner = Colour.EMPTY;
        this.passed = false;
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
        this.passed = false;
        this.activeplayer = activeplayer;
        this.winner = Colour.EMPTY;
    }

    public GameState(int size, Colour[][] board, int turn, int player1Captures, int player2Captures, boolean finished, Colour player, boolean passed) {
        this.size = size;
        this.board = board;
        this.turn = turn;
        this.player1Captures = player1Captures;
        this.player2Captures = player2Captures;
        this.finished = finished;
        this.passed = passed;
        if (finished) {
            this.activeplayer = Colour.EMPTY;
            this.winner = player;
        } else {
            this.activeplayer = player;
            this.winner = Colour.EMPTY;
        }
    }


    public int countScoreP1() {
        int score = countBoardScorePlayer(Colour.BLACK);
        return score + player1Captures;
    }

    public int countScoreP2() {
        int score = countBoardScorePlayer(Colour.WHITE);
        return score + player2Captures;
    }

    private int countBoardScorePlayer(Colour colour) {
        int score = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0 ; j < size; j++) {
                if (board[i][j].equals(Colour.EMPTY) && territory_owner(i, j).equals(colour)) {
                    score++;
                }
            }
        }
        return score;
    }

    private Colour territory_owner(int i , int j) {
        Boolean[][] visited = new Boolean[size][size];
        for (int k = 0; k < size; k++) {
            for (int l = 0 ; l < size; l++) {
                visited[k][l] = false;
            }
        }
        return territory_owner(i, j, visited);
    }

    private Colour territory_owner(int i , int j, Boolean[][] visited) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            return Colour.EMPTY;
        }
        if (visited[i][j]) {
            return Colour.EMPTY;
        }
        visited[i][j] = true;
        if (board[i][j] != Colour.EMPTY) {
            return board[i][j];
        }
        Colour owner = Colour.EMPTY;
        if (i + 1 < size) {
            owner = territory_owner(i + 1, j, visited);
        }
        if (i - 1 >= 0) {
            Colour owner2 = territory_owner(i - 1, j, visited);
            if (owner != owner2) {
                return Colour.EMPTY;
            }
        }
        if (j + 1 < size) {
            Colour owner2 = territory_owner(i, j + 1, visited);
            if (owner != owner2) {
                return Colour.EMPTY;
            }
        }
        if (j - 1 >= 0) {
            Colour owner2 = territory_owner(i, j - 1, visited);
            if (owner != owner2) {
                return Colour.EMPTY;
            }
        }
        return owner;
    }

    public Colour getWinner() {
        return winner;
    }

    public boolean getPassed() {
        return passed;
    }

    public int getPlayer1Captures() {
        return player1Captures;
    }

    public int getPlayer2Captures() {
        return player2Captures;
    }

    public boolean getFinished() {
        return finished;
    }
}
