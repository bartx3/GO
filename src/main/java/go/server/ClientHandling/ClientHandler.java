package go.server.ClientHandling;

import go.communications.Request;
import go.communications.SocketFacade;
import go.server.ClientHandling.comandStrategies.CommandStrategy;
import go.server.ClientHandling.comandStrategies.Exit;
import go.server.ClientHandling.comandStrategies.History;
import go.server.ClientHandling.comandStrategies.PlayGame;

import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class ClientHandler extends Thread {
    SocketFacade socket;
    String name;

    private static final HashMap<String, CommandStrategy> commands = new HashMap<>();
    void setupCommands()
    {
        synchronized (commands) {
            if (!commands.isEmpty()) {
                return;
            }
            commands.put("play", new PlayGame());
            commands.put("exit", new Exit());
            commands.put("history", new History());
        }
    }

    public ClientHandler(Socket socket) throws SocketException {
        this.socket = new SocketFacade(socket);
        setupCommands();
    }

    @Override
    public void run() {
        try {
            LoginHandler loginHandler = new LoginHandler(socket);
            Thread loginthread = new Thread(loginHandler);
            loginthread.start();
            loginthread.join();
            name = loginHandler.getName();

            while (true) {
                Request request = (Request) socket.receive();
                CommandStrategy command = commands.get(request.command);
                if (command == null) {
                    socket.send("Invalid command");
                    continue;
                }
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
