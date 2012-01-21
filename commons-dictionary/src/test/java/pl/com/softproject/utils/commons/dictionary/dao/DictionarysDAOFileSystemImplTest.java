/*
 * Copyright 2011-12-12 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.dao;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import junit.framework.Assert;
import org.junit.Test;
import pl.com.softproject.commons_dictionary.Dictionarys;
import pl.com.softproject.commons_dictionary.EntryType;
import pl.com.softproject.utils.commons.dictionary.Constant;
import pl.com.softproject.utils.commons.dictionary.util.UtilUtil;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class DictionarysDAOFileSystemImplTest {

    static final String fileName = "src/test/resources/test-multi-level.xml";
    
    @Test
    public void testDeterminateLastModificationDate() throws Exception {
        
        DictionarysDAOFileSystemImpl instance = new DictionarysDAOFileSystemImpl(new File("src/test/resources/test-last-modified.xml"));
        
        Date resDate = instance.determinateLastModificationDate();
        
        System.out.println("res: " + resDate);
        Assert.assertEquals("test default date", Constant.defaultModifivcationDate, resDate);
        
        System.out.println("res2: " + instance.getLastModification());
        Assert.assertEquals("test default date from getLastModification()", resDate, instance.getLastModification());
        
        instance = new DictionarysDAOFileSystemImpl(new File("src/test/resources/test-last-modified_1.xml"));
        resDate = instance.determinateLastModificationDate();
        System.out.println("res: " + resDate);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expected = df.parse("2011-12-21 00:00:00");
        
        Assert.assertEquals("from test-last-modified_1.xml", expected, resDate);
        
        instance = new DictionarysDAOFileSystemImpl(new File("src/test/resources/test-last-modified_2.xml"));
        resDate = instance.determinateLastModificationDate();
        System.out.println("res: " + resDate);
        
        Assert.assertEquals("from test-last-modified_2.xml", expected, resDate);
    }
    
    @Test
    public void testPrepareAllParentKeys() {
        
        DictionarysDAOFileSystemImpl instance = new DictionarysDAOFileSystemImpl(new File(fileName));
        
        Dictionarys dict = instance.loadAll(null);
        Set result = instance.prepareParentWithChildrenKeys(dict.getDictionary().get(0));
        
        System.out.println("res: " +result);
        assertContains(result, "k1.2", "k1", "k1.1");
    }
    
    protected void assertContains(Set<String> set, String... values) {
        for(String value : values) {
            assert set.contains(value) : "set not contain " + value;
        }
    }
    
    @Test
    public void testElements() {

        DictionarysDAOFileSystemImpl instance = new DictionarysDAOFileSystemImpl(new File(fileName));
        instance.cache();
        
        System.out.println("level 1");
        List<EntryType> entrys = instance.loadEntrysByParentKey("Słownik", "k1");
        UtilUtil.print(entrys);
        UtilUtil.compareKeys("no key", entrys, "k1.1", "k1.2");
        
        entrys = instance.loadEntrysByParentKey("Słownik", "k2");
        assert entrys == null : "k1 nie posiada dzieci";
        
        System.out.println("level 2 - k1");        
        entrys = instance.loadEntrysByParentKey("Słownik", "k1.1");
        UtilUtil.print(entrys);
        UtilUtil.compareKeys("no key", entrys, "k1.1.1", "k1.1.2");
        
        System.out.println("level 2 - k2");        
        entrys = instance.loadEntrysByParentKey("Słownik", "k1.2");
        UtilUtil.print(entrys);
        UtilUtil.compareKeys("no key", entrys, "k1.2.1");
    
        System.out.println("level 4");
        entrys = instance.loadEntrysByParentKey("Słownik2", "k1.1.2");
        UtilUtil.print(entrys);
        UtilUtil.compareKeys("no key", entrys, "k1.1.1.1");
        
    }
    
    /**
     * Test of cache method, of class DictionarysDAOFileSystemImpl.
     */
    @Test
    public void testCache() {
        System.out.println("cache");
        DictionarysDAOFileSystemImpl instance = new DictionarysDAOFileSystemImpl(new File(fileName));
        instance.cache();
        //System.out.println("root: " + instance.rootEntrys);
        //TestUtil.print(instance.rootEntrys, instance.entrys);
        
        UtilUtil.printR(instance.loadRootEntrys("Słownik"), instance, "Słownik");
        
        System.out.println("\nSłownik2");
        
        UtilUtil.printR(instance.loadRootEntrys("Słownik2"), instance, "Słownik2");
    }
    
}
