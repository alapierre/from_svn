/*
 * Copyright 2012-04-17 the original author or authors.
 */
package pl.com.softproject.commons.invoice;


import pl.com.softproject.commons.model.invoice.Invoice;
import pl.com.softproject.utils.xml.BaseXMLSerializer;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class InvoiceModelSerializer extends BaseXMLSerializer<Invoice> {

    public InvoiceModelSerializer() {
        super("pl.com.softproject.commons.model.invoice", 
                "invoice.xsd", 
                "http://www.softproject.com.pl/commons/model/invoice http://schema.softproject.com.pl/commons/invoice.xsd");
    }
    
}
