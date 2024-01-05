package server.ClientHandler;

import server.Server;

import java.net.Socket;

public class ClientObserver extends Thread {
    protected Socket socket;
    protected String username;

    public ClientObserver(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    @Override
    public void run() {
        while (true) {
            if (socket.isClosed()) {
                Server.usersOnline.remove(username);
                break;
            }
        }
    }
}
