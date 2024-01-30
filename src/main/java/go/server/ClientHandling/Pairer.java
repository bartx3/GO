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
                Player_Game player = queue.take();
                pl1name = player.playername;
                p1socket = player.socket;
                logger.log(System.Logger.Level.INFO, "Waiting for player 2");
                player = queue.take();
                pl2name = player.playername;
                p2socket = player.socket;
                new GameHandler(p1socket, p2socket, pl1name, pl2name, size).start();
                pl1name = null;
                p1socket = null;
                pl2name = null;
                p2socket = null;
                size = null;
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