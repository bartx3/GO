package go.client.comandStrategies;

import go.client.UI.UI;
import go.communications.Request;
import go.communications.SocketFacade;
import go.game.Game;

import java.net.SocketException;
import java.util.ArrayList;

import static go.client.Client.logger;

public class History implements CommandStrategy {
    @Override
    public Void apply(SocketFacade socketFacade, String[] args, UI ui) {
        Request request = new Request("history", args);
        try {
            socketFacade.send(request);

            long gameid;
            while (true) {
                ArrayList<Long> games = (ArrayList<Long>) socketFacade.receive();
                do {
                    gameid = ui.chooseGame(games);
                } while (gameid != -1 && !games.contains(gameid));
                if (gameid == -1) {
                    logger.log(System.Logger.Level.INFO, "Exiting to main menu");
                    Request r = new Request("main menu");
                    socketFacade.send(r);
                    return null;
                } else {
                    Request r = new Request("show", Long.toString(gameid));
                    socketFacade.send(r);
                    ui.displayGame((Game) socketFacade.receive());
                    continue;
                }
            }
        } catch (SocketException e) {
            ui.showErrorMessage(e.getMessage());
        } catch (ClassCastException e) {
            ui.showErrorMessage("Unexpected response from server. Got " + e.getMessage());
        } catch (Exception e) {
            ui.showErrorMessage(e.getMessage());
        }

        return null;
    }
}
