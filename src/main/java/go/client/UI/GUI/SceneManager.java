package go.client.UI.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    public static void setScene(String path) {
        try{
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(path));

            loader.getController();
            //Parent root = ;
            Scene scene = new Scene(loader.load());//root
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();



        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
