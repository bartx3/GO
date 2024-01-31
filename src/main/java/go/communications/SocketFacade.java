package go.communications;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.ResourceBundle;

public class SocketFacade {
    protected static final System.Logger logger = new System.Logger() {
        @Override
        public String getName() {
            return "Socketlogger";
        }

        @Override
        public boolean isLoggable(Level level) {
            return level.getSeverity() > Level.INFO.getSeverity();
        }

        @Override
        public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
            if (isLoggable(level)) {
                logger.log(level, bundle, msg, thrown);
            }
        }

        @Override
        public void log(Level level, ResourceBundle bundle, String format, Object... params) {
            if (isLoggable(level)) {
                logger.log(level, bundle, format, params);
            }
        }
    };
    protected Socket socket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public SocketFacade(Socket socket) throws SocketException {
        logger.log(System.Logger.Level.INFO, "Create new socket handler");
        try{
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e){
            logger.log(System.Logger.Level.ERROR, e.getMessage());
            throw new SocketException();
        }
        logger.log(System.Logger.Level.INFO, "New Socket created");
    }

    public synchronized void send(Serializable obj) throws SocketException {
        try {
            logger.log(System.Logger.Level.INFO, "Sending object");
            if (socket.isClosed()){
                logger.log(System.Logger.Level.ERROR, "Dupaprint");
            }
            out.writeObject(obj);
        }
        catch (Exception e){
            logger.log(System.Logger.Level.ERROR, e.getMessage());
            throw new SocketException();
        }
        logger.log(System.Logger.Level.INFO, "Sent object");
    }

    //okazuje się być redundantna, ale już dodałem
    public boolean test() throws SocketException {
        try {
            out.writeObject(new Contest());
            return true;
        }
        catch (Exception e){
            throw new SocketException();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public synchronized Serializable receive() throws SocketException {
        try {
            Serializable obj;
            do {
                obj = (Serializable) in.readObject();
            } while (obj instanceof Contest);
            logger.log(System.Logger.Level.INFO, "Received object");
            return obj;
        }
        catch (Exception e){
            throw new SocketException();
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }
}
class Contest implements Serializable{}
