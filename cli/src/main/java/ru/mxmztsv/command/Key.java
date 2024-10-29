package ru.mxmztsv.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Key {
    ID(List.of(Command.DELETE, Command.UPDATE), "-id", "--id","-id, --id        \t\t->\tКлючь указывает id клиента"),
    FIRST_NAME(List.of(Command.SEARCH, Command.CREATE, Command.UPDATE), "-fn", "--first-name", "-fn, --first-name\t\t->\tКлюч для передачи имени"),
    LAST_NAME(List.of(Command.SEARCH, Command.CREATE, Command.UPDATE), "-ln", "--last-name", "-ln, --last-name\t\t->\tКлюч для передачи фамилии"),
    STATUS(List.of(Command.SEARCH, Command.CREATE, Command.UPDATE), "-s", "--status", "-s, --status\t\t->\tКлюч для передачи статуса"),
    CATEGORY(List.of(Command.SEARCH, Command.CREATE, Command.UPDATE), "-cat", "--category", "-cat, --category\t\t->\tКлюч для передачи категории"),
    DATE(List.of(Command.SEARCH, Command.CREATE, Command.UPDATE), "-d", "--date", "-d, --date        \t\t->\tКлюч для передачи даты"),;

    private final List<Command> commands;
    private final String key;
    private final String fullKey;
    private final String description;

    public static Key findKey(String key) {
        return Arrays.stream(Key.values()).filter(k -> k.key.equals(key) || k.getFullKey().equals(key))
                .findFirst().orElse(null);
    }
}
