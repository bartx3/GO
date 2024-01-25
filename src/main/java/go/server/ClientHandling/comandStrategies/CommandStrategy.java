package go.server.ClientHandling.comandStrategies;

import go.server.ClientHandling.ClientHandler;

import java.util.function.BiFunction;
import java.util.function.Function;


// syntax sugar for a function that takes a ClientHandler and a String[] and returns void
public interface CommandStrategy extends BiFunction<ClientHandler, String[], Void> {
    @Override
    Void apply(ClientHandler clientHandler, String[] args);
};
