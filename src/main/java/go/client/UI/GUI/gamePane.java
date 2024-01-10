package go.client.UI.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    public void getMoveCoordinates(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY){
            MoveController.coordinates.add(event.getX());
            MoveController.coordinates.add(event.getY());
        }
    }
    public void btnPass(ActionEvent event){}
    public void btnSurrender(ActionEvent event){}
}
