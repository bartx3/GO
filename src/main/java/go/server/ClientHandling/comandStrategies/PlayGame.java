package go.server.ClientHandling.comandStrategies;

import go.communications.SocketFacade;
import go.server.ClientHandling.ClientHandler;
import go.server.ClientHandling.GameHandler;
import go.server.ClientHandling.Pairer;
import go.server.Server;

import java.net.SocketException;
import java.util.HashMap;

public class PlayGame implements CommandStrategy{
    @Override
    public Void apply(ClientHandler clientHandler, String[] args) {
        SocketFacade socket = clientHandler.get_socketf();
        if (socket == null) {
            throw new RuntimeException("socket is null");
        }
        try {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                socket.send("Invalid size");
            }
            int size = Integer.parseInt(args[0]);
            Pairer pairer = Server.getPairer(size);
            if (pairer == null) {
                socket.send("Invalid size");
                return null;
            }
            GameHandler gameHandler = pairer.addPlayer(clientHandler.getName(), size, socket);
            gameHandler.join();
        } catch (SocketException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
