package ru.mxmztsv.command.impl;

import ru.mxmztsv.command.Command;
import ru.mxmztsv.command.CommandHandler;
import ru.mxmztsv.command.Key;

import java.util.Map;

public class ExitCommandImpl implements CommandHandler {
    @Override
    public void execute(Map<Key, String> params) {
        System.out.println("Exit console, bye");
        System.exit(0);
    }

    @Override
    public Command getName() {
        return Command.EXIT;
    }
}
