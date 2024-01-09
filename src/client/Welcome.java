package client.UI;

import  javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Text;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class Welcome {
    @FXML
    Pane pane_welcome;
    @FXML
    private Button btn_start;
    @FXML
    Text txt_welcome;

    protected Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void btnWelcome(ActionEvent event){
        try {
            Logging log_in=new Logging();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
