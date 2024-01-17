package go.server.ClientHandling;

import go.communications.SocketFacade;

public class GameHandler extends Thread {
    public GameHandler(SocketFacade player1, SocketFacade player2, ) {
    }

    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
