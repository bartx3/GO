package go;

import java.net.*;
import java.util.HashMap;
import java.util.concurrent.Semaphore;


/**
 * Our main class of our server. Gets control of our client sockets and gives to the ClientOnlineHandlers
 */
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
                logger.log(System.Logger.Level.INFO, "Ready for new client");
                Socket client = socket.accept();
                logger.log(System.Logger.Level.INFO, "Client connected");
                Thread t = new Thread(new ClientOnlineHandler(client));
                logger.log(System.Logger.Level.INFO, "Client handler created");
                t.start();
            }

        }
        catch(Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }

    }
}