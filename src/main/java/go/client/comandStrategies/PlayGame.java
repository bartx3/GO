package go.client.comandStrategies;

import go.client.UI.UI;
import go.communications.Request;
import go.communications.SocketFacade;
import go.game.Colour;
import go.game.GameState;
import go.game.Move;

import java.io.Serializable;
import java.net.SocketException;

import static go.client.Client.logger;
import static jdk.internal.joptsimple.internal.Strings.join;

public class PlayGame implements CommandStrategy {

    @Override
    public Void apply(SocketFacade socketFacade, String[] strings, UI ui) {
        try {
            logger.log(System.Logger.Level.INFO, "Looking for game");
            Request request = new Request("play", strings);
            socketFacade.send(request);
            Request s = (Request) socketFacade.receive();
            if (s.command.equals("error")) {
                ui.showErrorMessage(s.args[0]);
                return null;
            }
            ui.promptMessage("Opponent found. Starting game against " + s.args[0]);
            Colour playerColour = (Colour) socketFacade.receive();
            ui.promptMessage("You are playing as " + playerColour);
            while (true) {
                Serializable recieved = socketFacade.receive();
                if (recieved instanceof Request) {
                    Request r = (Request) recieved;
                    if (r.command.equals("error")) {
                        ui.showErrorMessage(join(r.args, " "));
                        return null;
                    }
                }
                if (recieved instanceof String) {
                    ui.promptMessage((String) recieved);
                    continue;
                }
                if (!(recieved instanceof GameState)) {
                    throw new RuntimeException("Unexpected object received");
                }
                GameState gameState = (GameState) socketFacade.receive();
                ui.updateBoard(gameState);
                if (gameState.finished) {
                    String winner = (String) socketFacade.receive();
                    ui.showWinner(winner);
                    break;
                }
                if (gameState.getActivePlayer() == playerColour) {
                    Move move = ui.getMove(gameState);
                    socketFacade.send(move);
                }
            }
        } catch (SocketException | ClassCastException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}