package ru.mxmztsv.app.domain;

import lombok.extern.slf4j.Slf4j;
import ru.mxmztsv.model.Category;
import ru.mxmztsv.model.Status;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DatabaseExecutor {

    private final DataSource dataSource;

    public DatabaseExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Client> executeSelect(String sql, List<Object> params) {
        List<Client> Clients = new ArrayList<>();
        try (
                var connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            log.info("query: {}", sql);
            log.info("params: {}", params);
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Status) {
                    statement.setString(i + 1, ((Status) param).name());
                } else if (param instanceof Category) {
                    statement.setString(i + 1, ((Category) param).name());
                } else {
                    statement.setObject(i + 1, param);
                }
            }

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Clients.add(mapToClient(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Clients;
    }

    public Client executeQuery(String sql) {
        try (
                var connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            log.info("query: {}", sql);
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        log.info("generated id: {}", generatedId);
                        return getClientById(generatedId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeQueryEmpty(String sql) {
        try (
                var connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            log.info("query: {}", sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getClientById(int id) {
        try (
                var connection = dataSource.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement("SELECT * FROM Clients WHERE ID = " + id)
        ) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToClient(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Client mapToClient(ResultSet resultSet) throws SQLException {
        return Client.builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .status(resultSet.getString("status"))
                .category(resultSet.getString("category"))
                .createdAt(resultSet.getDate("created_at"))
                .build();
    }
}
