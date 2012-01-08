/*
 * Copyright 2009 the original author or authors.
 */
package pl.com.softproject.utils.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class XSLTTransformator {

    private Transformer transformer;

    public XSLTTransformator(File xsltFile) throws FileNotFoundException, TransformerConfigurationException {

        InputStream xsltImputStream = new FileInputStream(xsltFile);
        Source xsltSource = new StreamSource(xsltImputStream);

        TransformerFactory transFactory = TransformerFactory.newInstance();
        transformer = transFactory.newTransformer(xsltSource);
       
        //transformer.setOutputProperty(OutputKeys.ENCODING, "windows-1250"); 
    }

    public XSLTTransformator(InputStream is) throws FileNotFoundException, TransformerConfigurationException {

        Source xsltSource = new StreamSource(is);
        TransformerFactory transFactory = TransformerFactory.newInstance();
        transformer = transFactory.newTransformer(xsltSource);
        //transformer.setOutputProperty(OutputKeys.ENCODING, "windows-1250"); 
    }

    public void setEncoding(String encoding) {
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
    }
    
    public String getEncoding() {
        return transformer.getOutputProperty(OutputKeys.ENCODING);
    }
    
    public void transform(Reader in, OutputStream out) throws TransformerException {

        Result streamResult = new StreamResult(out);
        transformer.transform(new StreamSource(in), streamResult);

    }
    
     public void transform(Reader in, Writer out) throws TransformerException {

        Result streamResult = new StreamResult(out);
        transformer.transform(new StreamSource(in), streamResult);

    }

    public void transform(Source in, OutputStream out) throws TransformerException {
        Result streamResult = new StreamResult(out);
        transformer.transform(in, streamResult);
    }
    
    public void transform(Source in, Result out) throws TransformerException {        
        transformer.transform(in, out);
    }
    
    public String transform(String source) throws TransformerException {

        OutputStream result = new ByteArrayOutputStream();

        transform(new StringReader(source), result);
        return result.toString();
    }
    
    public void transform(String source, OutputStream out) throws TransformerException {        
        transform(new StringReader(source), out);
    }
}
