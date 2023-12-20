package go;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ClientHandler
 * A class that handles a client connection
 * getting its username and adding it to the online users list
 * and checking every second if the socket is still connected.
 * If it fails at an interraction, this thread erases it from online client list
 *
 */
public class ClientOnlineHandler implements Runnable {

    static final System.Logger logger = System.getLogger("CHlogger");
    private final Socket socket;
    private String name;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientOnlineHandler(Socket socket) {
        super();
        this.socket = socket;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());



        }
        catch(Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
            return;
        }


    }

    public void run() {
        while (true) {
            try {
                name = (String) in.readObject();

                Server.onlineUsersSemaphore.acquire();
                if (Server.usersOnline.containsKey(name)) {
                    out.writeObject("taken");
                    Server.onlineUsersSemaphore.release();
                    return;
                }
                out.writeObject("accepted");
                Server.usersOnline.put(name, new SessionData(socket, name));
                Server.onlineUsersSemaphore.release();
                logger.log(System.Logger.Level.INFO, "User " + name + " logged in");
                Thread t = new Thread(new ClientHandler(socket, name, in, out));
                t.start();
                Thread.sleep(1000);
                if (socket.isClosed()) {
                    logger.log(System.Logger.Level.ERROR, "Klient " + name + " nieaktywny");
                    Server.onlineUsersSemaphore.acquire();
                    Server.usersOnline.remove(name);
                    Server.onlineUsersSemaphore.release();
                    return;
                }
            }
            catch(Exception e) {
                logger.log(System.Logger.Level.ERROR, e.getMessage());
                return;
            }
        }
    }


}