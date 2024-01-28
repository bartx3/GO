package go.client.UI.GUI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MoveHandler {
    static int a=0;
    static int b=0;
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
            double x=circle.getCenterX();
            double y=circle.getCenterY();
            int n=1;
            
            for(int j=10;j<=910;j++){
                if(x==j){
                    setA(n);
                }
                if(y==j){
                    setB(n);
                }
                n++;
            }
        }
    }

    public static void setA(int a) {
        MoveHandler.a = a;
    }

    public static void setB(int b) {
        MoveHandler.b = b;
    }

    public static int getA() {
        return a;
    }

    public static int getB() {
        return b;
    }
}
