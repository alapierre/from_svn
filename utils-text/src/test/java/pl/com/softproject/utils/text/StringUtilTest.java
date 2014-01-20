/*
 * Copyright 2012-12-13 the original author or authors.
 */
package pl.com.softproject.utils.text;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class StringUtilTest {
    
    public StringUtilTest() {
    }

    /**
     * Test of swapCase method, of class StringUtil.
     */
    //@Test
    public void testSwapCase() {
        System.out.println("swapCase");
        String oryginal = "";
        String expResult = "";
        String result = StringUtil.swapCase(oryginal);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isOnlyFirstLetterUpperCase method, of class StringUtil.
     */
    //@Test
    public void testIsOnlyFirstLetterUpperCase() {
        System.out.println("isOnlyFirstLetterUpperCase");
        String str = "";
        boolean expResult = false;
        boolean result = StringUtil.isOnlyFirstLetterUpperCase(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isCapitalaze method, of class StringUtil.
     */
    @Test
    public void testIsCapitalaze() {
        System.out.println("isCapitalaze");
        System.out.println(StringUtil.isCapitalaze("ala Ma Kota"));
    }

    /**
     * Test of isLowerCase method, of class StringUtil.
     */
    //@Test
    public void testIsLowerCase() {
        System.out.println("isLowerCase");
        String str = "";
        boolean expResult = false;
        boolean result = StringUtil.isLowerCase(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isUpperCase method, of class StringUtil.
     */
    //@Test
    public void testIsUpperCase() {
        System.out.println("isUpperCase");
        String str = "";
        boolean expResult = false;
        boolean result = StringUtil.isUpperCase(str);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of uncapitalize method, of class StringUtil.
     */
    //@Test
    public void testUncapitalize() {
        System.out.println("uncapitalize");
        String str = "";
        char[] delimiters = null;
        String expResult = "";
        String result = StringUtil.uncapitalize(str, delimiters);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of capitalize method, of class StringUtil.
     */
    //@Test
    public void testCapitalize() {
        System.out.println("capitalize");
        String str = "";
        char[] delimiters = null;
        String expResult = "";
        String result = StringUtil.capitalize(str, delimiters);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSplitLinesAsList() {
        
        int len = 30;
        boolean ok = true;
        
        System.out.println("testSplitLinesAsList");
        List<String> res = StringUtil.splitLinesAsList("bardzo d�ugi tekst, ponad 30 znak�w w jednej linii tekstu.. Trzeba go podzieli�.", len);
        
        for(String str : res) {
            System.out.println("'" + str + "' -> " + str.length());
            if(str.length()> len) {
                ok = false;
            }
        }
        
        assert ok : "co� nie tak";
        
    }
    
    @Test
    public void testSplitLinesAsListNoWithChars() {
        
        int len = 30;
        boolean ok = true;
        
        
        System.out.println("testSplitLinesAsListNoWithChars");
        List<String> res = StringUtil.splitLinesAsList("bardzod�ugitekst,ponad30znak�wwjednejliniitekstu.Trzebagopodzieli�.", len);
        
        for(String str : res) {
            System.out.println("'" + str + "' -> " + str.length());
            if(str.length()> len) {
                ok = false;
            }
        }
        
        assert ok : "co� nie tak";
    }
    
    @Test
    public void testSplitLinesAsListShort() {
        
        int len = 30;
        boolean ok = true;        
        String str = "kr�tki tekst";
        
        System.out.println("testSplitLinesAsListShort");
        List<String> res = StringUtil.splitLinesAsList(str, len);
        
        assert res.size() == 1 : "za du�o element�w w res";
        
        assert res.get(0).equals(str) : "nieprawid�owy tekst";
        
        
        
    }
}
