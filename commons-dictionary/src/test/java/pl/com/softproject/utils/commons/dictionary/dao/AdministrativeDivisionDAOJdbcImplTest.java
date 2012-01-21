/*
 * Copyright 2011-12-18 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.dao;

import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import pl.com.softproject.commons_administrative_division.AdministrativeDivision;
import pl.com.softproject.commons_administrative_division.Community;
import pl.com.softproject.utils.commons.dictionary.XMLAdministrativeDivisionSerializer;
import pl.com.softproject.utils.commons.dictionary.util.ToStringBuilder;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
@ContextConfiguration("/test-context.xml")
public class AdministrativeDivisionDAOJdbcImplTest extends AbstractJUnit4SpringContextTests {
    
    @Autowired
    AdministrativeDivisionDAOJdbcImpl administrativeDivisionDAOJdbcImpl; 

//    /**
//     * Test of loadAll method, of class AdministrativeDivisionDAOJdbcImpl.
//     */
//    @Test
//    public void testLoadAll() {
//        System.out.println("loadAll");
//        Date lastSynchronizationTime = null;
//        AdministrativeDivisionDAOJdbcImpl instance = new AdministrativeDivisionDAOJdbcImpl();
//        AdministrativeDivision expResult = null;
//        AdministrativeDivision result = instance.loadAll(lastSynchronizationTime);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of loadCommunitysByDistrictId method, of class AdministrativeDivisionDAOJdbcImpl.
     */
    @Test
    public void testLoadCommunitysByDistrictId() {
        System.out.println("loadCommunitysByDistrictId");
        int distridcId = 1;        
        List<Community> result = administrativeDivisionDAOJdbcImpl.loadCommunitysByDistrictId(distridcId);        
        ToStringBuilder.printCommunity(result);
    }
    
    @Test
    public void testLoadAll() {
        System.out.println("testLoadAll");
        AdministrativeDivision res = administrativeDivisionDAOJdbcImpl.loadAll(null);
        
        XMLAdministrativeDivisionSerializer serializer = new XMLAdministrativeDivisionSerializer();
        System.out.println(res);
        System.out.println(serializer.toString(res));
        
    }

   
}
