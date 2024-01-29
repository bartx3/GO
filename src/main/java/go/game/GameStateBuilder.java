package go.game;

public class GameStateBuilder {
    private int size;
    private Colour[][] board;
    private int turn;
    private int player1Captures;
    private int player2Captures;
    private boolean finished = false;
    private Colour activeplayer;

    public GameStateBuilder(GameState gameState) {
        this.size = gameState.size;
        //copy board
        this.board = new Colour[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(gameState.board[i], 0, this.board[i], 0, size);
        }
        this.turn = gameState.turn;
        this.player1Captures = gameState.player1Captures;
        this.player2Captures = gameState.player2Captures;
        this.finished = gameState.finished;
        this.activeplayer = gameState.activeplayer;
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

    public GameStateBuilder setActivePlayer(Colour activeplayer) {
        this.activeplayer = activeplayer;
        return this;
    }

    public GameState createGameState() {
        return new GameState(size, board, turn, player1Captures, player2Captures, finished, activeplayer);
    }

    private boolean fastCheckMove(Move move) {
        if (move.isPass || move.giveUp) {
            return true;
        }
        if (move.x < 0 || move.x >= size || move.y < 0 || move.y >= size) {
            return false;
        }
        return board[move.x][move.y] == Colour.EMPTY;
    }

    private int countBreaths(int x, int y, Colour color) {
        Boolean[][] visited = new Boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0 ; j < size; j++) {
                visited[i][j] = false;
            }
        }
        return countBreaths(x, y, color, visited);
    }

    private int countBreaths(int x, int y, Colour color, Boolean[][] visited) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return 0;
        }
        int breaths = 0;
        visited[x][y] = true;
        if (board[x][y].equals(Colour.EMPTY)) {
            return 1;
        }
        if (!board[x][y].equals(color)) {
            return 0;
        }
        else {
            if (x + 1 < size && !visited[x+1][y]) {
                breaths += countBreaths(x + 1, y, color, visited);
            }
            if (x - 1 >= 0 && !visited[x-1][y]) {
                breaths += countBreaths(x - 1, y, color, visited);
            }
            if (y + 1 < size && !visited[x][y+1]) {
                breaths += countBreaths(x, y + 1, color, visited);
            }
            if (y - 1 >= 0 && !visited[x][y-1]) {
                breaths += countBreaths(x, y - 1, color, visited);
            }
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
        if (!whiteturn)
            turn++;
        if (move.isPass || move.giveUp)
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
        this.activeplayer = whiteturn ? Colour.BLACK : Colour.WHITE;
    }

    private boolean checkPostMove(Move move) {
        if (move.isPass || move.giveUp) {
            return true;
        }
        return board[move.x][move.y] != Colour.EMPTY;
    }

    public boolean performAndCheckMove(Move move, boolean whiteturn) {
        if (!fastCheckMove(move)) {
            return false;
        }
        MakeMove(move, whiteturn);
        /*for (int i = 0; i < size; i++) {
            for (int j = 0 ; j < size; j++) {
                System.out.print(board[i][j]);
            }
        }*/
        return checkPostMove(move);
    }
}
