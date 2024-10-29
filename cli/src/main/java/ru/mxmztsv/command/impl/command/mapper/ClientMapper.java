package ru.mxmztsv.command.impl.command.mapper;

import ru.mxmztsv.app.Category;
import ru.mxmztsv.app.Status;
import ru.mxmztsv.app.model.ClientRequest;
import ru.mxmztsv.app.model.ClientResponse;
import ru.mxmztsv.app.model.SearchClientsResponseModelType;
import ru.mxmztsv.command.impl.command.Key;
import ru.mxmztsv.command.impl.model.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class ClientMapper {

    public static final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat DATA_FORMAT_SERVER = new SimpleDateFormat("yyyy-MM-dd");

    public static String mapToString(SearchClientsResponseModelType client) {
        try {
            return "Client -> " + "\n" +
                    "\t Id: " + client.getId() + "\n" +
                    "\t Name: " + client.getFirstName() + "\n" +
                    "\t Last name: " + client.getLastName() + "\n" +
                    "\t Status: " + client.getStatus() + "\n" +
                    "\t Category: " + client.getCategory() + "\n" +
                    "\t Created at: " + DATA_FORMAT.format(DATA_FORMAT_SERVER.parse(client.getCreatedAt())) + "\n";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String mapToString(ClientResponse response) {
        try {
            return "Client -> " + "\n" +
                    "\t Id: " + response.getId() + "\n" +
                    "\t Name: " + response.getFirstName() + "\n" +
                    "\t Last name: " + response.getLastName() + "\n" +
                    "\t Status: " + response.getStatus() + "\n" +
                    "\t Category: " + response.getCategory() + "\n" +
                    "\t Created at: " + DATA_FORMAT.format(DATA_FORMAT_SERVER.parse(response.getCreatedAt())) + "\n";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Client mapKeysToClient(Map<Key, String> params) throws ParseException {
        return Client.builder()
                .id(params.get(Key.ID) != null ? Long.parseLong(params.get(Key.ID)) : null)
                .firstName(params.get(Key.FIRST_NAME))
                .lastName(params.get(Key.LAST_NAME))
                .status(params.get(Key.STATUS) != null
                        ? Status.valueOf(params.get(Key.STATUS).toUpperCase())
                        : null
                )
                .category(params.get(Key.CATEGORY) != null
                        ? Category.valueOf(params.get(Key.CATEGORY).toUpperCase())
                        : null
                )
                .createdAt(params.get(Key.DATE) != null
                        ? ClientMapper.DATA_FORMAT_SERVER
                        .format(ClientMapper.DATA_FORMAT.parse(params.get(Key.DATE)))
                        : null
                )
                .build();
    }

    public static ClientRequest mapToRequestCreate(Client client) {
        ClientRequest request = new ClientRequest();
        request.setFirstName(client.getFirstName());
        request.setLastName(client.getLastName());
        request.setStatus(client.getStatus());
        request.setCategory(client.getCategory());
        request.setCreatedAt(client.getCreatedAt());
        return request;
    }
}
