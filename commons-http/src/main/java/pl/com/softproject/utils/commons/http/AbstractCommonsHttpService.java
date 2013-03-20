/*
 * Copyright 2013-03-20 the original author or authors.
 */
package pl.com.softproject.utils.commons.http;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;



/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class AbstractCommonsHttpService implements InitializingBean, DisposableBean {
    
    protected Logger logger = Logger.getLogger(getClass());
    protected String serviceURL;
    protected String serviceUserName;
    protected String serviceUserPassword;
    private DefaultHttpClient httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
    
    public AbstractCommonsHttpService() {
        //logger.debug(getClass());
        
    }

    public AbstractCommonsHttpService(String url) {
        this.serviceURL = url;        
    }
    
    protected String invokeRemoteServiceAndReturnResultAsString(String serviceNameAndParam) throws MalformedURLException, URISyntaxException, IOException {
        
            URL source = new URL(serviceURL + serviceNameAndParam);
            HttpEntity entity = getHttpEntity(source);
            if (entity != null) {
                return EntityUtils.toString(entity);
            } else {
                throw new IOException("Nie otrzymano odpowiedzi z serwisu");
            }
         
    }

    protected HttpEntity getHttpEntity(URL source) throws URISyntaxException, IOException {

        HttpGet httpget = new HttpGet(source.toURI());
        if (logger.isDebugEnabled()) {
            logger.debug("invoking uri " + httpget.getURI());
        }
        HttpResponse response = httpclient.execute(httpget);
        return response.getEntity();
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        if (serviceUserName != null && !serviceUserName.isEmpty()) {
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(serviceUserName, serviceUserPassword);
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                    creds);
        }
        
    }

    @Override
    public void destroy() throws Exception {
        httpclient.getConnectionManager().shutdown();
    }

    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public String getServiceUserName() {
        return serviceUserName;
    }

    public void setServiceUserName(String serviceUserName) {
        this.serviceUserName = serviceUserName;
    }

    public String getServiceUserPassword() {
        return serviceUserPassword;
    }

    public void setServiceUserPassword(String serviceUserPassword) {
        this.serviceUserPassword = serviceUserPassword;
    }
    
}
