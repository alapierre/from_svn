/*
 * Copyright 2011-08-31 the original author or authors.
 */

package pl.com.softproject.utils.sitemap;

import org.sitemaps.schemas.sitemap._0.Urlset;
import pl.com.softproject.utils.xml.BaseXMLSerializer;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class SitemapSerializer extends BaseXMLSerializer<Urlset>{

    public SitemapSerializer() {
        super("org.sitemaps.schemas.sitemap._0", "sitemap.xsd", "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");
    }
    
}
