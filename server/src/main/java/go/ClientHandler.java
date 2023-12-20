package go;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ClientHandler extends Thread {
    Logger logger = Logger.getLogger("ClientHandlerLogger");
    private final Socket socket;
    private final String username;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        }
        catch(Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = (String) in.readObject();
                if (message.equals("exit")) {
                    socket.close();
                    return;
                }
                if (message.equals("list")) {
                    Server.onlineUsersSemaphore.acquire();
                    out.writeObject(Server.usersOnline.keySet());
                    Server.onlineUsersSemaphore.release();
                    continue;
                }
                if (message.equals("challenge")) {
                    String opponent = (String) in.readObject();
                    Server.onlineUsersSemaphore.acquire();
                    if (Server.usersOnline.containsKey(opponent)) {
                        
                    }
                    else {
                        out.writeObject("rejected");
                        continue;
                    }
                }
            }
            catch(Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

}
