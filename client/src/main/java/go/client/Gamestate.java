package go.client;

public interface Gamestate {
    Move createMove();
    boolean checkMove(); //checks if move can be done (with server)
    void makeMove(); //makes the move and writes it to server

}
