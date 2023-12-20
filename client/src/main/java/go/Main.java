package go;

import go.game.GameState;
import go.game.Move;
import go.ui.BasicUIFacade;
import go.ui.ConsoleUI;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;

public class Main {

    static Socket socket;
    static BasicUIFacade ui = new ConsoleUI();
    static System.Logger logger = System.getLogger("ClientLogger");
    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 8080);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ui.putLine("Connected to server");

            Object response = null;
            do {
                String name = ui.getLine();
                out.writeObject(name);
                response = in.readObject();
            } while (!response.equals("accepted"));
            ui.putLine("Logged in");

            do {
                String command = ui.getLine();
                String[] commandParts = command.split(" ");
                for (String part : commandParts) {
                    out.writeObject(part);
                }
                response = in.readObject();
                if (command.equals("list")) {
                    logger.log(System.Logger.Level.INFO, "Listuję innych użytkowników");
                    if (!(response instanceof String[])) {
                        ui.putLine("Nie trafiłem na seta");
                        break;
                    }
                    String[] users = (String[]) response;
                    ui.showUserList(users);
                    continue;
                }
                if (response.equals("accepted")) {
                    ui.putLine("Challenge accepted");
                    String color = (String) in.readObject();
                    playGame();
                }
                if (response.equals("challange")) {
                    ui.putLine("Challenged by " + (String) in.readObject());
                    String answer = ui.getLine();
                    out.writeObject(answer);
                    if (answer.equals("accepted")) {
                        String color = (String) in.readObject();
                        playGame();
                    }
                }
            } while (socket.isConnected());




        }
        catch(Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }
    }

    public static void playGame() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ui.putLine("Playing with " + (String) in.readObject());
            GameState gameState;
            do {
                Object object = in.readObject();
                if (object instanceof String) {
                    String response = (String) object;
                    if (response.equals("gameover")) {
                        ui.putLine("Game over");
                        break;
                    }
                }
                gameState = (GameState) object;
                ui.showGameState(gameState);
                String response;
                do {
                    Move move = ui.getMove();
                    out.writeObject(move);
                    response = (String) in.readObject();
                } while (response.equals("invalid"));
            } while (true);
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }
    }
}
