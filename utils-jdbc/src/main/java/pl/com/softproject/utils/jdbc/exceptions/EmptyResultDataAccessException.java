/*
 * Copyright 2010 the original author or authors.
 */

package pl.com.softproject.utils.jdbc.exceptions;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class EmptyResultDataAccessException extends IncorrectResultSizeDataAccessException {

    public EmptyResultDataAccessException(int expectedSize) {
        super(expectedSize);
    }



}
