package go.client.UI.GUI;

import  javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class Welcome {
    @FXML
    Pane pane_welcome;
    @FXML
    private Button btn_start;

    protected Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void btnWelcome(ActionEvent event){
        try {
            stage.close();
            SceneManager.setScene("logging.fxml");
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
