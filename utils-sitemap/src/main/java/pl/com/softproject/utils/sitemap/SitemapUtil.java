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
    private SitemapSerializer serializer = new SitemapSerializer();
    private String baseURL;
    
    
    public SitemapUtil() {
        
    }

    public SitemapUtil(String baseURL) {
        this.baseURL = (baseURL != null && !baseURL.endsWith("/")) ? baseURL + "/" : baseURL;
    }
    
    
    public SitemapUtil addUrl(String url) {
        TUrl turl = new TUrl();
        createLocElement(turl, url); 
        set.getUrl().add(turl);
        return this;
    }
    
    public SitemapUtil addUrl(String url, Date lastmod) {
        TUrl turl = new TUrl();
        createLocElement(turl, url); 
        if(lastmod != null) turl.setLastmod(DataTools.formatDateWithTime(lastmod));
        set.getUrl().add(turl);
        return this;
    }
    
    public SitemapUtil addUrl(String url, String lastmod) {
        TUrl turl = new TUrl();
        createLocElement(turl, url); 
        turl.setLastmod(lastmod);
        set.getUrl().add(turl);
        return this;
    }
    
    public SitemapUtil addUrl(String url, Date lastmod, TChangeFreq freq) {
        TUrl turl = new TUrl();
        createLocElement(turl, url); 
        if(lastmod != null) turl.setLastmod(DataTools.formatDateWithTime(lastmod));
        turl.setChangefreq(freq);
        set.getUrl().add(turl);
        return this;
    }
    
    public SitemapUtil addUrl(String url, String lastmod, TChangeFreq freq) {
        TUrl turl = new TUrl();
        createLocElement(turl, url); 
        turl.setLastmod(lastmod);
        turl.setChangefreq(freq);
        set.getUrl().add(turl);
        return this;
    }

    private void createLocElement(TUrl turl, String url) {
        turl.setLoc(baseURL != null ? baseURL + url : url);
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

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = (baseURL != null && !baseURL.endsWith("/")) ? baseURL + "/" : baseURL;
    }
    
}
