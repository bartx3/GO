package go.client.comandStrategies;

import java.util.HashMap;

public class CommandStrategyFactory {
    HashMap<String, CommandStrategy> commands = new HashMap<>();
    public CommandStrategyFactory() {
        commands.put("play", new PlayGame());
    }

    public CommandStrategy getCommandStrategy(String command) {
        return commands.get(command);
    }
}
