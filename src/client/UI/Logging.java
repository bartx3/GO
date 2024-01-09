package client.UI;
import communications.Credentials;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Text;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Text;
import javafx.event.ActionEvent;
public class Logging {
    @FXML
    Pane pane_logging;
    @FXML
    private Button btn_logging;
    @FXML
    Text txt_username, txt_password;
    @FXML
    TextField txtfield_username, txtfield_password;

    protected Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public Credentials btnLogged(ActionEvent event){
        String username="";
        String password="";
        try {
            username=txtfield_username.getText();
            password=txtfield_password.getText();

            Choice choose = new Choice();

        } catch (Exception e) {
            // TODO: handle exception
        }
        return new Credentials(username, password);
    }
}
