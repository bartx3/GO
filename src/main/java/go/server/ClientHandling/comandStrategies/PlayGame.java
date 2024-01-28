package go.server.ClientHandling.comandStrategies;

import go.communications.Request;
import go.communications.SocketFacade;
import go.server.ClientHandling.ClientHandler;
import go.server.ClientHandling.GameHandler;
import go.server.ClientHandling.Pairer;
import go.server.Server;

import java.net.SocketException;
import java.util.HashMap;

import static go.server.Server.logger;

public class PlayGame implements CommandStrategy{
    @Override
    public Void apply(ClientHandler clientHandler, String[] args) throws RuntimeException {
        if (clientHandler == null) {
            throw new RuntimeException("clientHandler is null");
        }
        if (args == null) {
            throw new RuntimeException("args is null");
        }
        SocketFacade socket = clientHandler.get_socketf();
        if (socket == null) {
            throw new RuntimeException("socket is null");
        }
        try {
            if (args.length != 1) {
                Request request = new Request("error", "Invalid number of arguments");
                socket.send(request);
                return null;
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        logger.log(System.Logger.Level.INFO, "Looking for game for " + clientHandler.getName() + " with size " + args[0]);
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
