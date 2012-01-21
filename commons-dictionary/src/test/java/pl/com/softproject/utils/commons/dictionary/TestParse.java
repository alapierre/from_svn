/*
 * Copyright 2011-12-08 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary;

import java.io.File;
import org.junit.Test;
import pl.com.softproject.commons_dictionary.Dictionarys;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class TestParse {
 
    @Test
    public void testParseXML() {
        
        XMLSerializer serializer = new XMLSerializer();
        Dictionarys res = serializer.fromFile(new File("src/main/xsd/sample.xml"));
        System.out.println("res: " + res.getDictionary().get(0).getName());
    }
    
}
