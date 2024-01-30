package go.server.ClientHandling.comandStrategies;

import go.communications.Request;
import go.communications.SocketFacade;
import go.server.ClientHandling.ClientHandler;
import go.server.DB.DBFacade;
import go.server.Server;

import java.net.SocketException;
import java.util.ArrayList;

import static go.server.Server.logger;

public class History implements CommandStrategy {
    @Override
    public Void apply(ClientHandler clientHandler, String[] args) {
        SocketFacade socket = clientHandler.get_socketf();
        DBFacade db = Server.getDB();
        do {
            ArrayList<Long> games = db.getGameIds();
            try {
                socket.send(games);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
            try {
                Request request = (Request) socket.receive();
                if (request == null) {
                    logger.log(System.Logger.Level.INFO, "Null request");
                    continue;
                }
                if (request.command.equals("main menu")) {
                    logger.log(System.Logger.Level.INFO, "Client exited");
                    break;
                }
                if (request.command.equals("show")) {
                    if (request.args.length != 1) {
                        Request error = new Request("error", "Invalid number of arguments");
                        socket.send(error);
                        continue;
                    }
                    try {
                        long id = Long.parseLong(request.args[0]);
                        if (!games.contains(id)) {
                            Request error = new Request("error", "Invalid game id");
                            socket.send(error);
                            continue;
                        }
                        socket.send(db.loadGame(id));
                    } catch (NumberFormatException e) {
                        Request error = new Request("error", "Invalid game id");
                        socket.send(error);
                        continue;
                    }
                }
            } catch (SocketException e) {
                throw new RuntimeException(e);
            } catch (ClassCastException e) {
                logger.log(System.Logger.Level.INFO, "Invalid response");
            }
        } while (true);
        return null;
    }
}
