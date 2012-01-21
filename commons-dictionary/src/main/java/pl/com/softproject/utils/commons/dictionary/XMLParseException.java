/*
 * Copyright 2011-08-11 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class XMLParseException extends RuntimeException {

    public XMLParseException(Throwable cause) {
        super(cause);
    }

    public XMLParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMLParseException(String message) {
        super(message);
    }

    public XMLParseException() {
    }
    
}
