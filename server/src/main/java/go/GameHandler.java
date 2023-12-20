package go;

import go.game.Game;

import java.net.Socket;

public class GameHandler extends Thread{
    private Socket socket1;
    private Socket socket2;
    private String name1;
    private String name2;
    Game game;
    public GameHandler(String name1, String name2) {
        super();

    }
}
