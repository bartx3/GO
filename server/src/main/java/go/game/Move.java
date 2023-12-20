package go.game;

public class Move {
    private final int x;
    private final int y;
    private final boolean whiteturn;
    private final boolean isPass;

    public Move(boolean white, boolean isPass) {
        this.whiteturn = white;
        this.isPass = isPass;
        this.x = -1;
        this.y = -1;
    }

    public Move(boolean white, int x, int y) {
        this.whiteturn = white;
        this.isPass = false;
        this.x=x;
        this.y=y;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean getWhiteTurn(){
        return whiteturn;
    }
    public boolean getIsPass(){
        return isPass;
    }
}
