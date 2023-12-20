package go;

import java.net.Socket;

public class SessionData {
    public final Socket socket;
    public final String username;
    public boolean isPlaying;

    public SessionData(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
        this.isPlaying = false;
    }

    public synchronized void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
