/*
 * Copyright 2011-08-31 the original author or authors.
 */

package pl.com.softproject.utils.sitemap;

import java.io.File;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import org.sitemaps.schemas.sitemap._0.TChangeFreq;
import org.sitemaps.schemas.sitemap._0.TUrl;
import org.sitemaps.schemas.sitemap._0.Urlset;
import pl.com.softproject.utils.xml.DataTools;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class SitemapUtil {

    private Urlset set = new Urlset();
    SitemapSerializer serializer = new SitemapSerializer();
    
    public SitemapUtil() {
        
    }
    
    public SitemapUtil addUrl(String url) {
        TUrl turl = new TUrl();
        turl.setLoc(url); 
        set.getUrl().add(turl);
        return this;
    }
    
    public SitemapUtil addUrl(String url, Date lastmod) {
        TUrl turl = new TUrl();
        turl.setLoc(url); 
        turl.setLastmod(DataTools.formatDate(lastmod));
        set.getUrl().add(turl);
        return this;
    }
    
    public SitemapUtil addUrl(String url, String lastmod) {
        TUrl turl = new TUrl();
        turl.setLoc(url); 
        turl.setLastmod(lastmod);
        set.getUrl().add(turl);
        return this;
    }
    
    public SitemapUtil addUrl(String url, Date lastmod, TChangeFreq freq) {
        TUrl turl = new TUrl();
        turl.setLoc(url); 
        turl.setLastmod(DataTools.formatDate(lastmod));
        turl.setChangefreq(freq);
        set.getUrl().add(turl);
        return this;
    }
    
    public SitemapUtil addUrl(String url, String lastmod, TChangeFreq freq) {
        TUrl turl = new TUrl();
        turl.setLoc(url); 
        turl.setLastmod(lastmod);
        turl.setChangefreq(freq);
        set.getUrl().add(turl);
        return this;
    }
    
    public Urlset getUrlset() {
        return set;
    }

    @Override
    public String toString() {
        String str = serializer.toString(set, true);
        return str;
    }
    
    public void save(File file) {
        serializer.toFile(set, file.getAbsolutePath());
    }
    
    public void save(OutputStream stream) {
        serializer.toStream(set, stream);
    }
    
}
