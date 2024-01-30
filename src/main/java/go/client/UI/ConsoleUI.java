package go.client.UI;

import go.communications.Credentials;
import go.communications.Request;
import go.game.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUI implements UI {

    System.Logger logger = System.getLogger("ConsoleLogger");

    @Override
    public void showGameState(GameState gameState) {
        clearscreen();
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
    public Move getMove(GameState gameState) {
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
    public long chooseGame(ArrayList<Long> games) {
        System.out.println("Available games: ");
        for (Long game : games) {
            System.out.println(game);
        }
        System.out.println("Choose game: ");
        int game;
        String line = getLine();
        if (line.equals("m")) {
            return -1;
        }
        try {
            game = Integer.parseInt(line);
        } catch (Exception e) {
            System.out.println("Wrong game format");
            return -2;
        }
        return game;
    }

    @Override
    public void promptMessage(String line) {
        logger.log(System.Logger.Level.INFO, "Wyświetlam linijkę");
        System.out.println(line);
    }

    @Override
    public void showWinner(String winner) {
        if (winner == null) {
            System.out.println("Draw");
            return;
        }
        System.out.println(winner + " won");
    }

    private void clearscreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public Request getCommand() {
        System.out.println("Enter your command: ");
        String command = getLine();
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
    public void displayGame(Game game) {
        System.out.println("n for next move, p for previous move, m for main menu");
        System.out.println("Game id: " + game.getId());
        System.out.println("Player 1 (Black) : " + game.player1);
        System.out.println("Player 2 (White) : " + game.player2);
        System.out.println("Won by: " + game.getWinner());
        int iterator = 0;
        if (game.gameStates.size() == 0) {
            promptMessage("No moves recorded");
        }
        while (true) {
            GameState gameState = game.getGameState(iterator);
            if (gameState == null) {
                promptMessage("No move");
            }
            else
                showGameState(gameState);
            String line = getLine();
            if (line.equals("n")) {
                if (iterator == game.gameStates.size() - 1) {
                    System.out.println("No more moves");
                    continue;
                }
                iterator++;
            } else if (line.equals("p")) {
                if (iterator == 0) {
                    System.out.println("No more moves");
                    continue;
                }
                iterator--;
            } else if (line.equals("m")) {
                break;
            }
        }
    }

}
