/*
 * Copyright 2011-12-14 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.util;

import java.util.List;
import pl.com.softproject.commons_administrative_division.Community;
import pl.com.softproject.commons_administrative_division.District;
import pl.com.softproject.commons_administrative_division.Province;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class ToStringBuilder {
    
    public static String toString(Community community) {
        return community.getName() + " " + community.getType().value() + " " + community.getTeryc();
    }
    
    public static String toString(District district) {
        return (district.getName());
    }
    
    public static String toString(Province province) {
        return (province.getName());
    }
    
    public static void printCommunity(List<Community> communitys) {
        for(Community community : communitys) {
            System.out.println(toString(community));
        }
    }

    public static void printDistrict(List<District> districts) {
        for(District district : districts) {
            System.out.println(district.getName());
        }
    }
    
    public static void printProvince(List<Province> provinces) {
        for(Province province : provinces) {
            System.out.println(province.getName());
        }
    }
    
}
