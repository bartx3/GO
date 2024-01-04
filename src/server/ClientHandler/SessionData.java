package server.ClientHandler;

import communications.SocketFacade;

import java.net.Socket;

public class SessionData {
    public final SocketFacade socket;
    public final String username;
    public boolean isPlaying;

    public SessionData(SocketFacade socket, String username) {
        this.socket = socket;
        this.username = username;
        this.isPlaying = false;
    }

    public synchronized void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
