package go.server.ClientHandling;

import go.communications.SocketFacade;
import go.game.Game;
import go.server.DB.DBFacade;

public class GameHandler extends Thread {
    static final DBFacade db = go.server.Server.getDB();
    SocketFacade player1;
    SocketFacade player2;
    String pl1name;
    String pl2name;
    Game game;

    public GameHandler(SocketFacade player1, SocketFacade player2, String pl1name, String pl2name, Integer size) {
        this.player1 = player1;
        this.player2 = player2;
        this.pl1name = pl1name;
        this.pl2name = pl2name;
        long id = db.newGame(pl1name, pl2name, size);
        this.game = new Game(id, pl1name, pl2name, size);
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
