package server.DB;

import communications.Credentials;
import go.game.Game;

import java.util.ArrayList;

public interface DBFacade {
    boolean register(Credentials credentials);
    boolean login(Credentials credentials);
    boolean saveGame(Game game);
    Game loadGame(long id);
    ArrayList<Long> getGameIds();

    ArrayList<String> getUsernames();

    long generateGameId();
}