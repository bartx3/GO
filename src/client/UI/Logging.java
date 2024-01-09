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
    String username="";
    String password="";
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public btnLogged(ActionEvent event){

        try {
            username=txtfield_username.getText();
            setUsername(username);
            password=txtfield_password.getText();
            setPassword(password);

            Choice choose = new Choice();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    private void setPassword(String password){
        this.password=password;
    }
    private String getPassword() {
        return password;
    }

    private void setUsername(String username){
        this.username=username;
    }
    private String getUsername() {
        return username;
    }
    public Credentials getCredentials() {
        username=getUsername();
        password=getPassword();
        return new Credentials(username, password);
    }


}
