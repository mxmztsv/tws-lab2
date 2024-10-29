package ru.mxmztsv.command.impl.command.impl;

import lombok.SneakyThrows;
import ru.mxmztsv.app.ClientServiceImpl;
import ru.mxmztsv.app.model.ClientResponse;
import ru.mxmztsv.command.impl.command.Command;
import ru.mxmztsv.command.impl.command.CommandHandler;
import ru.mxmztsv.command.impl.command.Key;
import ru.mxmztsv.command.impl.command.mapper.ClientMapper;
import ru.mxmztsv.command.impl.model.Client;

import java.util.Map;

public class UpdateCommandImpl implements CommandHandler {

    private final ClientServiceImpl clientService;

    public UpdateCommandImpl(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @SneakyThrows
    @Override
    public void execute(Map<Key, String> params) {
        Client client = ClientMapper.mapKeysToClient(params);
        ClientResponse response =
                clientService.update(client.getId(), ClientMapper.mapToRequestCreate(client));
        System.out.println(ClientMapper.mapToString(response));
    }

    @Override
    public Command getName() {
        return Command.UPDATE;
    }
}
