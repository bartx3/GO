package go.client.UI.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class gamePane {
    @FXML
    Pane gamePane, playingPane;
    @FXML
    private Button btnPass, btnSurrender;
    protected Stage stage;
    protected Scene scene;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene=scene;
    }

    public String action="none";

    public void setAction(String action) {
        this.action = action;
    }

    public gamePane(Stage stage, int n){
        for(int i=0;i<=n*n;i++){
            SquareBuilder square=new SquareBuilder(i,n);
            //playingPane.getChildren().add(square);
            CircleBuilder circle=new CircleBuilder(square);
            //playingPane.getChildren().add(circle);
        }
    }
    public void btnPass(ActionEvent event){
        setAction("pass");
    }
    public void btnSurrender(ActionEvent event){
        setAction("surrender");
    }
}
