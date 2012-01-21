/*
 * Copyright 2011-12-18 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.dao;

import com.csvreader.CsvReader;
import java.nio.charset.Charset;
import org.junit.Test;
import pl.com.softproject.commons_administrative_division.AdministrativeDivision;
import pl.com.softproject.commons_administrative_division.Community;
import pl.com.softproject.commons_administrative_division.CommunityType;
import pl.com.softproject.commons_administrative_division.District;
import pl.com.softproject.commons_administrative_division.Province;
import pl.com.softproject.utils.commons.dictionary.XMLAdministrativeDivisionSerializer;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class AdministrativeDivisionDAOCsvImpl {

    @Test
    public void loadAll() throws Exception {

        AdministrativeDivision result = new AdministrativeDivision();

        CsvReader gminiy = new CsvReader("D:/realizacje/SoftProject/UKE/materiały/gminy-utf.csv", ';', Charset.forName("UTF-8"));
        
        
        
        gminiy.readHeaders();

        String currentWoj = "";
        String currentPowiat = "";

        Province province = null;
        District district = null;
        
        while (gminiy.readRecord()) {
            
            if (!currentWoj.equals(gminiy.get("Wojewodztwo"))) {
                currentWoj = gminiy.get("Wojewodztwo");
                province = new Province();
                province.setName(currentWoj);
                result.getProvince().add(province);
            }

            if (!currentPowiat.equals(gminiy.get("Powiat"))) {
                currentPowiat = gminiy.get("Powiat");
                district = new District();
                district.setName(cleanPowiat(currentPowiat));
                province.getDistrict().add(district);
            }
            
            Community community = new Community();
            community.setName(gminiy.get("Gmina"));
            community.setTeryc(gminiy.get("Nr_GUS"));
            community.setType(cleanTypGminy(gminiy.get("TypGminy")));
            
            district.getCommunity().add(community);
            
            //System.out.println(currentWoj + " " + currentPowiat + " " + gminiy.get(1));

        }

        gminiy.close();
        
        XMLAdministrativeDivisionSerializer serializer = new XMLAdministrativeDivisionSerializer();
        //System.out.println(serializer.toString(result));
        serializer.toFile(result, "d:/gminy.xml");
        
    }
    
    protected String cleanPowiat(String powiat) {
        return powiat.replace("Powiat ", "");
    }
    
    protected CommunityType cleanTypGminy(String typ) {
        String cleaned = typ.replace("gmina ", "");        
        if(cleaned.equals("powiat grodzki")) cleaned = "powiat-grodzki";
        return CommunityType.fromValue(cleaned);
    }
    
}
