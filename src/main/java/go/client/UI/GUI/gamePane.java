package go.client.UI.GUI;

import go.game.Move;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class gamePane {
    @FXML
    Pane gamePane, playingPane;
    @FXML
    private Button btnPass, btnSurrender;
    protected Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
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
        new Move(true,false);
    }
    public void btnSurrender(ActionEvent event){
        new Move(false, true);
    }
}
