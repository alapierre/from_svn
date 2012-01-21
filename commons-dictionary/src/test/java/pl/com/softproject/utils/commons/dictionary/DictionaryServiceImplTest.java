/*
 * Copyright 2011-12-13 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary;

import java.io.File;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pl.com.softproject.commons_dictionary.EntryType;
import pl.com.softproject.utils.commons.dictionary.dao.DictionarysDAO;
import pl.com.softproject.utils.commons.dictionary.dao.DictionarysDAOFileSystemImpl;
import pl.com.softproject.utils.commons.dictionary.model.DictionaryEntry;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class DictionaryServiceImplTest {
    
    static DictionaryServiceImpl dictionaryServiceImpl;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        DictionarysDAO dictionarysDAO = new DictionarysDAOFileSystemImpl(new File("src/test/resources/test-parent.xml"));
        dictionaryServiceImpl = new DictionaryServiceImpl(dictionarysDAO);
    }

    /**
     * Test of loadEntrysByParentKey method, of class DictionaryServiceImpl.
     */
    @Test
    public void testLoadRootEntrys() {
        System.out.println("loadEntrysByParentKey");
        String dictionaryKey = "anteny-producenci";
        String parentKey = null;
        
        List<DictionaryEntry> result = dictionaryServiceImpl.loadEntrysByParentKey(dictionaryKey, parentKey);
        assert result != null : "result is null";
        
        print(result);
        
        System.out.println("---------------");
        
        result = dictionaryServiceImpl.loadEntrysByParentKey(dictionaryKey, "grecja");
        print(result);
        
        //compare("nie znaleziono", result, "wartość", "wartość2");
    }

//    @Test
//    public void testLoadEntrys() {
//        System.out.println("testLoadEntrys");
//        String dictionaryKey = "Słownik";
//        String parentKey = "klucz2";
//        
//        List<DictionaryEntry> result = dictionaryServiceImpl.loadEntrysByParentKey(dictionaryKey, parentKey);
//        assert result != null : "result is null";
//        compare("nie znaleziono", result, "wwww");
//    }
//    
//    @Test
//    public void testLoadEmptyEntrys() {
//        System.out.println("testLoadEmptyEntrys");
//        String dictionaryKey = "Słownik";
//        String parentKey = "klucz";
//        
//        List<DictionaryEntry> result = dictionaryServiceImpl.loadEntrysByParentKey(dictionaryKey, parentKey);
//        assert result != null : "result is null";
//        assertTrue("should by empty", result.isEmpty());
//    }
//    
//    @Test
//    public void testLoadNotExistentEntrys() {
//        System.out.println("testLoadNotExistentEntrys");
//        String dictionaryKey = "Słownik";
//        String parentKey = "klucz123456";
//        
//        List<DictionaryEntry> result = dictionaryServiceImpl.loadEntrysByParentKey(dictionaryKey, parentKey);
//        assert result != null : "result is null";
//        assertTrue("should by empty", result.isEmpty());
//    }
//    
//    @Test
//    public void testLoadNotExistentDirectory() {
//        System.out.println("testLoadNotExistentDirectory");
//        String dictionaryKey = "Słownik3456789";
//        String parentKey = "klucz123456";
//        
//        List<DictionaryEntry> result = dictionaryServiceImpl.loadEntrysByParentKey(dictionaryKey, parentKey);
//        assert result != null : "result is null";
//        assertTrue("should by empty", result.isEmpty());
//    }
    
    protected void print(List<DictionaryEntry> result) {
        for(DictionaryEntry value : result) {
            System.out.println(value.toString());
        }
    }
    
    protected void compare(String msg, List<DictionaryEntry> result, String... values) {
        for(String value : values) {
            if(!findValue(result, value)) {
                throw new AssertionError(msg + " " + value);
            }
        }
    }

    protected boolean findValue(List<DictionaryEntry> result, String value) {
        for(DictionaryEntry entry : result) {
            if(entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
