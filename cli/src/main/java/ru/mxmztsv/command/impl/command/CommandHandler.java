package ru.mxmztsv.command.impl.command;

import java.util.Map;

public interface CommandHandler {
    void execute(Map<Key, String> params);

    Command getName();
}
