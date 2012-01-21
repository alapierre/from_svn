/*
 * Copyright 2011-12-09 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import pl.com.softproject.commons_dictionary.EntryType;
import pl.com.softproject.utils.commons.dictionary.dao.DictionarysDAO;
import pl.com.softproject.utils.commons.dictionary.model.DictionaryEntry;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class DictionaryServiceImpl implements DictionarysService {
    
    private DictionarysDAO dictionarysDAO;

    public DictionaryServiceImpl() {
    }

    public DictionaryServiceImpl(DictionarysDAO dictionarysDAO) {
        this.dictionarysDAO = dictionarysDAO;
    }

    @Override
    public void loadAll() {
        dictionarysDAO.loadAll(null);
    }

    @Override
    public List<DictionaryEntry> loadEntrysByParentKey(String dictionaryKey, String parentKey) {
        return copy(dictionarysDAO.loadEntrysByParentKey(dictionaryKey, parentKey));
    }

    protected List<DictionaryEntry> copy(List<EntryType> source) {
        List<DictionaryEntry> result = new ArrayList<DictionaryEntry>();
        if(source == null) return result;
        for(EntryType tmp: source) {
            result.add(new DictionaryEntry(tmp));
        }
        return result;
    }
    
    @Override
    public void refresh(boolean force) {
        dictionarysDAO.loadAll(null);
    }

    public void setDictionarysDAO(DictionarysDAO dictionarysDAO) {
        this.dictionarysDAO = dictionarysDAO;
    }

    @Override
    public Date getLastModifiaction() {
        return this.dictionarysDAO.getLastModification();
    }

}
