/*
 * Copyright 2011-12-18 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.dao;

import java.util.Date;
import pl.com.softproject.commons_administrative_division.AdministrativeDivision;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public interface AdministrativeDivisionDAO {
    
    AdministrativeDivision loadAll(Date lastSynchronizationTime);

    Date getLastModification();
    
}
