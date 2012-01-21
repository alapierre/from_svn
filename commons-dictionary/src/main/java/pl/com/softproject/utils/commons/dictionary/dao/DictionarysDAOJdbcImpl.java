/*
 * Copyright 2011-12-09 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import pl.com.softproject.commons_dictionary.DictionaryType;
import pl.com.softproject.commons_dictionary.Dictionarys;
import pl.com.softproject.commons_dictionary.EntryType;
import pl.com.softproject.utils.commons.dictionary.Constant;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class DictionarysDAOJdbcImpl implements DictionarysDAO {

    private DataSource dataSource;
    private SimpleJdbcTemplate jdbcTemplate;
    private static final Logger logger = Logger.getLogger(DictionarysDAOJdbcImpl.class.getCanonicalName());

    @Override
    public Dictionarys loadAll(Date lastSynchronizationTime) {

        List<Dictionary> dictionaryList = loadDictionaryNames();
        Dictionarys result = new Dictionarys();

        for (Dictionary dictionary : dictionaryList) {
            RowMapper<EntryType> rowMapper = new BeanPropertyRowMapper<EntryType>(EntryType.class);
            List<EntryType> entryList = jdbcTemplate.query("select * from dictionary_entry where dictionary_id = ?", rowMapper, dictionary.id);

            for (EntryType entry : entryList) {
                dictionary.getEntry().add(entry);
            }

            result.getDictionary().add(dictionary);
        }

        return result;
    }

    protected List<Dictionary> loadDictionaryNames() {
        List<Dictionary> list = jdbcTemplate.query("select * from dictionary", new DictionaryRowMapper());
        return list;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public List<EntryType> loadEntrysByParentKey(String dictionaryKey, String parentKey) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntryType> loadRootEntrys(String dictionaryKey) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getLastModification() {

        try {
            java.sql.Timestamp res = jdbcTemplate.queryForObject("select last_updated from dictionary where last_updated is not null order by last_updated desc limit 1", java.sql.Timestamp.class);

            if (res == null) {
                return Constant.defaultModifivcationDate;
            }

            return new Date(res.getTime());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return Constant.defaultModifivcationDate;
        }
    }

    protected static class DictionaryRowMapper implements RowMapper<Dictionary> {

        @Override
        public Dictionary mapRow(ResultSet rs, int rowNum) throws SQLException {
            Dictionary dictionary = new Dictionary();
            dictionary.setName(rs.getString("name"));
            dictionary.setId(rs.getInt("id"));
            //
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(rs.getDate("last_updated"));
            try {
                XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                dictionary.setLastUpdated(date2);
            } catch (DatatypeConfigurationException ex) {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
            return dictionary;
        }
    }

    protected static class Dictionary extends DictionaryType {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Dictionary{" + "id=" + id + " name=" + name + '}';
        }
    }
}
