package go.server.ClientHandling;

import go.communications.Request;
import go.communications.SocketFacade;
import go.game.Colour;
import go.game.Game;
import go.game.GameState;
import go.server.DB.DBFacade;

import java.net.SocketException;

public class GameHandler extends Thread {
    static final DBFacade db = go.server.Server.getDB();
    SocketFacade player1;
    SocketFacade player2;
    String pl1name;
    String pl2name;
    Game game;

    public GameHandler(SocketFacade player1, SocketFacade player2, String pl1name, String pl2name, Integer size) {
        // randomize who is black and who is white
        if (Math.random() < 0.5) {
            this.player1 = player1;
            this.player2 = player2;
            this.pl1name = pl1name;
            this.pl2name = pl2name;
        }
        else {
            this.player1 = player2;
            this.player2 = player1;
            this.pl1name = pl2name;
            this.pl2name = pl1name;
        }
        long id = db.newGame(pl1name, pl2name, size);
        this.game = new Game(id, pl1name, pl2name, size);
    }

    @Override
    public void run() {
        try {
            player1.send(new Request("start", pl2name));
            player1.send(Colour.WHITE);
            player2.send(new Request("start", pl1name));
            player2.send(Colour.BLACK);
            game.init();
            while (true) {

            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
