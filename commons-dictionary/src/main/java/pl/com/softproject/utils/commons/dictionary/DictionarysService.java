/*
 * Copyright 2011-12-09 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary;

import java.util.Date;
import java.util.List;
import pl.com.softproject.utils.commons.dictionary.model.DictionaryEntry;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public interface DictionarysService {
    
    /**
     * Load all dictionarys and cache it
     * @return 
     */
    public void loadAll();
    
    /**
     * Refresh cached dictionary data
     * @param force if true date will by retroved from datasource even are did not change from last reload
     */
    public void refresh(boolean force);
    
    /**
     * Retrive from local cache dictionary entrys for given dictionary name and parent key
     * 
     * @param dictionaryName
     * @param parentKey if null - first level of entrys will by retrived
     * @return 
     */
    public List<DictionaryEntry> loadEntrysByParentKey(String dictionaryName, String parentKey);
    
    /**
     * 
     * @return 
     */
    Date getLastModifiaction();
    
}
