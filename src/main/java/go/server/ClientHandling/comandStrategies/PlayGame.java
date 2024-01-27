package go.server.ClientHandling.comandStrategies;

import go.communications.Request;
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
            int size = 0;
            try {
                size = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                Request request = new Request("error", "Invalid size");
                socket.send(request);
            }
            Pairer pairer = Server.getPairer(size);
            if (pairer == null) {
                Request request = new Request("error", "Unavailable size");
                socket.send(request);
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
