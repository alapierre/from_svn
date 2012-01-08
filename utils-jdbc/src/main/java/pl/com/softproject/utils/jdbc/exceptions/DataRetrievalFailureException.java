/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.jdbc.exceptions;

/**
 *
 * @author Adrian Lapierre
 */
public class DataRetrievalFailureException extends DataAccessException {

    private static final long serialVersionUID = 1273918588024970404L;

    public DataRetrievalFailureException() {
    }

    public DataRetrievalFailureException(String message) {
        super(message);
    }

    public DataRetrievalFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataRetrievalFailureException(Throwable cause) {
        super(cause);
    }



}
