package go.server.ClientHandler;

import go.communications.SocketFacade;

import java.util.concurrent.Semaphore;


public class SessionData {
    public final SocketFacade socket;
    public final String username;
    public boolean isPlaying;
    protected ClientObserver observer;
    public Semaphore semaphore = new Semaphore(1);

    public SessionData(SocketFacade socket, String username) {
        this.socket = socket;
        this.username = username;
        this.isPlaying = false;
        this.observer = new ClientObserver(socket.getSocket(), username);
    }

    public synchronized void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public void start() throws IllegalThreadStateException {
        observer.start();
    }

}
