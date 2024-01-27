package go.server.ClientHandling.comandStrategies;

import java.util.HashMap;

public class CommandStrategyFactoryS {
    HashMap<String, CommandStrategy> commands = new HashMap<>();
    public CommandStrategyFactoryS() {
        commands.put("play", new PlayGame());
        commands.put("history", new History());
    }

    public CommandStrategy getCommandStrategy(String command) {
        return commands.get(command);
    }
}
