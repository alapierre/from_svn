/*
 * Copyright 2009 the original author or authors.
 */
package pl.com.softproject.utils.jdbc;

import pl.com.softproject.utils.jdbc.util.JdbcUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import pl.com.softproject.utils.jdbc.exceptions.CreateConnectionException;
import pl.com.softproject.utils.jdbc.exceptions.DataAccessException;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class ConnectionServiceImpl {

    Logger logger = Logger.getLogger(getClass());

    private Connection connection;
    private static ConnectionServiceImpl instance;

    private ConnectionServiceImpl() {
    }

    public static ConnectionServiceImpl getInstance() {

        if (instance == null) {
            instance = new ConnectionServiceImpl();
        }

        return instance;
    }

    /**
     * Connect to the database
     *
     * @param ulr
     * @param user
     * @param pass
     * @throws CreateConnectionException
     */
    public void connect(String ulr, String user, String pass) throws CreateConnectionException {

        disconnect();

        try {
            connection = DriverManager.getConnection(ulr, user, pass);
        } catch (SQLException ex) {
            throw new CreateConnectionException(ex.getMessage(), ex);
        }
    }

    /**
     * Disconect from the database
     */
    public void disconnect() {
        if (connection != null) {
            JdbcUtils.closeConnection(connection);
        }
    }

    /**
     * Return connection to database. If connection is null or is colse
     * method throws DataAccessException
     *
     * @return ready to use jdbc connection
     */
    public Connection getConnection() throws DataAccessException {

        logger.debug(connection);

        try {
            if (connection != null) {
                if (connection.isClosed()) {
                    throw new DataAccessException("connection is close");
                }
            } else {
                throw new DataAccessException("Connection is null");
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }

        return connection;
    }
}
