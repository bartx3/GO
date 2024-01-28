package go.client.UI.GUI;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
public class CircleBuilder {
    public static ArrayList<Circle> circles = new ArrayList<>();
    double x,y;
    public CircleBuilder(SquareBuilder square){
        x= square.getX_coordinate();
        setCircleX(x);
        y= square.getY_coordinate();
        setCircleY(y);

        Circle circle=new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(25);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.TRANSPARENT);
        circles.add(circle);

        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new MoveHandler(circle);
            }
        });
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
