/*
 * Copyright 2012-01-24 the original author or authors.
 */
package pl.com.softproject.utils.text;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class AmountInWordsTest {
    
    /**
     * Test of toWords method, of class AmountInWords.
     */
    @Test
    public void testToWordsZero() {
        System.out.println("toWordsZero");
        double kwota = 0.0;
        String expResult = "zero z� zero gr";
        String result = AmountInWords.toWords(kwota);
        System.out.println("result: " + result);
        assertEquals(expResult, result);
    }
    
    
    @Test
    public void testToWords100() {
        System.out.println("toWords");
        double kwota = 123.50;
        String expResult = "sto dwadzie�cia trzy z� pi��dziesi�t gr";
        String result = AmountInWords.toWords(kwota);
        System.out.println("result: " + result);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testToWords126() {
        System.out.println("toWords");
        double kwota = 126.50;
        String expResult = "sto dwadzie�cia sze�� z� pi��dziesi�t gr";
        String result = AmountInWords.toWords(kwota);
        System.out.println("result: " + result);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testToWords101() {
        System.out.println("toWords");
        double kwota = 124.50;
        String expResult = "sto dwadzie�cia cztery z� pi��dziesi�t gr";
        String result = AmountInWords.toWords(kwota);
        System.out.println("result: " + result);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testToWords113() {
        System.out.println("toWords");
        double kwota = 113.20;
        String expResult = "sto trzyna�cie z� dwadzie�cia gr";
        String result = AmountInWords.toWords(kwota);
        System.out.println("result: " + result);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testToWords1() {
        System.out.println("toWords");
        double kwota = 3;
        String expResult = "trzy z� zero gr";
        String result = AmountInWords.toWords(kwota);
        System.out.println("result: " + result);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testToWords1000() {
        System.out.println("toWords");
        double kwota = 1237.24;
        String expResult = "tysi�c dwie�cie trzydzie�ci siedem z� dwadzie�cia cztery gr";
        String result = AmountInWords.toWords(kwota);
        System.out.println("result: " + result);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testLiczba_groszy() {
        
        double liczba = 123.6456;
        long result = AmountInWords.liczba_groszy(liczba);
        assertEquals(64L, result);
        
    }
}
