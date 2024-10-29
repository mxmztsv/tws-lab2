package ru.mxmztsv.command.impl;

import lombok.SneakyThrows;
import ru.mxmztsv.app.ClientServiceImpl;
import ru.mxmztsv.command.Command;
import ru.mxmztsv.command.CommandHandler;
import ru.mxmztsv.command.Key;
import ru.mxmztsv.command.mapper.ClientMapper;
import ru.mxmztsv.model.Client;

import java.util.Map;

public class DeleteCommandImpl implements CommandHandler {

    private final ClientServiceImpl clientService;

    public DeleteCommandImpl(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @SneakyThrows
    @Override
    public void execute(Map<Key, String> params) {
        Client client = ClientMapper.mapKeysToClient(params);
        if(client.getId() != null){
            clientService.delete(client.getId());
        } else {
            throw new RuntimeException("Id is null");
        }
    }

    @Override
    public Command getName() {
        return Command.DELETE;
    }
}
