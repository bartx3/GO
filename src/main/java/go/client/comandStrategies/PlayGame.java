package go.client.comandStrategies;

import go.client.UI.UI;
import go.communications.Request;
import go.communications.SocketFacade;
import go.game.Colour;
import go.game.GameState;
import go.game.Move;

import java.net.SocketException;

public class PlayGame implements CommandStrategy {

    @Override
    public Void apply(SocketFacade socketFacade, String[] strings, UI ui) {
        try {
            Request request = new Request("play", strings);
            socketFacade.send(request);
            Request s = (Request) socketFacade.receive();
            if (s.equals("Invalid size")) {
                ui.showErrorMessage("Invalid size");
                return null;
            }
            ui.promptMessage("Opponent found. Starting game against " + s);
            Colour playerColour = (Colour) socketFacade.receive();
            ui.promptMessage("You are playing as " + playerColour);
            while (true) {
                GameState gameState = (GameState) socketFacade.receive();
                ui.updateBoard(gameState);
                if (gameState.finished) {
                    ui.promptMessage("Game finished");
                    break;
                }
                if (gameState.getCurrentPlayer() == playerColour) {
                    Move move = ui.getMove();
                    socketFacade.send(move);
                }
            }
        } catch (SocketException | ClassCastException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}