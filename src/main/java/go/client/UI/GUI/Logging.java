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

public class Logging {
    @FXML
    Pane pane_logging;
    @FXML
    private Button btn_logging;
    @FXML
    TextField txtfield_username, txtfield_password;

    protected Stage stage;
    protected Scene scene;
    String username="";
    String password="";
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setScene(Scene scene){
        this.scene=scene;
    }
    public void btnLogged(MouseEvent event){

        try {
            username=txtfield_username.getText();
            setUsername(username);
            password=txtfield_password.getText();
            setPassword(password);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/choice.fxml"));
            Scene scene = new Scene(loader.load());//root
            Choice choose=loader.getController();
            choose.setStage(stage);
            choose.setScene(scene);
            stage.setScene(scene);

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
