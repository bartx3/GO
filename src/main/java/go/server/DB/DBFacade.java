package go.server.DB;

import go.communications.Message;
import go.communications.PlayerCredentials;
import go.game.Game;

import java.util.ArrayList;

public interface DBFacade {
    boolean register(PlayerCredentials credentials);
    boolean login(PlayerCredentials credentials);
    boolean saveGame(Game game);
    long newGame(String player1, String player2, int size);
    Game loadGame(long id);
    ArrayList<Long> getGameIds();

    ArrayList<String> getUsernames();
}