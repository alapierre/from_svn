/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.jdbc.exceptions;

/**
 *
 * @author Adrian Lapierre
 */
public class IncorrectResultSetColumnCountException extends DataRetrievalFailureException {

    public IncorrectResultSetColumnCountException(int exected, int current) {
        super("exected " + exected + " but was " + current);
    }

}
