

package pl.com.softproject.utils.jdbc.exceptions;

/**
 * Exception thrown on mismatch between Java type and database type:
 * for example on an attempt to set an object of the wrong type
 * in an RDBMS column.
 *
 * @author Rod Johnson
 */
public class TypeMismatchDataAccessException extends InvalidDataAccessResourceUsageException {

	/**
	 * Constructor for TypeMismatchDataAccessException.
	 * @param msg the detail message
	 */
	public TypeMismatchDataAccessException(String msg) {
		super(msg);
	}

	/**
	 * Constructor for TypeMismatchDataAccessException.
	 * @param msg the detail message
	 * @param cause the root cause from the data access API in use
	 */
	public TypeMismatchDataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
