package ru.mxmztsv.app.publisher;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.xml.ws.Endpoint;
import lombok.extern.slf4j.Slf4j;
import ru.mxmztsv.app.ClientServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@Slf4j
@WebListener
public class ContextInitializer implements ServletContextListener {
    private Endpoint endpoint;

    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("\n\tStart initializing context");
        initConnection();
        publishService();
    }

    private void initConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/postgres");
        } catch (NamingException e) {
            log.error("Failed to initialize DataSource", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (endpoint != null) {
            endpoint.stop();
            log.info("\n\tClientService is stopped!");
        }
    }

    private void publishService() {
        try {
            ClientServiceImpl ClientServiceImpl = new ClientServiceImpl(dataSource);
            endpoint = Endpoint.publish("http://localhost:9090/ClientService", ClientServiceImpl);
            log.info("\n\tClientService is published!");
        } catch (Exception e) {
            log.error("\n\tFailed to publish ClientService", e);
        }
    }
}
