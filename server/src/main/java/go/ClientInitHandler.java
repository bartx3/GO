package go;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ClientHandler
 * A class that handles a client connection
 * getting its username and adding it to the online users list
 * and checking every second if the client is still connected
 */
public class ClientInitHandler extends Thread{

    static final System.Logger logger = System.getLogger("CHlogger");

    public ClientInitHandler(Socket socket) {
        ObjectInputStream in;
        ObjectOutputStream out;
        String name;

        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            name = (String) in.readObject();

            Server.onlineUsersSemaphore.acquire();
            if (Server.usersOnline.containsKey(name)) {
                out.writeObject("taken");
                Server.onlineUsersSemaphore.release();
                return;
            }
            out.writeObject("accepted");
            Server.usersOnline.put(name, socket);
            Server.onlineUsersSemaphore.release();


            while (true) {
                Thread.sleep(1000);
                if (socket.isClosed()) {
                    Server.onlineUsersSemaphore.acquire();
                    Server.usersOnline.remove(name);
                    Server.onlineUsersSemaphore.release();
                    return;
                }
            }

        }
        catch(Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
            return;
        }


    }

    public void run() {

    }


}