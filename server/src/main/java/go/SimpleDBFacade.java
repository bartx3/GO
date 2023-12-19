package go;

import go.game.Game;
import go.game.GameState;

import java.util.*;

public class SimpleDBFacade implements DBFacade{
    TreeMap<String, String> users = new TreeMap<>();
    TreeMap<Long, Game> games = new TreeMap<>();

    @Override
    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        try {
            users.put(username, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveGame(Game game) {
        try {
            games.put(game.id, game);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Game loadGame() {
        return null;
    }

    public void saveGamestate(Game gamestate) {
    }

    public Game loadGamestate() {
        return null;
    }

    public void saveClientHandler(ClientHandler clientHandler) {
    }

    public ClientHandler loadClientHandler() {
        return null;
    }
}
