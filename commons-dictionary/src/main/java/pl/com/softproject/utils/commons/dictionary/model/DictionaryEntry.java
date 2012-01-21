/*
 * Copyright 2011-12-13 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.model;

import pl.com.softproject.commons_dictionary.EntryType;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class DictionaryEntry extends EntryType {

    public DictionaryEntry(EntryType base) {
        super();
        if(base!=null) {
            setKey(base.getKey());
            setKeySubstitute(base.getKeySubstitute());
            setParentKey(base.getParentKey());
            setValue(base.getValue());
        }
    }

    @Override
    public String getKeySubstitute() {
        if(getKeySubstitute() == null || getKeySubstitute().isEmpty())
            return super.getKeySubstitute();
        return super.key;
    }

    @Override
    public String toString() {
        return super.value;
    }
    
}
