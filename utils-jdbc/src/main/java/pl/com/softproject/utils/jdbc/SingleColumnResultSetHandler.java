/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.jdbc;

import pl.com.softproject.utils.jdbc.util.JdbcUtils;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import pl.com.softproject.utils.jdbc.exceptions.IncorrectResultSetColumnCountException;

/**
 *
 * @author Adrian Lapierre
 */
public class SingleColumnResultSetHandler<T> implements ResultSetHandler<T> {

    private Class<T> clazz;

    public SingleColumnResultSetHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T handle(ResultSet rs) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();

        int nrOfColumns = rsmd.getColumnCount();
		if (nrOfColumns != 1) {
			throw new IncorrectResultSetColumnCountException(1, nrOfColumns);
		}

        T result = (T) JdbcUtils.getResultSetValue(rs, 1, clazz);

        return result;
    }



}
