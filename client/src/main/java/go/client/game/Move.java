package go.game;

public class Move {
    public final int x;
    public final int y;
    public final boolean whiteturn;
    public final boolean isPass;
    public final boolean giveUp;

    public Move(boolean white, boolean isPass) {
        this.whiteturn = white;
        this.isPass = isPass;
        this.giveUp = false;
        this.x = -1;
        this.y = -1;
    }

    public Move(boolean white, int x, int y) {
        this.whiteturn = white;
        this.isPass = false;
        this.giveUp = false;
        this.x=x;
        this.y=y;
    }
}
