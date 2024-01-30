package go.server.ClientHandling;

//import go.client.UI.ConsoleUI;
import go.communications.Request;
import go.communications.SocketFacade;
import go.game.*;
import go.server.DB.DBFacade;

import java.io.Serializable;
import java.net.SocketException;

import static go.server.Server.logger;

public class GameHandler extends Thread {
    static final DBFacade db = go.server.Server.getDB();
    SocketFacade player1;
    SocketFacade player2;
    String pl1name;
    String pl2name;
    Game game;

    private void test_sockets() throws SocketException {
        player1.test();
        player2.test();
    }

    public GameHandler(SocketFacade player1, SocketFacade player2, String pl1name, String pl2name, Integer size) {
        logger.log(System.Logger.Level.INFO, "Starting new game handler");
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
            player1.send(Colour.BLACK);
            player2.send(new Request("start", pl1name));
            player2.send(Colour.WHITE);
            game.init();
            db.saveGame(game);
            GameState gameState = game.getGameState(0);
            //(new ConsoleUI()).showGameState(gameState);
            player1.send(gameState);
            player2.send(gameState);

            while (true) {
                // Narazie zostawiam tu 2 podobne bloki kodu. Przerobienie tego na funkcje jest mozliwe, ale teraz to by pogorszyło czytelność
                boolean validmove;
                do {
                    Move move = (Move) player1.receive();
                    GameStateBuilder gameStateBuilder = new GameStateBuilder(gameState);
                    validmove = gameStateBuilder.performAndCheckMove(move, false);
                    if (!validmove) {
                        logger.log(System.Logger.Level.INFO, "Invalid move");
                        player1.send(gameState);
                        continue;
                    }
                    gameState = gameStateBuilder.createGameState();
                    player1.send(gameState);

                } while (!validmove);
                player2.send(gameState);

                savegametodb(gameState);
                check_if_finished(gameState);

                do {
                    Move move = (Move) player2.receive();
                    GameStateBuilder gameStateBuilder = new GameStateBuilder(gameState);
                    validmove = gameStateBuilder.performAndCheckMove(move, true);
                    if (!validmove) {
                        logger.log(System.Logger.Level.INFO, "Invalid move");
                        player2.send(gameState);
                        continue;
                    }
                    gameState = gameStateBuilder.createGameState();
                    player2.send(gameState);

                } while (!validmove);
                player1.send(gameState);

                savegametodb(gameState);
                check_if_finished(gameState);


            }
        } catch (SocketException e) {
            try {
                player1.send(new Request("error", "Opponent disconnected"));
            } catch (SocketException ignore) {}
            try {
                player2.send(new Request("error", "Opponent disconnected"));
            } catch (SocketException ignore) {}
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } catch (gameFinished ignore) {
        }
    }

    private void savegametodb(GameState gameState) throws Exception {
        game.addGameState(gameState);
        db.saveGame(game);
        logger.log(System.Logger.Level.INFO, "Saved game " + game.getId() + " to database");
    }

    private void check_if_finished(GameState gameState) throws Exception, gameFinished {
        logger.log(System.Logger.Level.INFO, "Checking if game " + game.getId() + " finished. Result: " + gameState.finished);
        if (gameState.finished) {
            logger.log(System.Logger.Level.INFO, "Game " + game.getId() + " finished");
            Colour winner = gameState.getWinner();
            try {
                player1.send(winner);
            } catch (SocketException ignore) {}
            try {
                player2.send(winner);
            } catch (SocketException ignore) {}
            throw new gameFinished();
        }
    }

    private void process_move(SocketFacade curP, String curPn, SocketFacade p2, String p2n, GameState gameState, boolean whiteturn) throws gameFinished, SocketException, Exception {

    }
}


class gameFinished extends Throwable {

}
