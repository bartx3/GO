package go.communications;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.SynchronousQueue;

public class SocketFacade {
    protected static final System.Logger logger = System.getLogger(SocketFacade.class.getName());
    protected Socket socket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public SocketFacade(Socket socket) throws SocketException {
        try{
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        }
        catch (Exception e){
            logger.log(System.Logger.Level.ERROR, e.getMessage());
            throw new SocketException();
        }
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

    public Socket getSocket() {
        return socket;
    }

    public synchronized Serializable receive() throws SocketException {
        try {
            Serializable obj = (Serializable) in.readObject();
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
