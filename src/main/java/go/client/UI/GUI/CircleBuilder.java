package go.client.UI.GUI;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
public class CircleBuilder {
    public ArrayList<Circle> circles = new ArrayList<>();
    double x,y;
    public CircleBuilder(SquareBuilder square){
        x= square.getX_coordinate();
        setCircleX(x);
        y= square.getY_coordinate();
        setCircleX(y);

        Circle circle=new Circle(x,y,25);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.TRANSPARENT);
        circles.add(circle);
    }

    public void setCircleX(double x) {
        this.x = x;
    }

    public void setCircleY(double y) {
        this.y=y;
    }

    public double getCircleX() {
        return x;
    }

    public double getCircleY() {
        return y;
    }
}
