package go.client.UI.GUI;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


public class SquareBuilder {
    int x_coordinate;
    int y_coordinate;
    public SquareBuilder(int i, int n){
        x_coordinate=10+50*(i%n);
        y_coordinate= (int) (10+Math.floor(i/n));
        setX_coordinate(x_coordinate);
        setY_coordinate(y_coordinate);
        Rectangle square=new Rectangle(x_coordinate, y_coordinate, 50, 50);
        square.setFill(Color.TRANSPARENT);
        square.setStroke(Color.BLACK);
    }
    public void setX_coordinate(int x_coordinate){
        this.x_coordinate=x_coordinate;
    }
    public void setY_coordinate(int y_coordinate){
        this.y_coordinate=y_coordinate;
    }

    public int getX_coordinate() {
        return x_coordinate;
    }

    public int getY_coordinate() {
        return y_coordinate;
    }
}
