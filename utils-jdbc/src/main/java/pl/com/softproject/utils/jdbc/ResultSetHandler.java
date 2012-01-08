/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Adrian Lapierre
 */
public interface  ResultSetHandler<T> {

    T handle(ResultSet rs) throws SQLException;

}
