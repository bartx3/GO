package go.server;

import go.server.ClientHandling.Accepter;
import go.server.ClientHandling.Pairer;
import go.server.DB.DBFacade;
import go.server.DB.MongoDBFacade;
import go.server.DB.SimpleDBFacade;

import java.io.IOException;
import java.util.HashMap;

public class Server {
    public static final System.Logger logger = System.getLogger("server");
    static final HashMap<String, Pairer> pairers = new HashMap<>();
    static final HashMap<Integer, Accepter> accepters = new HashMap<>();
    static final DBFacade db = new MongoDBFacade();
    public static void main(String[] args) throws IOException {
        accepters.put(8080, new Accepter(8080));
        pairers.put("19", new Pairer(19));
        for (Accepter accepter : accepters.values()) {
            accepter.start();
        }
        for (Pairer pairer : pairers.values()) {
            pairer.start();
        }
    }

    public static Pairer getPairer(String key) {
        return pairers.get(key);
    }

    public static DBFacade getDB() {
        return db;
    }
}
