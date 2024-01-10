package go.server.ClientHandler;

import static java.lang.Thread.sleep;

public class SGameHandler implements Runnable {
    public SGameHandler(SessionData p1, SessionData p2) {
        //throw new UnsupportedOperationException("Not supported yet.");
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
