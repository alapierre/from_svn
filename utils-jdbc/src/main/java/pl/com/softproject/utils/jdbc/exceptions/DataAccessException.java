/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.jdbc.exceptions;

/**
 *
 * @author Adrian Lapierre
 */
public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = -714344219116581982L;

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException() {
    }



}
