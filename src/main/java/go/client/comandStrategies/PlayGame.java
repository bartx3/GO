package go.client.comandStrategies;

import go.client.UI.UI;
import go.communications.Request;
import go.communications.SocketFacade;
import go.game.GameState;
import go.game.Move;

import java.net.SocketException;
import java.util.function.BiFunction;

public class PlayGame implements CommandStrategy {

    @Override
    public Void apply(SocketFacade socketFacade, String[] strings, UI ui) {
        try {
            Request request = new Request("play", strings);
            socketFacade.send(request);
            String s = (String) socketFacade.receive();
            ui.promptMessage("Opponent found. Starting game against " + s);
            if (s.equals("Invalid size")) {
                return null;
            }
            while (true) {
                Object o = socketFacade.receive();
                if (o instanceof GameState) {
                    ui.showGameState((GameState) o);
                    if (((GameState) o).finished) {
                        break;
                    }
                } else if (o instanceof String) {
                    ui.showErrorMessage((String) o);
                } else {
                    throw new RuntimeException("Unexpected object received");
                }
                Move move = ui.getMove();
                socketFacade.send(move);
                if (move.giveUp) {
                    break;
                }
            }
        } catch (SocketException | ClassCastException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}