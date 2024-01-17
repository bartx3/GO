package go.communications;

import java.io.IOException;
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
    protected SynchronousQueue<Serializable> inQueue;

    public SocketFacade(Socket socket) throws SocketException {
        try{
            this.inQueue = new SynchronousQueue<>();
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            InputListener inputListener = new InputListener(in, inQueue);
            inputListener.start();
        }
        catch (Exception e){
            logger.log(System.Logger.Level.ERROR, e.getMessage());
            throw new SocketException();
        }
    }

    public synchronized void send(Serializable obj) throws SocketException {
        try {
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
            Serializable obj = (Serializable) inQueue.take();
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

    public boolean dataAvailable() {
        return !inQueue.isEmpty();
    }
}

class InputListener extends Thread {

    ObjectInputStream stream;
    final SynchronousQueue<Serializable> queue;
    public InputListener(ObjectInputStream stream, SynchronousQueue<Serializable> queue) {
        this.stream = stream;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Serializable response = (Serializable) stream.readObject();
                synchronized (queue) {
                    queue.put(response);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}