package ru.mxmztsv.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Command {
    HELP("help", ""),
    SEARCH("search", "Команда для вызова поиска и обращения к внешнему серверу"),
    CREATE("create", "Команда для создания клиента"),
    UPDATE("update", "Команда для обновление данных о клиенте"),
    DELETE("delete", "Команда для удаления клиента"),
    EXIT("exit", "Выход из консоли");

    private final String commandName;
    private final String description;
}
