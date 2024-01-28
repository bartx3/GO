package go.client.UI.GUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MoveHandler {
    Circle circle=new Circle();
    public MoveHandler(Circle circle){
        this.circle=circle;
    }
    public boolean isTaken(Circle circle){
        boolean flag=false;
        if(circle.getFill().equals(Color.WHITE)||circle.getFill().equals(Color.BLACK)) {
                flag=true;
        }
        return flag;
    }
    public void makeMove (Circle circle){
        if(isTaken(circle)==false){
            int a,b;
            double x=circle.getCenterX();
            double y=circle.getCenterY();
            int n=1;
            
            for(int j=10;j<=910;j++){
                if(x==j){
                    a=n;
                }
                if(y==j){
                    b=n;
                }
                n++;
            }
            Move move = new Move(a, b);
        }
    }
}
