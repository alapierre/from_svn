/*
 * Copyright 2011-12-09 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.dao;

import java.util.Date;
import java.util.List;
import pl.com.softproject.commons_dictionary.Dictionarys;
import pl.com.softproject.commons_dictionary.EntryType;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public interface DictionarysDAO {
    
    Dictionarys loadAll(Date lastSynchronizationTime);

    List<EntryType> loadEntrysByParentKey(String dictionaryKey, String parentKey);

    List<EntryType> loadRootEntrys(String dictionaryKey);
    
    Date getLastModification();

}
