/*
 * Copyright 2011-12-13 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import pl.com.softproject.commons_administrative_division.AdministrativeDivision;
import pl.com.softproject.commons_administrative_division.Community;
import pl.com.softproject.commons_administrative_division.District;
import pl.com.softproject.commons_administrative_division.Province;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class AdministrativeDivisionServiceImpl {
    
    private AdministrativeDivision administrativeDivision;
    Map<String, List<District>> districts = new LinkedHashMap<String, List<District>>();
    Map<String, List<Community>> communities = new LinkedHashMap<String, List<Community>>();
    boolean initialized;
    
    
    public AdministrativeDivisionServiceImpl(InputStream is) {
        XMLAdministrativeDivisionSerializer serializer = new XMLAdministrativeDivisionSerializer();        
        administrativeDivision = serializer.fromStream(is);
    }

    public AdministrativeDivisionServiceImpl(File file) {
        XMLAdministrativeDivisionSerializer serializer = new XMLAdministrativeDivisionSerializer();        
        administrativeDivision = serializer.fromFile(file);
    }
    
    public AdministrativeDivisionServiceImpl(AdministrativeDivision administrativeDivision) {
        this.administrativeDivision = administrativeDivision;
    }
    
    public List<Province> loadProvince() {
        return administrativeDivision.getProvince();
    }
    
    public List<District> loadDistrictsForProvinceName(String provinceName) {
        initIfNeeded();
        return districts.get(provinceName);
    }
    
    public List<Community> loadCommunitysForDistrictName(String districtName) {
        initIfNeeded();
        return communities.get(districtName);
    }
    
    protected void initIfNeeded() {        
        if(!initialized) {
            for(Province province : loadProvince()) {
                districts.put(province.getName(), province.getDistrict());
                initCommunities(province.getDistrict());
            }
        }        
        initialized = true;
    }
    
    protected void initCommunities(List<District> districts) {
        for(District district : districts) {
            communities.put(district.getName(), district.getCommunity());
        }
    }
    
    public Date getLastModifiaction() {
        
        if(administrativeDivision.getLastUpdated()== null)
            return Constant.defaultModifivcationDate;
        
        GregorianCalendar calendar = administrativeDivision.getLastUpdated().toGregorianCalendar();
        return calendar.getTime();
    }
    
}
