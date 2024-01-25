package go.server.DB;

import go.communications.Credentials;
import go.game.Game;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SimpleDBFacade implements DBFacade {
    TreeMap<String, String> users = new TreeMap<>();
    TreeMap<Long, Game> games = new TreeMap<>();

    @Override
    public synchronized boolean register(Credentials credentials) {
        if (users.containsKey(credentials.getUsername())) {
            return false;
        }
        try {
            users.put(credentials.getUsername(), credentials.getPassword());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public synchronized boolean login(Credentials credentials) {
        if (!users.containsKey(credentials.getUsername())) {
            return false;
        }
        return checkPassword(credentials.getUsername(), credentials.getPassword());
    }

    @Override
    public synchronized boolean saveGame(Game game) {
        try {
            games.put(game.id, game);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public synchronized Game loadGame(long id) {
        try {
            return games.get(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public synchronized long newGame(String player1, String player2, int size) {
        try {
            long id = generateGameId();
            games.put(id, new Game(id, player1, player2, size));
            return id;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized ArrayList<Long> getGameIds() {
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



    @Override
    public synchronized ArrayList<String> getUsernames() {
        try {
            ArrayList<String> usernames = new ArrayList<>();
            for (Map.Entry<String, String> entry : users.entrySet()) {
                usernames.add(entry.getKey());
            }
            return usernames;
        } catch (Exception e) {
            return null;
        }
    }

    private synchronized long generateGameId() {
        try {
            return games.lastKey() + 1;
        } catch (Exception e) {
            return 0;
        }
    }



    private boolean checkPassword(String username, String password) {
        return users.get(username).equals(password);
    }
}
