package go.server;

import go.server.ClientHandling.Accepter;
import go.server.ClientHandling.Pairer;
import go.server.DB.DBFacade;
import go.server.DB.SimpleDBFacade;

import java.io.IOException;
import java.util.HashMap;

public class Server {
    public static final HashMap<Integer, Pairer> pairers = new HashMap<>();
    public static final HashMap<Integer, Accepter> accepters = new HashMap<>();
    public static final DBFacade db = new SimpleDBFacade();
    public static void main(String[] args) throws IOException {
        accepters.put(8000, new Accepter(8080));
        pairers.put(19, new Pairer(19));
    }
}
