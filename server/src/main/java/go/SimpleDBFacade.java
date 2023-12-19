package go;

import go.game.Game;

import java.util.*;

public class SimpleDBFacade implements DBFacade {
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

    @Override
    public boolean login(String username, String password) {
        if (!users.containsKey(username)) {
            return false;
        }
        return checkPassword(username, password);
    }

    public boolean saveGame(Game game) {
        try {
            games.put(game.id, game);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Game loadGame(long id) {
        try {
            return games.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ArrayList<Long> getGameIds() {
        try {
            ArrayList<Long> ids = new ArrayList<>();
            for (Map.Entry<Long, Game> entry : games.entrySet()) {
                ids.add(entry.getKey());
            }
            return ids;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean checkPassword(String username, String password) {
        return users.get(username).equals(password);
    }
}
