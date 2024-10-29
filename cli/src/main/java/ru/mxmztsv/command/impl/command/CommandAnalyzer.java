package ru.mxmztsv.command.impl.command;

import ru.mxmztsv.app.ClientService;
import ru.mxmztsv.command.impl.command.impl.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandAnalyzer {

    private final Map<Command, CommandHandler> commandHandlers = new HashMap<>();

    public CommandAnalyzer() {
        ClientService service = new ClientService();
        SearchCommandImpl searchCommand = new SearchCommandImpl(service.getClientServicePort());
        DeleteCommandImpl deleteCommand = new DeleteCommandImpl(service.getClientServicePort());
        CreateCommandImpl createCommand = new CreateCommandImpl(service.getClientServicePort());
        UpdateCommandImpl updateCommand = new UpdateCommandImpl(service.getClientServicePort());
        HelpCommandImpl helpCommand = new HelpCommandImpl();
        ExitCommandImpl exitCommand = new ExitCommandImpl();
        commandHandlers.put(searchCommand.getName(), searchCommand);
        commandHandlers.put(deleteCommand.getName(), deleteCommand);
        commandHandlers.put(createCommand.getName(), createCommand);
        commandHandlers.put(updateCommand.getName(), updateCommand);
        commandHandlers.put(helpCommand.getName(), helpCommand);
        commandHandlers.put(exitCommand.getName(), exitCommand);
    }

    public Optional<CommandHandler> handler(String command) {
        Optional<Command> commandExecute = Arrays.stream(Command.values())
                .filter(it -> it.getCommandName().equals(command)).findFirst();
        return commandExecute.flatMap(this::findCommandHandler);
    }

    private Optional<CommandHandler> findCommandHandler(Command command) {
        return Optional.ofNullable(commandHandlers.get(command));
    }
}
