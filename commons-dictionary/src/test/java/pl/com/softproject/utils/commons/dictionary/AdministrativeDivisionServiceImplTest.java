/*
 * Copyright 2011-12-13 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary;

import java.io.File;
import java.util.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.com.softproject.commons_administrative_division.Community;
import static org.junit.Assert.*;
import pl.com.softproject.commons_administrative_division.District;
import pl.com.softproject.commons_administrative_division.Province;
import static pl.com.softproject.utils.commons.dictionary.util.ToStringBuilder.*;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class AdministrativeDivisionServiceImplTest {
    
    static AdministrativeDivisionServiceImpl instance;

    @BeforeClass
    public static void setUpClass() throws Exception {        
        instance = new AdministrativeDivisionServiceImpl(new File("src/test/resources/gminy.xml"));
    }
        
    @Test
    public void testLastModificationDate() {
        Date result = instance.getLastModifiaction();
        System.out.println("r: " + result);
    }
    
    /**
     * Test of loadProvince method, of class AdministrativeDivisionServiceImpl.
     */
    @Test
    public void testLoadProvince() {
        System.out.println("loadProvince");
        
        List expResult = null;
        List result = instance.loadProvince();
        
        printProvince(result);
    }

    /**
     * Test of loadDistrictsForProvinceName method, of class AdministrativeDivisionServiceImpl.
     */
    @Test
    public void testLoadDistrictsForProvinceName() {
        System.out.println("loadDistrictsForProvinceName");
        String provinceName = "Mazowieckie";
        
        List expResult = null;
        List result = instance.loadDistrictsForProvinceName(provinceName);
        
        printDistrict(result);
    }

    /**
     * Test of loadCommunitysForDistrictName method, of class AdministrativeDivisionServiceImpl.
     */
    @Test
    public void testLoadCommunitysForDistrictName() {
        System.out.println("loadCommunitysForDistrictName");
        String districtName = "Warszawski Zachodni";
        
        List expResult = null;
        List result = instance.loadCommunitysForDistrictName(districtName);
        
        printCommunity(result);
        
    }

    
    
//    protected void compare(String msg, List<Object> result, String... values) {
//        for(String value : values) {
//            if(!findValue(result, value)) {
//                throw new AssertionError(msg + " " + value);
//            }
//        }
//    }
//
//    protected boolean findValue(List<Object> result, String value) {
//        for(Object entry : result) {
//            if(entry.getValue().equals(value)) {
//                return true;
//            }
//        }
//        return false;
//    }
    
}
