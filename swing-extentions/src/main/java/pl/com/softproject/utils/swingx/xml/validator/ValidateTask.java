/*
 * Copyright 2013-03-26 the original author or authors.
 */
package pl.com.softproject.utils.swingx.xml.validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JOptionPane;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import pl.com.softproject.utils.swingworker.WindowBlocker;
import pl.com.softproject.utils.swingx.BaseTask;
import pl.com.softproject.utils.xml.XMLValidator;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class ValidateTask extends BaseTask<Collection<SAXParseException>, Void>{

    private String url;
    private Collection<SAXParseException> list;
    private WindowBlocker windowBlocker;

    public ValidateTask(String url, WindowBlocker blocker, Collection<SAXParseException> list) {
        this.url = url;
        this.list = list;
        setBlocker(blocker);
        this.windowBlocker = blocker;
        block();
    }
    
    @Override
    protected Collection<SAXParseException> doInBackground() throws Exception {
        
        URL xmlUrl = new URL(url);
        BufferedReader reader = new BufferedReader(new InputStreamReader(xmlUrl.openStream()));
        
        //Reader reader = new FileReader(new File(url));
        boolean result = XMLValidator.validate(reader, list);
        
        if(result) {
            JOptionPane.showMessageDialog(windowBlocker, "XML is valid", "Validation result", JOptionPane.INFORMATION_MESSAGE);            
        } else {
            JOptionPane.showMessageDialog(windowBlocker, "XML is NOT valid", "Validation result", JOptionPane.ERROR_MESSAGE);
        }
        
        return list;
    }
    
}
