package go.client.UI.GUI;

import go.communications.Credentials;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.concurrent.Semaphore;

import static go.client.UI.GUI.GUI.logger;

public class Logging {
    @FXML
    Pane pane_logging;
    @FXML
    private Button btn_logging;
    @FXML
    TextField txtfield_username, txtfield_password;

    public void btnLogged(MouseEvent event){
        logger.log(System.Logger.Level.INFO, "Przycisk zaloguj");
        Stage stage = (Stage) btn_logging.getScene().getWindow();
        stage.close();
    }

    public String getUsername(){
        return txtfield_username.getText();
    }
    public String getPassword(){
        return txtfield_password.getText();
    }
}
