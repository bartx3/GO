package go;

import go.game.Game;
import go.game.GameState;
import go.game.GameStateBuilder;
import go.game.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameHandler extends Thread{
    public static System.Logger logger = System.getLogger("GameHandlerLogger");
    private Socket socket1;
    private Socket socket2;
    private String name1;
    private String name2;
    Game game;
    public GameHandler(String name1, String name2) throws PlayersPlayingException, InterruptedException, IOException {
        super();
        //pick random player as player1
        if(Math.random() < 0.5) {
            this.name1 = name1;
            this.name2 = name2;
        } else {
            this.name1 = name2;
            this.name2 = name1;
        }
        Server.onlineUsersSemaphore.acquire();
        if(Server.usersOnline.get(name1).isPlaying || Server.usersOnline.get(name2).isPlaying) {
            Server.onlineUsersSemaphore.release();
            throw new PlayersPlayingException();
        }
        Server.usersOnline.get(name1).setIsPlaying(true);
        Server.usersOnline.get(name2).setIsPlaying(true);
        socket1 = Server.usersOnline.get(name1).socket;
        socket2 = Server.usersOnline.get(name2).socket;
        Server.onlineUsersSemaphore.release();
        ObjectInputStream p1in = new ObjectInputStream(socket1.getInputStream());
        ObjectInputStream p2in = new ObjectInputStream(socket2.getInputStream());
        p1in = new ObjectInputStream(socket1.getInputStream());
        p2in = new ObjectInputStream(socket2.getInputStream());
        ObjectOutputStream p1out = new ObjectOutputStream(socket1.getOutputStream());
        ObjectOutputStream p2out = new ObjectOutputStream(socket2.getOutputStream());

        game = new Game(Server.db.generateGameId(), name1, name2);
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream p1out = new ObjectOutputStream(socket1.getOutputStream());
            ObjectOutputStream p2out = new ObjectOutputStream(socket2.getOutputStream());
            ObjectInputStream p1in = new ObjectInputStream(socket1.getInputStream());
            ObjectInputStream p2in = new ObjectInputStream(socket2.getInputStream());
            p1out.writeObject("white");
            p2out.writeObject("black");
            //get board size from player 1
            int boardSize = (int) p1in.readObject();
            GameState gameState = new GameState(boardSize);
            boolean gameEnded = false;
            boolean player1Turn = true;
            boolean player1passed = false;
            boolean player2passed = false;
            Move move;
            while (!gameEnded) {
                    move = player1Turn ? (Move) p1in.readObject() : (Move) p2in.readObject();

                    if (move == null || move.giveUp) {
                        gameEnded = true;
                        game.winner = player1Turn ?  Game.FinalState.PLAYER2_WON : Game.FinalState.PLAYER1_WON;
                        break;
                    }
                    GameStateBuilder gameStateBuilder = new GameStateBuilder(gameState);
                    boolean goodmove = gameStateBuilder.performAndCheckMove(move, player1Turn);
                    if (!goodmove) {
                        if (player1Turn) {
                            p1out.writeObject("badmove");
                        } else {
                            p2out.writeObject("badmove");
                        }
                        continue;
                    }

                    gameState = gameStateBuilder.createGameState();
                    game.addGameState(gameState);
                    if (player1Turn) {
                        p1out.writeObject("goodmove");
                        p2out.writeObject(gameState);
                    } else {
                        p2out.writeObject("goodmove");
                        p1out.writeObject(gameState);
                    }
                    if (move.isPass) {
                        if (player1Turn) {
                            player1passed = true;
                        } else {
                            player2passed = true;
                        }
                    } else {
                        player1passed = false;
                        player2passed = false;
                    }
                    player1Turn = !player1Turn;
            }
            if (game.winner == Game.FinalState.NOT_FINISHED) {
                int player1Score = gameState.countScoreP1();
                int player2Score = gameState.countScoreP2();
                if (player1Score > player2Score) {
                    game.winner = Game.FinalState.PLAYER1_WON;
                } else if (player1Score < player2Score) {
                    game.winner = Game.FinalState.PLAYER2_WON;
                } else {
                    game.winner = Game.FinalState.DRAW;
                }
            }
            Server.db.saveGame(game);
            String winner = game.winner == Game.FinalState.PLAYER1_WON ? name1 : name2;
            p2out.writeObject("gameover");
            p1out.writeObject("gameover");

        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, e.getMessage());
        }
        try {
            Server.onlineUsersSemaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Server.usersOnline.get(name1).setIsPlaying(false);
        Server.usersOnline.get(name2).setIsPlaying(false);
        Server.onlineUsersSemaphore.release();
    }
}
