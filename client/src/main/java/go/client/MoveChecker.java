package go.client;

public class MoveChecker implements Gamestate{
    Move move;

    @Override
    public Move createMove() {
        return move;
    }

    @Override
    public boolean checkMove() {
        int checked_x=move.getX();
        int checked_y=move.getY();
        return false;
    }

    @Override
    public void makeMove() {

    }
}
