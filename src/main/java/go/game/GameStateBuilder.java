package go.game;

public class GameStateBuilder {
    private int size;
    private Colour[][] board;
    private int turn;
    private int player1Captures;
    private int player2Captures;
    private boolean finished;

    public GameStateBuilder(GameState gameState) {
        this.size = gameState.size;
        this.board = gameState.board;
        this.turn = gameState.turn;
        this.player1Captures = gameState.player1Captures;
        this.player2Captures = gameState.player2Captures;
    }

    public GameStateBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public GameStateBuilder setBoard(Colour[][] board) {
        this.board = board;
        return this;
    }

    public GameStateBuilder setTurn(int turn) {
        this.turn = turn;
        return this;
    }

    public GameStateBuilder setPlayer1Captures(int player1Captures) {
        this.player1Captures = player1Captures;
        return this;
    }

    public GameStateBuilder setPlayer2Captures(int player2Captures) {
        this.player2Captures = player2Captures;
        return this;
    }
    
    public GameStateBuilder setFinished(boolean finished) {
        this.finished = finished;
        return this;
    }

    public GameState createGameState() {
        return new GameState(size, board, turn, player1Captures, player2Captures,);
    }

    private boolean fastCheckMove(Move move) {
        if (move.isPass) {
            return true;
        }
        if (move.x < 0 || move.x >= size || move.y < 0 || move.y >= size) {
            return false;
        }
        return board[move.x][move.y] == Colour.EMPTY;
    }

    private int countBreaths(int x, int y, Colour color) {
        int breaths = 0;
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return 0;
        }
        if (board[x][y] == Colour.EMPTY) {
            return 1;
        }
        if (board[x][y] != color) {
            return 0;
        }
        if (board[x][y] == color) {
            breaths += countBreaths(x + 1, y, color);
            breaths += countBreaths(x - 1, y, color);
            breaths += countBreaths(x, y + 1, color);
            breaths += countBreaths(x, y - 1, color);
        }
        return breaths;
    }

    private int[][] countAllBreaths() {
        int[][] breaths = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0 ; j < size; j++) {
                breaths[i][j] = countBreaths(i, j, board[i][j]);
            }
        }
        return breaths;
    }

    public void MakeMove(Move move, boolean whiteturn) {
        if (whiteturn)
            turn++;
        if (move.isPass)
            return;

        board[move.x][move.y] = whiteturn ? Colour.WHITE : Colour.BLACK;
        int[][] breaths = countAllBreaths();
        //Najpierw zbijamy przeciwnika
        for (int i = 0; i < size; i++) {
            for (int j = 0 ; j < size; j++) {
                if (breaths[i][j] == 0) {
                    if (board[i][j] == Colour.BLACK && whiteturn) {
                        board[i][j] = Colour.EMPTY;
                        player1Captures++;
                    }
                    if (board[i][j] == Colour.WHITE && !whiteturn) {
                        board[i][j] = Colour.EMPTY;
                        player2Captures++;
                    }
                }
            }
        }
        //Liczymy oddechy od nowa i zbijamy swoje kamienie
        breaths = countAllBreaths();
        for (int i = 0; i < size; i++) {
            for (int j = 0 ; j < size; j++) {
                if (breaths[i][j] == 0) {
                    if (board[i][j] == Colour.BLACK) {
                        board[i][j] = Colour.EMPTY;
                        player1Captures++;
                    }
                    if (board[i][j] == Colour.WHITE) {
                        board[i][j] = Colour.EMPTY;
                        player2Captures++;
                    }
                }
            }
        }
    }

    private boolean checkPostMove(Move move) {
        if (move.isPass) {
            return true;
        }
        return board[move.x][move.y] != Colour.EMPTY;
    }

    public boolean performAndCheckMove(Move move, boolean whiteturn) {
        if (!fastCheckMove(move)) {
            return false;
        }
        MakeMove(move, whiteturn);
        return checkPostMove(move);
    }
}
