/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.xml;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author adrian
 */
public class XMLValidator {

   /**
     * Sprawdza sk³adnie XML w przekazanym dokumencie
     *
     * @param reader - Reader do dokumentu XML
     * @param errors - zainicjowana kolekcja, w której zostan¹ zwró³cone b³êdy. Kolekcja zostanie wyzerowana.
     * @return
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws java.io.IOException
     */
    public static boolean checkSyntax(Reader reader, Collection<SAXParseException> errors) throws ParserConfigurationException, IOException {

       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
       dbf.setValidating(false);
       dbf.setNamespaceAware(true);

       DocumentBuilder builder = dbf.newDocumentBuilder();
       builder.setErrorHandler(new XMLErrorHandler(errors));

       //InputSource is = new InputSource(filename);
       InputSource source = new InputSource(reader);
        try {
            builder.parse(source);
        } catch (SAXException ignore) {}

       return errors.isEmpty();
    }

    /**
     * Validuje XML wzglêdem XML Schemy
     *
     * @param reader - Reader do dokumentu XML
     * @param schemaLocation - url do shcemy XML, jeœli jest null to zostanie u¿yta schema wskazana w atrybucie schemaLocation z dokumentu XML
     * @param errors  - zainicjowana kolekcja, w której zostan¹ zwrócone b³êdy. Kolekcja zostanie wyzerowana.
     * @return - true jeœli dokument validuje siê
     * @throws org.xml.sax.SAXException - jeœli nie mo¿na zainicjowaæ parsera
     * @throws java.io.IOException - jeœli nie mo¿na czytaæ z Readera
     */
    public static boolean validate(Reader reader, URL schemaLocation, Collection<SAXParseException> errors) throws SAXException, IOException {
    //List<SAXParseException> errors = new LinkedList<SAXParseException>();

        errors.clear();

        // 1. Lookup a factory for the W3C XML Schema language
        SchemaFactory factory =
            SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

        // 2. Compile the schema.
        Schema schema = null;
        //schema = factory.newSchema(new URL("http://www.dmh.pl/schema/dsml/dsml-1_2_14/dsml.xsd"));
        //schema = factory.newSchema(new File("D:/realizacje/CharSource/svn-nowy/dsml/dsml-core/src/main/xsd/dsml.xsd"));
        if(schemaLocation == null)
            schema = factory.newSchema();
        else
            schema = factory.newSchema(schemaLocation);

        // 3. Get a validator from the schema.
        Validator validator = schema.newValidator();

        // 4. Parse the document you want to check.
        Source source = new StreamSource(reader);

        // 5. Check the document

        validator.setErrorHandler(new XMLErrorHandler(errors));
        validator.validate(source);

        return errors.isEmpty();
    }

    /**
     * Validuje XML wzglêdem XML Schemy, lokalizacja schemy bêdzie pobrana z atrybutu schemaLocation z dokumentu XML
     *
     * @param reader - Reader do dokumentu XML
     * @param errors  - zainicjowana kolekcja, w której zostan¹ zwrócone b³êdy. Kolekcja zostanie wyzerowana.
     * @return - true jeœli dokument validuje siê
     * @throws org.xml.sax.SAXException - jeœli nie mo¿na zainicjowaæ parsera
     * @throws java.io.IOException - jeœli nie mo¿na czytaæ z Readera
     */
    public static boolean validate(Reader reader, Collection<SAXParseException> errors) throws SAXException, IOException {

        return validate(reader, null, errors);
    }

    public static class XMLErrorHandler implements ErrorHandler {

        public XMLErrorHandler(Collection<SAXParseException> errors) {
            this.errors = errors;
        }

        private Collection<SAXParseException> errors;

        @Override
        public void warning(SAXParseException exception) throws SAXException {
            errors.add(exception);
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            errors.add(exception);
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            errors.add(exception);
        }

    }

}
