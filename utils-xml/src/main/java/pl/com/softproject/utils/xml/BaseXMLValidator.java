/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.com.softproject.utils.xml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author gchas
 */
public class BaseXMLValidator {
    
    private JAXBContext jc;
    private SchemaFactory sf;
    private Schema schema;
    
    public String schemaLoaction;//= "http://www.uke.gov.pl/euro http://schema.softproject.com.pl/uke/uke-euro.xsd";

    public BaseXMLValidator(String contextPath, String xsdFileName, String schemaLocation) {
        this.schemaLoaction = schemaLocation;
        
        try {
            jc = JAXBContext.newInstance(contextPath);

            sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI); 
            URL url = getClass().getClassLoader().getResource(xsdFileName);            
            schema = sf.newSchema(url);
        } catch (SAXException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }
    
    /**
     * Validuje XML względem XML Schemy, lokalizacja schemy b�dzie pobrana z atrybutu schemaLocation z dokumentu XML
     * Metoda z założenia, nigdy nie rzuca wyjątkami. Gdy walidacje nie przejdzie zwraca po prostu "false".
     * @param <T>
     * @param dsml - dokument który powstał w wyniku wywołania metody "unmarshal". Np. DsmlDocument lub DomainsDocument.
     * @param xsdFileName - nazwa pliku xsd, jeśli jest null to zostanie użyta schema wskazana w atrybucie schemaLocation z dokumentu XML.
     * @param exceptions - kolekcja, w której zostaną zwrócone błędy.
     * @return - true jeśli dokument przechodzi poprawnie walidację.
     */
    public <T> boolean validate(T dsml, List<SAXParseException> exceptions) {          
        
            try {
                JAXBSource source = new JAXBSource(jc, dsml);
               
                Validator validator = schema.newValidator();        

                if (exceptions == null)
                    exceptions = new ArrayList<SAXParseException>();

                validator.setErrorHandler(new XMLValidator.XMLErrorExtensionHandler(exceptions));        
                validator.validate(source);

                return exceptions.isEmpty();
    
            } catch (SAXException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            } catch(JAXBException ex) {                                                                                                                                                                                       
                throw new RuntimeException(ex.getMessage(), ex);                                                                                                                                    
            } catch(IOException ex) {
                throw new XMLParseException(ex.getMessage(), ex);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }           
    }
}
