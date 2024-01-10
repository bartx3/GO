package go.server.ClientHandler;

import go.communications.SocketFacade;


public class SessionData {
    public final SocketFacade socket;
    public final String username;
    public boolean isPlaying;
    protected ClientObserver observer;

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
