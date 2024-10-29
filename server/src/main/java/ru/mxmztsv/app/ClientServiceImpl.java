package ru.mxmztsv.app;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import ru.mxmztsv.app.domain.DatabaseExecutor;
import ru.mxmztsv.app.domain.Client;
import ru.mxmztsv.model.Category;
import ru.mxmztsv.model.Status;
import ru.mxmztsv.model.soap.ClientRequest;
import ru.mxmztsv.model.soap.ClientResponse;
import ru.mxmztsv.model.soap.ClientSearchResponse;
import ru.mxmztsv.model.soap.SearchClientsResponseModel;

import javax.sql.DataSource;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@WebService(serviceName = "ClientService", portName = "ClientServicePort")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class ClientServiceImpl {

    private static final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final DatabaseExecutor databaseExecutor;

    public ClientServiceImpl(DataSource dataSource) {
        this.databaseExecutor = new DatabaseExecutor(dataSource);
    }

    @WebMethod
    @WebResult(name = "ClientSearchResponse", targetNamespace = "http://app.mxmztsv.ru/")
    public ClientSearchResponse searchClients(
            @WebParam(name = "firstName")
            String firstName,
            @WebParam(name = "lastName")
            String lastName,
            @WebParam(name = "status")
            Status status,
            @WebParam(name = "category")
            Category category,
            @WebParam(name = "data")
            String data
    ) throws ParseException {
        StringBuilder query = new StringBuilder("SELECT * FROM Clients WHERE 1=1");
        List<Object> params = new ArrayList<>();
        addStringParam(firstName, "first_name", query, params);
        addStringParam(lastName, "last_name", query, params);
        if (data != null && !data.isEmpty()) {
            addStringParamDate(DATA_FORMAT.format(DATA_FORMAT.parse(data)), "created_at", query, params);
        }
        addEnumParam(status, "status", query, params);
        addEnumParam(category, "category", query, params);
        return ClientSearchResponse.builder()
                .responseModelList(databaseExecutor.executeSelect(query.toString(), params).stream()
                        .map(ClientServiceImpl::mapToSearchResponse).collect(Collectors.toList()))
                .build();
    }

    @WebMethod
    @WebResult(name = "ClientResponse", targetNamespace = "http://app.mxmztsv.ru/")
    public ClientResponse create(@WebParam(name = "request") ClientRequest request) {
        StringBuilder query = new StringBuilder(
                "INSERT INTO Clients (first_name, last_name, status, category, created_at) values ("
        );
        query.append("'").append(request.getFirstName()).append("'");
        query.append(", '").append(request.getLastName()).append("'");
        query.append(", '").append(request.getStatus()).append("'");
        query.append(", '").append(request.getCategory()).append("'");
        query.append(", '").append(request.getCreatedAt()).append("'");
        query.append(")");
        Client Client = databaseExecutor.executeQuery(query.toString());
        log.info("Client created: {}", Client.getId());
        return Client != null ? mapToClientResponse(Client) : null;
    }

    @WebMethod
    @WebResult(name = "ClientResponse", targetNamespace = "http://app.mxmztsv.ru/")
    public ClientResponse update(
            @WebParam(name = "id") Long id,
            @WebParam(name = "request") ClientRequest request
    ) {
        StringBuilder query = new StringBuilder("UPDATE Clients SET ").append(request.toQueryStringUpdate())
                .append(" WHERE ID = ").append(id);
        Client Client = databaseExecutor.executeQuery(query.toString());
        return Client != null ? mapToClientResponse(Client) : null;
    }

    @WebMethod
    public void delete(
            @WebParam(name = "id") Long id
    ) {
        databaseExecutor.executeQueryEmpty("DELETE FROM Clients WHERE ID = " + id);
    }

    private void addStringParam(String value, String name, StringBuilder query, List<Object> params) {
        if (value != null && !value.isEmpty()) {
            query.append(" AND ").append(name).append(" = ?");
            params.add(value);
        }
    }

    private void addStringParamDate(String value, String name, StringBuilder query, List<Object> params) {
        if (value != null && !value.isEmpty()) {
            query.append(" AND ").append(name).append(" = CAST(? AS DATE)");
            params.add(value);
        }
    }

    private void addEnumParam(Object value, String name, StringBuilder query, List<Object> params) {
        if (value != null) {
            query.append(" AND ").append(name).append(" = ?");
            params.add(value);
        }
    }

    private static SearchClientsResponseModel mapToSearchResponse(Client Client) {
        return SearchClientsResponseModel.builder()
                .id(Client.getId())
                .firstName(Client.getFirstName())
                .lastName(Client.getLastName())
                .status(Status.valueOf(Client.getStatus()))
                .category(Category.valueOf(Client.getCategory()))
                .createdAt(DATA_FORMAT.format(Client.getCreatedAt()))
                .build();
    }

    private static ClientResponse mapToClientResponse(Client Client) {
        return ClientResponse.builder()
                .id(Client.getId())
                .firstName(Client.getFirstName())
                .lastName(Client.getLastName())
                .status(Status.valueOf(Client.getStatus()))
                .category(Category.valueOf(Client.getCategory()))
                .createdAt(DATA_FORMAT.format(Client.getCreatedAt()))
                .build();
    }
}


