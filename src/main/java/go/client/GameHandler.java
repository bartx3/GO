package go.client;

import go.client.UI.UI;
import go.communications.SocketFacade;

import static java.lang.Thread.sleep;

public class GameHandler implements Runnable {
    public GameHandler(SocketFacade socket, UI ui, String playername){
    }

    @Override
    public void run(){
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
