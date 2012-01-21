/*
 * Copyright 2011-12-12 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.dao;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import javax.xml.datatype.XMLGregorianCalendar;
import pl.com.softproject.commons_dictionary.DictionaryType;
import pl.com.softproject.commons_dictionary.Dictionarys;
import pl.com.softproject.commons_dictionary.EntryType;
import pl.com.softproject.utils.commons.dictionary.Constant;
import pl.com.softproject.utils.commons.dictionary.XMLSerializer;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class DictionarysDAOFileSystemImpl implements DictionarysDAO {

    protected File fileName;
    private InputStream inputStream;
    private final Dictionarys dictionarys;
    private boolean cached;
    protected Map<String, List<EntryType>> rootEntrys;
    protected Map<String, Map<String, List<EntryType>>> entrys = new LinkedHashMap<String, Map<String, List<EntryType>>>();
    private Date lastModification;

    public DictionarysDAOFileSystemImpl(File fileName) {
        this.fileName = fileName;
        XMLSerializer serializer = new XMLSerializer();
        dictionarys = serializer.fromFile(fileName);
    }

    public DictionarysDAOFileSystemImpl(InputStream inputStream) {
        this.inputStream = inputStream;
        XMLSerializer serializer = new XMLSerializer();
        dictionarys = serializer.fromStream(this.inputStream);
    }

    protected void cacheIfNeeded() {
        if (!cached) {
            cache();
        }
    }

    protected Date determinateLastModificationDate() {

        XMLGregorianCalendar resutl = null;

        for (DictionaryType dictionary : dictionarys.getDictionary()) {
            
            XMLGregorianCalendar currentLastUpdated = dictionary.getLastUpdated();
            if(currentLastUpdated != null) {
                if (resutl == null || currentLastUpdated.compare(resutl) == 1) {
                    resutl = currentLastUpdated;
                }
            }
        }
        if(resutl == null) return Constant.defaultModifivcationDate;
        else {
            GregorianCalendar calendar = resutl.toGregorianCalendar(TimeZone.getTimeZone("Europe/Warsaw"), new Locale("pl_PL"), null);
            return calendar.getTime();
        }
    }

    protected void cache() {
        rootEntrys = new LinkedHashMap<String, List<EntryType>>();
        for (DictionaryType dictionary : dictionarys.getDictionary()) {

            List<EntryType> tmpRootEntry = new ArrayList<EntryType>();

            for (EntryType entry : dictionary.getEntry()) {
                if (entry.getParentKey() == null) {
                    tmpRootEntry.add(entry);
                }
            }
            rootEntrys.put(dictionary.getName(), tmpRootEntry);
        }
        processLevel();
        lastModification = determinateLastModificationDate();
        cached = true;
    }

    protected Set prepareParentWithChildrenKeys(DictionaryType dictionary) {

        Set<String> result = new HashSet<String>();

        for (EntryType entry : dictionary.getEntry()) {
            if (entry.getParentKey() != null) {
                result.add(entry.getParentKey());
            }

        }

        return result;
    }

    protected void processLevel() {

        LinkedHashMap<String, List<EntryType>> levelEntrys;
        for (DictionaryType dictionary : dictionarys.getDictionary()) {
            levelEntrys = new LinkedHashMap<String, List<EntryType>>();
            for (EntryType entry : dictionary.getEntry()) {
                if (entry.getParentKey() != null) {
                    //String parentKey=entry.getParentKey().toLowerCase();
                    String parentKey = entry.getParentKey();
                    levelEntrys.put(parentKey, sameParent(parentKey, dictionary));
                }
                entrys.put(dictionary.getName(), levelEntrys);
            }
        }
    }

    protected List<EntryType> sameParent(String parentKey, DictionaryType dictionary) {

        List<EntryType> result = new ArrayList<EntryType>();

        for (EntryType entry : dictionary.getEntry()) {
            if (parentKey.equals(entry.getParentKey())) {
                result.add(entry);
            }
        }

        return result;
    }

    @Override
    public Dictionarys loadAll(Date lastSynchronizationTime) {
        cacheIfNeeded();
        return dictionarys;
    }

    @Override
    public List<EntryType> loadEntrysByParentKey(String dictionaryKey, String parentKey) {

        //parentKey = parentKey.toLowerCase();

        if (parentKey == null) {
            return loadRootEntrys(dictionaryKey);
        }

        cacheIfNeeded();
        Map<String, List<EntryType>> dictionary = entrys.get(dictionaryKey);
        if (dictionary != null) {
            return dictionary.get(parentKey);
        }

        return null;
    }

    @Override
    public List<EntryType> loadRootEntrys(String dictionaryKey) {
        cacheIfNeeded();
        return rootEntrys.get(dictionaryKey);
    }

    @Override
    public Date getLastModification() {
        if (lastModification == null) {
            lastModification = determinateLastModificationDate();
        }
        return lastModification;
    }
}
