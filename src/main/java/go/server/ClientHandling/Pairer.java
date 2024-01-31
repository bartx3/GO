package go.server.ClientHandling;

import go.communications.SocketFacade;

import java.util.concurrent.SynchronousQueue;

public class Pairer extends Thread {
    private static System.Logger logger = System.getLogger("Pairer");
    int size;
    private final SynchronousQueue<Player_Game> queue;

    public Pairer(int size) {
        this.size = size;
        this.queue = new SynchronousQueue<>();
    }

    @Override
    public void run() {
        String pl1name = null;
        SocketFacade p1socket = null;
        String pl2name = null;
        SocketFacade p2socket = null;
        Integer size = null;
        while (true) {
            try {
                size = this.size;
                logger.log(System.Logger.Level.INFO, "Waiting for player 1");
                Player_Game player1 = queue.take();
                pl1name = player1.playername;
                p1socket = player1.socket;
                logger.log(System.Logger.Level.INFO, "Waiting for player 2");
                Player_Game player2 = queue.take();
                pl2name = player2.playername;
                p2socket = player2.socket;
                GameHandler gh = new GameHandler(p1socket, p2socket, pl1name, pl2name, size);
                gh.start();
                player1.handler = gh;
                player2.handler = gh;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public GameHandler addPlayer(String name, SocketFacade socket) throws IllegalArgumentException {
        logger.log(System.Logger.Level.INFO, "Adding player " + name + " to queue");
        Player_Game player = new Player_Game(name, socket, null);
        try {
            queue.put(player);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (player.handler == null) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return player.handler;
    }
}

class Player_Game {
    public String playername;
    public SocketFacade socket;
    public GameHandler handler;

    public Player_Game(String playername, SocketFacade socket, GameHandler handler) {
        this.playername = playername;
        this.socket = socket;
        this.handler = handler;
    }
}