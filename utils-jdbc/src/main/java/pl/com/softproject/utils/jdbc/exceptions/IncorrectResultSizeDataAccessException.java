/*
 * Copyright 2010 the original author or authors.
 */
package pl.com.softproject.utils.jdbc.exceptions;

/**
 * Data access exception thrown when a result was not of the expected size,
 * for example when expecting a single row but getting 0 or more than 1 rows.
 * 
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class IncorrectResultSizeDataAccessException extends DataRetrievalFailureException {

    private int expectedSize;
    private int actualSize;

    /**
     * Constructor for IncorrectResultSizeDataAccessException.
     * @param expectedSize the expected result size
     */
    public IncorrectResultSizeDataAccessException(int expectedSize) {
        super("Incorrect result size: expected " + expectedSize);
        this.expectedSize = expectedSize;
        this.actualSize = -1;
    }

    /**
     * Constructor for IncorrectResultSizeDataAccessException.
     * @param expectedSize the expected result size
     * @param actualSize the actual result size (or -1 if unknown)
     */
    public IncorrectResultSizeDataAccessException(int expectedSize, int actualSize) {
        super("Incorrect result size: expected " + expectedSize + ", actual " + actualSize);
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }
}
