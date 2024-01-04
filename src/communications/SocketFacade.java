package communications;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketFacade {
    protected Socket socket;
    protected ObjectInputStream in;
    protected ObjectOutputStream out;

    public SocketFacade(Socket socket) throws Exception{
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public void send(Object obj) throws Exception {
        out.writeObject(obj);
    }

    public Object receive() throws Exception {
        return in.readObject();
    }
}
