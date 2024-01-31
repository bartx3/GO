package go.client.UI.GUI;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class Choice {
    @FXML
    Pane pane_choice;
    @FXML
    private Button btn9,btn13,btn19,btnLoad;

    protected Stage stage;
    protected Scene scene;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene=scene;
    }

    public String command="co robic?";
    public void btn9(MouseEvent event){
        setCommand("play");
    }
    public void btn13(MouseEvent event){
        setCommand("play");
        Stage stage = (Stage) btn13.getScene().getWindow();
        stage.close();
    }
    public void btn19(MouseEvent event){
        setCommand("play");
        Stage stage = (Stage) btn19.getScene().getWindow();
        stage.close();
    }
    public void gameLoad(MouseEvent event){
        setCommand("history");
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
