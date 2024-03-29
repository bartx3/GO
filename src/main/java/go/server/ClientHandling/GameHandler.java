package go.server.ClientHandling;

import go.communications.Request;
import go.communications.SocketFacade;
import go.game.*;
import go.server.DB.DBFacade;

import java.net.SocketException;

import static go.server.Server.logger;

public class GameHandler extends Thread {
    static final DBFacade db = go.server.Server.getDB();
    SocketFacade player1;
    SocketFacade player2;
    String pl1name;
    String pl2name;
    Game game;

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
                logger.log(System.Logger.Level.INFO, "Waiting for move from " + pl1name);
                // Narazie zostawiam tu 2 podobne bloki kodu. Przerobienie tego na funkcje jest mozliwe, ale teraz to by pogorszyło czytelność
                boolean validmove;
                do {
                    Move move = (Move) player1.receive();
                    GameStateBuilder gameStateBuilder = new GameStateBuilder(gameState);
                    validmove = gameStateBuilder.performAndCheckMove(move, false) &&  (!is_in_game(gameStateBuilder.createGameState(), game) || move.giveUp || move.isPass);
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
                logger.log(System.Logger.Level.INFO, "Checking if game finished. Result: " + gameState.finished );
                if (gameState.finished) {
                    break;
                }

                logger.log(System.Logger.Level.INFO, "Waiting for move from " + pl2name);
                do {
                    Move move = (Move) player2.receive();
                    GameStateBuilder gameStateBuilder = new GameStateBuilder(gameState);
                    validmove = gameStateBuilder.performAndCheckMove(move, true) && (!is_in_game(gameStateBuilder.createGameState(), game) || move.giveUp || move.isPass);
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
                logger.log(System.Logger.Level.INFO, "Checking if game finished. Result: " + gameState.finished );
                if (gameState.finished) {
                    break;
                }


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
        }
        logger.log(System.Logger.Level.INFO, "Game finished");
    }

    private void savegametodb(GameState gameState) throws Exception {
        game.addGameState(gameState);
        db.saveGame(game);
        logger.log(System.Logger.Level.INFO, "Saved game " + game.getId() + " to database");
    }

    boolean is_in_game(GameState gs, Game game)
    {
        for (GameState g : game.gameStates)
        {
            if (identical_gamestates(gs, g))
                return true;
        }
        return false;
    }

    boolean identical_gamestates(GameState gs1, GameState gs2)
    {
        if (gs1.getSize() != gs2.getSize())
            return false;
        for (int i = 0; i < gs1.getSize(); i++)
        {
            for (int j = 0; j < gs1.getSize(); j++)
            {
                if (!gs1.getBoard()[i][j].equals( gs2.getBoard()[i][j]))
                    return false;
            }
        }
        return true;
    }
}

