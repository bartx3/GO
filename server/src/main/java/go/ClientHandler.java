package go;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;


/**
 * Our handler for client interraction.
 * It is meant to allow checking the list of other connected clients,
 * challenge them and accept or decline challanges
 */
public class ClientHandler implements Runnable {
    Logger logger = Logger.getLogger("ClientHandlerLogger");
    private final Socket socket;
    private final String username;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientHandler(Socket socket, String username, ObjectInputStream in, ObjectOutputStream out) {
        super();
        this.socket = socket;
        this.username = username;
        this.in = in;
        this.out = out;
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
                    logger.log(Level.INFO, "Wyświetlamy listę pozostałych użytkowników dla " + this.username);
                    Server.onlineUsersSemaphore.acquire();
                    out.writeObject(Server.usersOnline.keySet().toArray());
                    Server.onlineUsersSemaphore.release();
                    continue;
                }
                if (message.equals("challenge")) {
                    Server.onlineUsersSemaphore.acquire();
                    if (Server.usersOnline.get(username).isPlaying) {
                        out.writeObject("rejected");
                        continue;
                    }
                    String opponent = (String) in.readObject();
                    if (!Server.usersOnline.containsKey(opponent)) {
                        out.writeObject("rejected");
                        Server.onlineUsersSemaphore.release();
                        continue;
                    }

                    //ToDo: jakiś lock na opponentSession i mój session
                    SessionData opponentSession = Server.usersOnline.get(opponent);
                    Server.onlineUsersSemaphore.release();
                    synchronized (opponentSession) {
                        if (opponentSession.isPlaying) {
                            out.writeObject("rejected");
                            continue;
                        }
                        Socket opponentSocket = opponentSession.socket;
                        ObjectInputStream opponentIn = new ObjectInputStream(opponentSocket.getInputStream());
                        ObjectOutputStream opponentOut = new ObjectOutputStream(opponentSocket.getOutputStream());
                        opponentOut.writeObject("challenge");
                        opponentOut.writeObject(username);
                        if (opponentIn.readObject().equals("accepted")) {
                            opponentSession.setIsPlaying(true);
                            out.writeObject("accepted");

                            (new GameHandler(username, opponentSession.username)).start();
                        }
                        else {
                            out.writeObject("rejected");

                        }
                    }
                }
            }
            catch(Exception e) {
                logger.log(Level.SEVERE, e.getMessage());
                try {
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                return;
            }
        }
    }

}
