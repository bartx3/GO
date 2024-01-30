package go.client.UI.GUI;

import go.client.UI.UI;
import go.communications.Credentials;
import go.communications.Request;
import go.game.Colour;
import go.game.GameState;
import go.game.Move;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public class GUI implements UI {
    Stage stage;
    public static System.Logger logger = System.getLogger("GUILogger");

    public GUI(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void showGameState(go.game.GameState gameState) {
        logger.log(System.Logger.Level.INFO, "Wyświetlam stan gry");
        System.out.println("Turn: " + gameState.getTurn());
        System.out.println("Active player: " + gameState.getActivePlayer());
        for (int i = 0; i < gameState.getBoard().length; i++) {
            for (int j = 0; j < gameState.getBoard()[i].length; j++) {
                System.out.print(gameState.getBoard()[i][j] == Colour.EMPTY ? "+ " : gameState.getBoard()[i][j] == Colour.BLACK ? "B " : "W ");
            }
            System.out.println();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println(message);
    }

    @Override
    public Credentials getCredentials() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logging.fxml"));
            Scene scene = new Scene(loader.load());
            Logging logging = loader.getController();
            Stage newstage = new Stage();
            newstage.setScene(scene);
            newstage.showAndWait();
            String username = logging.getUsername();
            String password = logging.getPassword();
            return new Credentials(username, password);
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }

        return null;
    }

    @Override
    public void promptMessage(String line) {
        logger.log(System.Logger.Level.INFO, "Wyświetlam linijkę");
        System.out.println(line);
    }

    @Override
    public go.game.Move getMove(GameState gameState) {
        gamePane game=new gamePane(stage,19);
        if (game.action=="pass"){
            return new Move(true,false);
        }else if(game.action=="surrender"){
            return new Move(false, true);
        }else{
            return new Move(MoveHandler.getA(),MoveHandler.getB());
        }
    }


    @Override
    public void showWinner(String winner) {
        System.out.println("Winner is: " + winner);
    }

    @Override
    public Request getCommand() {
        Choice choose=new Choice();
        String command=choose.getCommand();
        String[] commandArray = command.split(" ");
        //command array without first element
        String[] args = new String[commandArray.length - 1];
        System.arraycopy(commandArray, 1, args, 0, commandArray.length - 1);
        try {
            return new Request(commandArray[0], args);
        } catch (Exception e) {
            System.out.println("Wrong command format");
        }
        return null;
    }

    @Override
    public void updateBoard(GameState gameState) {
        showGameState(gameState);
    }

}
