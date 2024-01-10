package go.client.UI;

import go.communications.Packet;
import go.communications.Credentials;
import go.game.*;

import java.util.Scanner;

public class ConsoleUI implements UI {

    System.Logger logger = System.getLogger("ConsoleLogger");

    @Override
    public void showGameState(GameState gameState) {
        clearscreen();
        logger.log(System.Logger.Level.INFO, "Wyświetlam stan gry");
        System.out.println("Turn: " + gameState.getTurn());
        for (int i = 0; i < gameState.getBoard().length; i++) {
            for (int j = 0; j < gameState.getBoard()[i].length; j++) {
                System.out.print(gameState.getBoard()[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println(message);
    }

    public String getLine() {
        //clearscreen();
        Scanner scan=new Scanner(System.in);
        String line = scan.nextLine();
        return line;
    }

    public Credentials getCredentials() {
        System.out.println("Enter your username: ");
        String username = getLine();
        System.out.println("Enter your password: ");
        String password = getLine();
        return new Credentials(username, password);
    }

    @Override
    public Move getMove() {
        do {
            System.out.println("Enter your move: ");
            String move = getLine();
            if (move.equals("p")) {
                return new Move(true, false);
            } else if (move.equals("r")) {
                return new Move(false, true);
            } else {
                String[] moveArray = move.split(" ");
                try {
                    return new Move(Integer.parseInt(moveArray[0]), Integer.parseInt(moveArray[1]));
                } catch (Exception e) {
                    System.out.println("Wrong move format");
                }
            }
        } while (true);
    }

    @Override
    public void showUserList(String[] games) {
        System.out.println("Available players: ");
        for (String game : games) {
            System.out.println(game);
        }
    }

    @Override
    public void promptMessage(String line) {
        logger.log(System.Logger.Level.INFO, "Wyświetlam linijkę");
        System.out.println(line);
    }

    @Override
    public void showWinner(String winner) {
        System.out.println("Winner is: " + winner);
    }

    private void clearscreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public Packet getCommand() {
        System.out.println("Enter your command: ");
        String command = getLine();
        String[] commandArray = command.split(" ");
        try {
            return new Packet(commandArray[0], commandArray[1]);
        } catch (Exception e) {
            System.out.println("Wrong command format");
        }
        return null;
    }

    @Override
    public boolean getConfirmation(String message) {
        System.out.println(message);
        String confirmation = getLine();
        if (confirmation.equals("y")) {
            return true;
        } else {
            return false;
        }
    }
}
