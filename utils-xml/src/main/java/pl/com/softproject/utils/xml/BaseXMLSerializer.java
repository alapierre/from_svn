/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.xml;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.*;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;



/**
 * Klasa serializera dla pliku XML wniosku EURO
 * 
 * @author adrian
 */
public class BaseXMLSerializer<T> {

    static Logger logger = Logger.getLogger(BaseXMLSerializer.class);

    private JAXBContext jc;
    private SchemaFactory sf;
    private Schema schema;
    
    public String schemaLoaction;//= "http://www.uke.gov.pl/euro http://schema.softproject.com.pl/uke/uke-euro.xsd";

    public BaseXMLSerializer(String contextPath, String xsdFileName, String schemaLocation) {
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

    public T fromFile(File file) {
        return fromFile(file, true);
    }
    
    public T fromFile(File file, boolean validate) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            if(validate)
                unmarshaller.setSchema(schema);
            T document = (T) unmarshaller.unmarshal(file);

            return document;

        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }

    }

    public T fromStream(InputStream stream) {
        return fromStream(stream, true);
    }
    
    public T fromStream(InputStream stream, boolean validate) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            if(validate)
                unmarshaller.setSchema(schema);
            T document = (T) unmarshaller.unmarshal(stream);

            return document;

        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }

    }

    public T fromReader(Reader reader) {
        return fromReader(reader, true);
    }
    
    public T fromReader(Reader reader, boolean validate) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            if(validate)
                unmarshaller.setSchema(schema);
            T document = (T) unmarshaller.unmarshal(reader);

            return document;

        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }

    }
    
    public T fromString(String xml) {
        return fromString(xml, true);
    }
    
    public T fromString(String xml, boolean validate) {
        try {
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            if(validate)
                unmarshaller.setSchema(schema);
            T wniosek = (T) unmarshaller.unmarshal(new StringReader(xml));
            return wniosek;
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }

    public void toFile(T dictionarys, String fileName) {
        toFile(dictionarys, fileName, true);
    }
    
    public void toFile(T document, String fileName, boolean validate) {
        try {
            Marshaller marshaller = jc.createMarshaller();        
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLoaction);
            if(validate)
                marshaller.setSchema(schema);
            marshaller.marshal(document, new FileOutputStream(fileName));

        } catch (JAXBException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }
    
    public void toFile(T document, String fileName, String encoding) {
        toFile(document, fileName, encoding, true);
    }
    
    public void toFile(T document, String fileName, String encoding, boolean validate) {
        try {
            Marshaller marshaller = jc.createMarshaller();        
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLoaction);
            if(validate)
                marshaller.setSchema(schema);
            marshaller.marshal(document, new FileOutputStream(fileName));

        } catch (JAXBException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }
    }

    public String toString(T document) {
        return toString(document, true);
    }
    
    public String toString(T document, boolean validate) {
        try {
            Marshaller marshaller = jc.createMarshaller();        
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLoaction);
            if(validate)
                marshaller.setSchema(schema);
            StringWriter sw = new StringWriter();          
            marshaller.marshal(document, sw);
            
            return sw.toString();
            
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        } 
        
    }

    public boolean validate(T dsml, List<SAXParseException> exceptions) {          
        try {
            JAXBSource source = new JAXBSource(jc, dsml);
            Validator validator = schema.newValidator();        
            if (exceptions == null)
                exceptions = new ArrayList<SAXParseException>();
                
            validator.setErrorHandler(new MyExceptionHandler(exceptions));        
            validator.validate(source);
            
            if (!exceptions.isEmpty()) {
                return false;
            } else {
                return true;
            }            
        } catch (SAXException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        } catch(JAXBException ex) {                                                                                                                                                                                       
            throw new RuntimeException(ex.getMessage(), ex);                                                                                                                                    
        } catch(IOException ex) {                                                                                                                                                                                       
            throw new XMLParseException(ex.getMessage(), ex);                                                                                                                                   
        } 
    }
    
    public JAXBElement convertFromDomNode(Node domNode, Class jaxbType) {
        
        try {            
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return unmarshaller.unmarshal(domNode, jaxbType);            
            
        } catch (JAXBException ex) {
            throw new XMLParseException(ex.getMessage(), ex);
        }      
    }
    
    private class MyExceptionHandler implements ErrorHandler {
 
        List<SAXParseException> exceptions = null;
        
        public MyExceptionHandler(List<SAXParseException> exceptions) {
            this.exceptions = exceptions;
        }
        
        public void warning(SAXParseException exception) throws SAXException {
            logger.warn("", exception);
        }

        public void error(SAXParseException exception) throws SAXException {
            exceptions.add(exception);
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            exceptions.add(exception);
        }
    }
}
