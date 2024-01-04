package server;

import communications.SocketFacade;
import server.ClientHandler.ClientHandler;
import server.ClientHandler.SessionData;
import server.DB.DBFacade;
import server.DB.SimpleDBFacade;

import java.net.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;


/**
 * Our main class of our server. Gets control of our client sockets and gives to the ClientOnlineHandlers
 */
public class Server {
    public static final DBFacade db = new SimpleDBFacade();
    static final System.Logger logger = System.getLogger("ServerLogger");
    public static final Map<String, SessionData> usersOnline = Collections.synchronizedMap( new HashMap<>());

    public static void main(String[] args) {
        final int PORT = 8080;

        ServerSocket socket;
        try {
            socket = new ServerSocket(PORT);

            while(true) {
                logger.log(System.Logger.Level.INFO, "Ready for new client");
                Socket client = socket.accept();
                logger.log(System.Logger.Level.INFO, "Client connected");
                Thread t = new Thread(new ClientHandler(client));
                logger.log(System.Logger.Level.INFO, "Client handler created");
                t.start();
            }
        }
        catch(Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }

    }
}