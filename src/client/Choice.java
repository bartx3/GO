package client.UI;
import  javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Text;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Text;
import javafx.event.ActionEvent;
public class Choice {
    @FXML
    Pane pane_choice;
    @FXML
    private Button btn_logging;
    @FXML
    Text txt_choice;

    protected Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void btn9(ActionEvent event){
        try {
            gamePane gamepane = new gamePane(stage,9);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void btn13(ActionEvent event){
        try {
            gamePane gamepane = new gamePane(stage,13);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void btn19(ActionEvent event){
        try {
            gamePane gamepane = new gamePane(stage,19);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void gameLoad(ActionEvent event){

    }
}
