/*
 * Copyright 2011-12-16 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import pl.com.softproject.commons_dictionary.EntryType;
import pl.com.softproject.utils.commons.dictionary.dao.DictionarysDAO;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class UtilUtil {

    public static void print(List<EntryType> entrys) {
        
        assert entrys != null : "przekazano nulową listę do druku";
        
        for(EntryType entry : entrys) {
            System.out.println("k:" +entry.getKey() + " v:" + entry.getValue() + " ref:" + entry.getParentKey());
        }
    }
    
    public static void printR(List<EntryType> entrys, DictionarysDAO dao, String dictionaryName) {
        printR(entrys, dao, dictionaryName, 0);
    }
    
    public static void printR(List<EntryType> entrys, DictionarysDAO dao, String dictionaryName, int level) {
        //System.out.println("level " + level);
        if(entrys == null) return;
        
        for(EntryType entry : entrys) {
            for(int i =0; i<level; i++)
                System.out.print("    ");
            System.out.println("k:" +entry.getKey() + " v:" + entry.getValue() + " ref:" + entry.getParentKey());
            printR(dao.loadEntrysByParentKey(dictionaryName, entry.getKey()), dao, dictionaryName, ++level);
            --level;
        }
    }
    
    public static void print(Map<String, List<EntryType>> rootEntrys, Map<String, Map<String, List<EntryType>>> entrys) {

        for (Entry<String, List<EntryType>> entry : rootEntrys.entrySet()) {
            System.out.println(entry.getKey());
            for (EntryType tmp : entry.getValue()) {
                System.out.println("   " + tmp.getKey() + " -> " + tmp.getValue());
                print(entry.getKey(), tmp.getKey(), entrys);
            }
        }
    }

    public static void print(String dictionary, String parent, Map<String, Map<String, List<EntryType>>> entrys) {
        if (entrys != null && entrys.get(dictionary) != null) {
            for (Entry<String, List<EntryType>> tmp : entrys.get(dictionary).entrySet()) {
                if (tmp.getKey().equals(parent)) {
                    for (EntryType entry : tmp.getValue()) {
                        System.out.println("      " + entry.getKey() + " -> " + entry.getValue());
                    }
                }
            }
        }

    }
    
    public static void compareValues(String msg, List<EntryType> result, String... values) {
        for(String value : values) {
            if(!findValue(result, value)) {
                throw new AssertionError(msg + " " + value);
            }
        }
    }
    
    public static void compareKeys(String msg, List<EntryType> result, String... values) {
        for(String value : values) {
            if(!findKey(result, value)) {
                throw new AssertionError(msg + " " + value);
            }
        }
        
        Set valuesSet = new HashSet(Arrays.asList(values));
        
        for(EntryType entry : result) {
            if(!valuesSet.contains(entry.getKey())) {
                throw new AssertionError("result zawiera nadmiarowy klucz " + entry.getKey());
            }
        }
    }
    
     protected static boolean findValue(List<EntryType> result, String value) {
        for(EntryType entry : result) {
            if(entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }
    
    protected static boolean findKey(List<EntryType> result, String value) {
        for(EntryType entry : result) {
            if(entry.getKey().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
