/*
 * Copyright 2012-07-17 the original author or authors.
 */
package pl.com.softproject.utils.pivot;

import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class PivotTableModelImpl implements Iterable<Map<String, Object>>, PivotTableModel {
    
    private Map<String, Map<String, Object>> map = new LinkedHashMap<String, Map<String, Object>>();
    
    @Override
    public void add(String rowKey, String columnKey, Object value) {
        Map<String, Object> row = map.get(rowKey);
        if(row == null) {
            row = new HashMap<String, Object>();
            row.put(columnKey, value);
            map.put(rowKey, row);
        } else {
            row.put(columnKey, value);
        }
    }
    
    @Override
    public Iterator  iterator() {
        return new RowIterator(this);
    }
    
    public Object get(String rowKey, String columnKey) {
        Map<String, Object> tmp = map.get(rowKey);
        return tmp != null ? tmp.get(columnKey) : null;
    }
    
    @Override
    public List<String> getRowNames() {
        
        List<String> rows = new ArrayList<String>();
        
        for(Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            rows.add(entry.getKey());
        }
        return rows;
    }
    
    
    @Override
    public Set<String> getColumnNames() {
        
        Set<String> columns = new HashSet<String>();
        
        for(Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            columns.addAll(entry.getValue().keySet());
        }
        return columns;
    }
    
    public void test() {
        System.out.println("map: " + map);
        
        for(Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
//            for(Map.Entry<String, Object> t : entry.getValue().entrySet()) {
//                System.out.println("");
//            }
        }
    }
    
    public static class RowIterator implements Iterator<Map<String, Object>> {
        
        private final Set<Entry<String, Map<String, Object>>> entrys;
        //private final Iterator<Map<String, Object>> it;
        private PivotTableModelImpl impl;
        private Entry<String, Map<String, Object>> currentEntry;
        private final Iterator<Entry<String, Map<String, Object>>> it;
        

        RowIterator(PivotTableModelImpl impl) {
            this.impl = impl;
            //it = impl.map.values().iterator();
            entrys = impl.map.entrySet();
            it = entrys.iterator();
        }
        
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Map<String, Object> next() {
            currentEntry = it.next();
            return currentEntry.getValue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public String rowKey() {
            return currentEntry.getKey();
        }
    }
    
}
