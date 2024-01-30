package go.client.UI.GUI;
import  javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
public class Choice {
    @FXML
    Pane pane_choice;
    @FXML
    private Button btn_logging;

    protected Stage stage;
    protected Scene scene;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene=scene;
    }

    public String command="co robic?";
    public void btn9(ActionEvent event){

    }
    public void btn13(ActionEvent event){

    }
    public void btn19(ActionEvent event){

    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
