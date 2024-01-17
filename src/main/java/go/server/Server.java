package go.server;

import go.server.DB.DBFacade;
import go.server.DB.SimpleDBFacade;

public class Server {
    public static final int PORT = 8080;

    public static final DBFacade db = new SimpleDBFacade();
    public static void main(String[] args) {

    }
}
