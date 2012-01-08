/*
 * Copyright 2010 the original author or authors.
 */
package pl.com.softproject.utils.jdbc.util;

import java.util.Collection;
import pl.com.softproject.utils.jdbc.exceptions.EmptyResultDataAccessException;
import pl.com.softproject.utils.jdbc.exceptions.IncorrectResultSizeDataAccessException;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class DataAccessUtils {

    /**
     * Return a single result object from the given Collection.
     * <p>Throws an exception if 0 or more than 1 element found.
     * @param results the result Collection (can be <code>null</code>)
     * @return the single result object
     * @throws IncorrectResultSizeDataAccessException if more than one
     * element has been found in the given Collection
     * @throws EmptyResultDataAccessException if no element at all
     * has been found in the given Collection
     */
    public static Object requiredSingleResult(Collection results) throws IncorrectResultSizeDataAccessException {
        int size = (results != null ? results.size() : 0);
        if (size == 0) {
            throw new EmptyResultDataAccessException(1);
        }
        if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, size);
        }
        return results.iterator().next();
    }
}
