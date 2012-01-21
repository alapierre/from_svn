/*
 * Copyright 2011-12-18 the original author or authors.
 */
package pl.com.softproject.utils.commons.dictionary.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import pl.com.softproject.commons_administrative_division.AdministrativeDivision;
import pl.com.softproject.commons_administrative_division.Community;
import pl.com.softproject.commons_administrative_division.CommunityType;
import pl.com.softproject.commons_administrative_division.District;
import pl.com.softproject.commons_administrative_division.Province;
import pl.com.softproject.utils.commons.dictionary.Constant;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class AdministrativeDivisionDAOJdbcImpl implements AdministrativeDivisionDAO {

    private static final Logger logger = Logger.getLogger(AdministrativeDivisionDAOJdbcImpl.class.getCanonicalName());
    private DataSource dataSource;
    private SimpleJdbcTemplate jdbcTemplate;
    protected static final String SQL_Community_by_district_id = "select community.name as name, community.gus_number as teryc, community_type_id "
            + "from community "
            + "where district_id = ?";
    protected static final String SQL_District_by_province_id = "select * "
            + "from district "
            + "where province_id = ?";
    protected static final String SQL_Province = "select * "
            + "from province ";

    @Override
    public AdministrativeDivision loadAll(Date lastSynchronizationTime) {

        AdministrativeDivision result = new AdministrativeDivision();

        GregorianCalendar c = new GregorianCalendar();
        c.setTime(getLastModification());
        try {
            XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            result.setLastUpdated(date);
        } catch (DatatypeConfigurationException ex) {
            logger.log(Level.WARNING, ex.getMessage(), ex);
        }

        for (ProvinceExt province : loadProvince()) {
            //System.out.println("province: " + province.getName());
            for (DistrictExt district : loadDistrictByProvinceId(province.getId())) {
                //System.out.println("district: " + district.getName());
                for (Community community : loadCommunitysByDistrictId(district.getId())) {
                    //System.out.println("community: " + community.getName());
                    district.getCommunity().add(community);
                }
                province.getDistrict().add(district);
            }
            result.getProvince().add(province);
        }
        return result;
    }

    public List<Community> loadCommunitysByDistrictId(int distridcId) {
        //RowMapper<Community> rowMapper = new BeanPropertyRowMapper<Community>(Community.class);
        return jdbcTemplate.query(SQL_Community_by_district_id, new CommunityRowMapper(), distridcId);
    }

    public List<DistrictExt> loadDistrictByProvinceId(int provinceId) {
        RowMapper<DistrictExt> rowMapper = new BeanPropertyRowMapper<DistrictExt>(DistrictExt.class);
        return jdbcTemplate.query(SQL_District_by_province_id, rowMapper, provinceId);
    }

    public List<ProvinceExt> loadProvince() {
        RowMapper<ProvinceExt> rowMapper = new BeanPropertyRowMapper<ProvinceExt>(ProvinceExt.class);
        return jdbcTemplate.query(SQL_Province, rowMapper);
    }

    @Override
    public Date getLastModification() {

        try {
            String res = jdbcTemplate.queryForObject("select value from system_config where key = 'community_lastupdate'", String.class);

            if (res == null) {
                return Constant.defaultModifivcationDate;
            }

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(res);

            return date;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return Constant.defaultModifivcationDate;
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new SimpleJdbcTemplate(this.dataSource);
    }

    protected static class CommunityRowMapper implements RowMapper<Community> {

        @Override
        public Community mapRow(ResultSet rs, int rowNum) throws SQLException {
            Community result = new Community();

            result.setName(rs.getString("name"));
            result.setTeryc(rs.getString("teryc"));

            switch (rs.getInt("community_type_id")) {
                case 1:
                    result.setType(CommunityType.WIEJSKA);
                    break;
                case 2:
                    result.setType(CommunityType.MIEJSKA);
                    break;
                case 3:
                    result.setType(CommunityType.MIEJSKO_WIEJSKA);
                    break;
                case 4:
                    result.setType(CommunityType.POWIAT_GRODZKI);
                    break;
            }

            return result;
        }
    }

    public static class CommunityExt extends Community {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class DistrictExt extends District {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class ProvinceExt extends Province {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
