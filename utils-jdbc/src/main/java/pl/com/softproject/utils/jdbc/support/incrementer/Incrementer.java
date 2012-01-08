/*
 * Copyright 2009 the original author or authors.
 */

package pl.com.softproject.utils.jdbc.support.incrementer;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public interface Incrementer {

    long getNextId();
    long[] getNextId(int size);

}
