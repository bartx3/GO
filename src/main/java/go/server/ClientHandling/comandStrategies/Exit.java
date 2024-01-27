package go.server.ClientHandling.comandStrategies;

import go.server.ClientHandling.ClientHandler;

public class Exit implements CommandStrategy{
    @Override
    public Void apply(ClientHandler clientHandler, String[] args) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
