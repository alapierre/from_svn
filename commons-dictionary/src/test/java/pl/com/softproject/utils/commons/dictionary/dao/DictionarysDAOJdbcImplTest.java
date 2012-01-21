/*
 * Copyright 2011-12-09 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.dao;

import java.io.File;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import pl.com.softproject.commons_dictionary.Dictionarys;
import pl.com.softproject.commons_dictionary.EntryType;
import pl.com.softproject.utils.commons.dictionary.XMLSerializer;
import pl.com.softproject.utils.commons.dictionary.dao.DictionarysDAOJdbcImpl.Dictionary;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
@ContextConfiguration("/test-context.xml")
public class DictionarysDAOJdbcImplTest extends AbstractJUnit4SpringContextTests {
    
    @Autowired
    DictionarysDAOJdbcImpl dictionarysDAO;
    
    public DictionarysDAOJdbcImplTest() {
    }

    @Test
    public void testGetLastModification() {
        System.out.println("testGetLastModification");
        
        Date res = dictionarysDAO.getLastModification();
        System.out.println("ddd:" + res);
    }
    
    /**
     * Test of loadAll method, of class DictionarysDAOJdbcImpl.
     */
    @Test
    public void testLoadAll() throws Exception{
        System.out.println("loadAll");
        Date lastSynchronizationTime = null;
    
        Dictionarys result = dictionarysDAO.loadAll(lastSynchronizationTime);
        XMLSerializer serializer = new XMLSerializer();
        
        File file = File.createTempFile("dictionary", ".xml");        
        serializer.toFile(result, file.getAbsolutePath());
        
        System.out.println("file " + file);
        
        for(EntryType entry : result.getDictionary().get(0).getEntry()) {
            System.out.println("entry=" + entry.getValue());
        }
        
        
    }
    
    @Test
    public void testLoadAll2() throws Exception{
        System.out.println("loadAll2");
        Date lastSynchronizationTime = null;
    
        Dictionarys result = dictionarysDAO.loadAll(lastSynchronizationTime);
        XMLSerializer serializer = new XMLSerializer();
        
        String str = serializer.toString(result);
        System.out.println("str " + str);
        
    }

    /**
     * Test of loadDictionaryNames method, of class DictionarysDAOJdbcImpl.
     */
    @Test
    public void testLoadDictionaryNames() {
        System.out.println("loadDictionaryNames");
        List<Dictionary> result = dictionarysDAO.loadDictionaryNames();
        System.out.println("res=" + result);
        
    }

}
