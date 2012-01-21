/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.commons.dictionary;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import pl.com.softproject.commons_dictionary.Dictionarys;


/**
 *
 * @author adrian
 */
public class XMLSerializer {

    //static Logger logger = Logger.getLogger(XMLSerializer.class);

    private JAXBContext jc;
    private SchemaFactory sf;
    private Schema schema;

    public XMLSerializer() {
        try {
            jc = JAXBContext.newInstance("pl.com.softproject.commons_dictionary");

            sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL url = getClass().getClassLoader().getResource("commons-dictionary.xsd");            
            schema = sf.newSchema(url);
        } catch (SAXException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }

    public Dictionarys fromFile(File file) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            Dictionarys dictionary = (Dictionarys) unmarshaller.unmarshal(file);

            return dictionary;

        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }

    }

    public Dictionarys fromStream(InputStream stream) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            Dictionarys dictionarys = (Dictionarys) unmarshaller.unmarshal(stream);

            return dictionarys;

        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }

    }

    public Dictionarys fromString(String xml) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            Dictionarys dictionarys = (Dictionarys) unmarshaller.unmarshal(new StringReader(xml));
            return dictionarys;
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }

    public void toFile(Dictionarys dictionarys, String fileName) {
        try {
            Marshaller marshaller = jc.createMarshaller();        
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.softproject.com.pl/commons-dictionary http://schema.softproject.com.pl/commons-dictionary/commons-dictionary.xsd");
            marshaller.setSchema(schema);
            marshaller.marshal(dictionarys, new FileOutputStream(fileName));

        } catch (JAXBException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }
    
    public void toFile(Dictionarys dictionarys, String fileName, String encoding) {
        try {
            Marshaller marshaller = jc.createMarshaller();        
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
                        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.softproject.com.pl/commons-dictionary http://schema.softproject.com.pl/commons-dictionary/commons-dictionary.xsd");
            marshaller.marshal(dictionarys, new FileOutputStream(fileName));

        } catch (JAXBException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }

    public String toString(Dictionarys dictionarys) {
        try {
            Marshaller marshaller = jc.createMarshaller();        
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.softproject.com.pl/commons-dictionary http://schema.softproject.com.pl/commons-dictionary/commons-dictionary.xsd");
            marshaller.setSchema(schema);
            StringWriter sw = new StringWriter();
            marshaller.marshal(dictionarys, sw);
            
            return sw.toString();
            
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        } 
        
    }

}
