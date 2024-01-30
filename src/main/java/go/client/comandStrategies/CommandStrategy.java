package go.client.comandStrategies;

import go.client.UI.UI;
import go.communications.SocketFacade;
import go.server.ClientHandling.ClientHandler;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;


// syntax sugar for a function that takes a SocketFacade and a String[] and returns void
public interface CommandStrategy extends TriFunction<SocketFacade, String[], UI, Void> {
    @Override
    Void apply(SocketFacade socketFacade, String[] args, UI ui);

    static HashMap<String, CommandStrategy> commands = new HashMap<>();
};

