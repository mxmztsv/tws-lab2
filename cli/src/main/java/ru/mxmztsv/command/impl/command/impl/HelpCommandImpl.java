package ru.mxmztsv.command.impl.command.impl;

import ru.mxmztsv.command.impl.command.Command;
import ru.mxmztsv.command.impl.command.CommandHandler;
import ru.mxmztsv.command.impl.command.Key;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class HelpCommandImpl implements CommandHandler {

    @Override
    public void execute(Map<Key, String> params) {
        Arrays.stream(Command.values()).filter(command -> !command.equals(Command.HELP))
                .forEach(command -> {
                    System.out.println("\n-> " + command.getCommandName());
                    System.out.println("   " + command.getDescription());
                    String descriptionKeyCommands = Arrays.stream(Key.values())
                            .filter(key -> key.getCommands().contains(command))
                            .map(Key::getDescription).collect(Collectors.joining("\n\t"));
                    if (!descriptionKeyCommands.isEmpty()) {
                        System.out.println("Keys:");
                        System.out.println("\t" + descriptionKeyCommands);
                    }
                });
    }

    @Override
    public Command getName() {
        return Command.HELP;
    }
}
