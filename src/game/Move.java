package game;

public class Move implements java.io.Serializable {
    public final int x;
    public final int y;
    //public final boolean whiteturn;
    public final boolean isPass;
    public final boolean giveUp;

    public Move(boolean isPass, boolean giveUp) {
        this.isPass = isPass;
        this.giveUp = giveUp;
        this.x = -1;
        this.y = -1;
    }

    public Move(int x, int y) {
        this.isPass = false;
        this.giveUp = false;
        this.x=x;
        this.y=y;
    }
}
