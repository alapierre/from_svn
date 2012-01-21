/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.commons.dictionary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
//import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import pl.com.softproject.commons_administrative_division.AdministrativeDivision;


/**
 *
 * @author adrian
 */
public class XMLAdministrativeDivisionSerializer {

    //static Logger logger = Logger.getLogger(XMLSerializer.class);

    private JAXBContext jc;
    private SchemaFactory sf;
    private Schema schema;

    public XMLAdministrativeDivisionSerializer() {
        try {
            jc = JAXBContext.newInstance("pl.com.softproject.commons_administrative_division");

            sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL url = getClass().getClassLoader().getResource("administrative-division.xsd");            
            schema = sf.newSchema(url);
        } catch (SAXException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }

    public AdministrativeDivision fromFile(File file) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            AdministrativeDivision administrativeDivision = (AdministrativeDivision) unmarshaller.unmarshal(file);

            return administrativeDivision;

        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }

    }

    public AdministrativeDivision fromStream(InputStream stream) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            AdministrativeDivision administrativeDivision = (AdministrativeDivision) unmarshaller.unmarshal(stream);

            return administrativeDivision;

        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }

    }

    public AdministrativeDivision fromString(String xml) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            AdministrativeDivision administrativeDivision = (AdministrativeDivision) unmarshaller.unmarshal(new StringReader(xml));
            return administrativeDivision;
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }

    public void toFile(AdministrativeDivision administrativeDivision, String fileName) {
        try {
            Marshaller marshaller = jc.createMarshaller();        
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.softproject.com.pl/commons-administrative-division http://schema.softproject.com.pl/commons-dictionary/administrative-division.xsd");
            marshaller.setSchema(schema);
            marshaller.marshal(administrativeDivision, new FileOutputStream(fileName));

        } catch (JAXBException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }
    
    public void toFile(AdministrativeDivision administrativeDivision, String fileName, String encoding) {
        try {
            Marshaller marshaller = jc.createMarshaller();        
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.softproject.com.pl/commons-administrative-division http://schema.softproject.com.pl/commons-dictionary/administrative-division.xsd");
            marshaller.marshal(administrativeDivision, new FileOutputStream(fileName));

        } catch (JAXBException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }

    public String toString(AdministrativeDivision administrativeDivision) {
        try {
            Marshaller marshaller = jc.createMarshaller();        
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.softproject.com.pl/commons-administrative-division http://schema.softproject.com.pl/commons-dictionary/administrative-division.xsd");
            marshaller.setSchema(schema);
            StringWriter sw = new StringWriter();
            marshaller.marshal(administrativeDivision, sw);
            
            return sw.toString();
            
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        } 
        
    }

}
