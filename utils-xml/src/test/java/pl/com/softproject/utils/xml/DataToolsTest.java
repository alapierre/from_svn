/*
 * Copyright 2011-08-31 the original author or authors.
 */

package pl.com.softproject.utils.xml;

import java.util.Date;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class DataToolsTest {
    
    
    /**
     * Test of countDaysBetween method, of class DataTools.
     */
   
    /**
     * Test of formatDateWithTime method, of class DataTools.
     */
    @Test
    public void testFormatDateWithTime() {
        System.out.println("formatDateWithTime");
        Date date = new Date();
        String expResult = "";
        String result = DataTools.formatDateWithTime(date);
        
        System.out.println(result);
        
    }

   
    
}
