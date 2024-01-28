package go.server.ClientHandling;

import go.communications.Request;
import go.communications.SocketFacade;
import go.server.ClientHandling.comandStrategies.CommandStrategy;
import go.server.ClientHandling.comandStrategies.CommandStrategyFactoryS;
import go.server.ClientHandling.comandStrategies.History;
import go.server.ClientHandling.comandStrategies.PlayGame;
import go.server.Server;

import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import static go.client.Client.logger;

public class ClientHandler extends Thread {
    SocketFacade socket;
    String name;

    public ClientHandler(Socket socket) throws SocketException {
        this.socket = new SocketFacade(socket);
    }

    @Override
    public void run() {
        try {
            Server.logger.log(System.Logger.Level.INFO, "New Client handler started");
            CommandStrategyFactoryS csf = new CommandStrategyFactoryS();
            LoginHandler loginHandler = new LoginHandler(socket);
            Server.logger.log(System.Logger.Level.INFO, "Starting new loginhandler");
            Thread loginthread = new Thread(loginHandler);
            loginthread.start();
            loginthread.join();
            name = loginHandler.getName();

            while (true) {
                Request request = (Request) socket.receive();
                CommandStrategy command = csf.getCommandStrategy(request.command);
                if (command == null) {
                    socket.send("Invalid command");
                    continue;
                }
                logger.log(System.Logger.Level.INFO, "Client " + name + " sent command " + request.command + " and we found " + command.getClass().getName());
                command.apply(this, request.args);
            }
        } catch (InterruptedException | ClassCastException | SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public SocketFacade get_socketf() {
        return socket;
    }
}
