package ru.mxmztsv.command.impl.command.impl;

import lombok.SneakyThrows;
import ru.mxmztsv.app.ClientServiceImpl;
import ru.mxmztsv.command.impl.command.Command;
import ru.mxmztsv.command.impl.command.CommandHandler;
import ru.mxmztsv.command.impl.command.Key;
import ru.mxmztsv.command.impl.command.mapper.ClientMapper;
import ru.mxmztsv.command.impl.model.Client;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchCommandImpl implements CommandHandler {
    private final ClientServiceImpl clientService;

    public SearchCommandImpl(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @SneakyThrows
    @Override
    public void execute(Map<Key, String> params) {
        Client client = ClientMapper.mapKeysToClient(params);
        String clients = Optional.ofNullable(
                        clientService.searchClients(client.getFirstName(), client.getLastName(),
                                client.getStatus(), client.getCategory(), client.getCreatedAt()
                        ).getResponseModelList()
                ).stream()
                .flatMap(Collection::stream)
                .map(ClientMapper::mapToString)
                .collect(Collectors.joining("\n"));
        if (clients.isEmpty()) {
            System.out.println("Нет клиентов, соответствующих заданным параметрам.");
        } else {
            System.out.println(clients);
        }

    }

    @Override
    public Command getName() {
        return Command.SEARCH;
    }
}
