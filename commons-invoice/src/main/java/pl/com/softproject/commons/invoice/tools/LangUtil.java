/*
 * Copyright 2013-04-26 the original author or authors.
 */
package pl.com.softproject.commons.invoice.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class LangUtil {

    String fileName = "invoice-";

    private Map<String, Map<String, String>> langs = new HashMap<String, Map<String, String>>();

    public Map<String, String> loadLang(String lang) {

        if (!langs.containsKey(lang)) {
            Properties pr = new Properties();

            try {
                String messagesFileName = fileName + lang + ".properties";
                final InputStream resource = getClass().getClassLoader().getResourceAsStream(messagesFileName);
                if(resource != null) {
                    pr.load(resource);
                } else {
                    throw new RuntimeException("brak pliku języka " + lang);
                }
                langs.put(lang, new HashMap(pr));
            } catch (IOException ex) {
                throw new RuntimeException("problem z odczytem pliku języka " + lang, ex);
            }
        }
        return langs.get(lang);
    }
}
