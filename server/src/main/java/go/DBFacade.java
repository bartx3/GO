package go;

import go.game.Game;

import java.util.ArrayList;

public interface DBFacade {
    boolean register(String username, String password);
    boolean login(String username, String password);
    boolean saveGame(Game game);
    Game loadGame(long id);
    ArrayList<Long> getGameIds();

}