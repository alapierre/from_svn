/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.softproject.utils.jdbc;

import pl.com.softproject.utils.jdbc.util.DataAccessUtils;
import pl.com.softproject.utils.jdbc.util.JdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import pl.com.softproject.utils.jdbc.exceptions.DataAccessException;
import pl.com.softproject.utils.jdbc.exceptions.DataRetrievalFailureException;

/**
 *
 * @author Adrian Lapierre
 */
public class JdbcTemplate {

    protected Logger logger = Logger.getLogger(getClass());

    public <T> T queryForObject(Connection connection, String sql, Class<T> requiredType) throws DataAccessException {
        List results = queryForOneColumnList(connection, sql, requiredType);
        return (T) DataAccessUtils.requiredSingleResult(results);
    }

    public String queryForString(Connection connection, String sql) {
        return queryForObject(connection, sql, String.class);
    }

    public long queryForLong(Connection connection, String sql) throws DataAccessException {
        return queryForObject(connection, sql, Long.class);
    }

    public int queryForInt(Connection connection, String sql) throws DataAccessException {
        return queryForObject(connection, sql, Integer.class);
    }

    public <T> List<T> queryForOneColumnList(Connection connection, String sql, Class<T> clazz) {
        return query(connection, sql, new SingleColumnResultSetHandler<T>(clazz));
    }

    public void executeQuery(Connection connection, String sql) {

        Statement stmt = null;

        try {
            try {
                stmt = connection.createStatement();
            } catch (SQLException ex) {
                throw new DataAccessException("Error creating statement", ex);
            }

            try {
                stmt.executeUpdate(sql);
            } catch (SQLException ex) {
                throw new DataAccessException("Error executing query", ex);
            }
        } finally {
            JdbcUtils.closeStatement(stmt);
        }
    }

    public <T> List<T> query(Connection connection, String sql, ResultSetHandler<T> handler) {

        Statement stmt = null;
        List<T> result = new LinkedList<T>();
        ResultSet rs = null;

        try {
            try {
                stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            } catch (SQLException ex) {
                throw new DataAccessException("Error creating statement", ex);
            }

            try {
                rs = stmt.executeQuery(sql);
            } catch (SQLException ex) {
                throw new DataAccessException("Error executing query", ex);
            }

            try {
                while (rs.next()) {
                    result.add(handler.handle(rs));
                }
            } catch (SQLException ex) {
                throw new DataRetrievalFailureException("Error handling result", ex);
            }
        } finally {
            JdbcUtils.closeResultSet(rs);
            JdbcUtils.closeStatement(stmt);
        }

        return result;
    }
}
