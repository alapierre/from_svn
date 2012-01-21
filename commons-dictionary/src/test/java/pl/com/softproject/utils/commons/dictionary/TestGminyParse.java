/*
 * Copyright 2011-12-13 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary;

import java.io.File;
import org.junit.Test;
import pl.com.softproject.commons_administrative_division.AdministrativeDivision;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class TestGminyParse {
 
    @Test
    public void test() {
        
        XMLAdministrativeDivisionSerializer serializer = new XMLAdministrativeDivisionSerializer();
        AdministrativeDivision gminy = serializer.fromFile(new File("src/test/resources/gminy.xml"));
        
        System.out.println(">>" + gminy.getProvince());
        
        
    }
    
}
