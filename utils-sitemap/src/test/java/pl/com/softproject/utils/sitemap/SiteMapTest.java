/*
 * Copyright 2011-08-31 the original author or authors.
 */

package pl.com.softproject.utils.sitemap;

import java.util.Date;
import org.junit.Test;
import org.sitemaps.schemas.sitemap._0.TChangeFreq;
import org.sitemaps.schemas.sitemap._0.TUrl;
import org.sitemaps.schemas.sitemap._0.Urlset;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class SiteMapTest {
    
    @Test
    public void testUtil() {
        
        SitemapUtil sitemap = new SitemapUtil();
        
        sitemap.addUrl("www.example.com", new Date(), TChangeFreq.DAILY)
                .addUrl("sample.example.com", new Date(), TChangeFreq.HOURLY);
        
        System.out.println(sitemap);
        
    }
    
    @Test
    public void testCreate() {
        
        Urlset set = new Urlset();
        TUrl url = new TUrl();
        url.setLoc("www.sample.com");
        
        set.getUrl().add(url);
        
        SitemapSerializer serializer = new SitemapSerializer();
        String str = serializer.toString(set, true);
        
        System.out.println(str);
    }
    
}
