package go;

import java.net.*;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Server {
    static final DBFacade db = new SimpleDBFacade();
    static final System.Logger logger = System.getLogger("ServerLogger");

    public static Semaphore onlineUsersSemaphore = new Semaphore(1);
    public static HashMap<String, SessionData> usersOnline = new HashMap<>();
    public static void main(String[] args) {
        final int PORT = 8080;

        ServerSocket socket;
        try {
            socket = new ServerSocket(PORT);

            while(true) {
                Socket client = socket.accept();
                logger.log(System.Logger.Level.INFO, "Client connected");
                (new ClientOnlineHandler(client)).start();
            }

        }
        catch(Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }

    }
}