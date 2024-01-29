package go.client.UI.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Welcome {
    @FXML
    Pane pane_welcome;
    @FXML
    private Button btn_start;

    protected Stage stage;
    protected Scene scene;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene=scene;
    }
    public void btnWelcome(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logging.fxml"));
            Scene scene = new Scene(loader.load());//root
            Logging log=loader.getController();
            log.setStage(stage);
            log.setScene(scene);
            stage.setScene(scene);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
