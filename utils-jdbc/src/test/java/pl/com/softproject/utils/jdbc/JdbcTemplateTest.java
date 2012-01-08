/*
 * Copyright 2010 the original author or authors.
 */
package pl.com.softproject.utils.jdbc;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public class JdbcTemplateTest extends BaseHsqlDbTest {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Test
    public void testQueryForObject() {

        int res = jdbcTemplate.queryForObject(conn, "select id from test where id = 1", Integer.class);
        System.out.println("res: " + res);

        assertEquals(1, 1);
    }

    @Test
    public void testQueryForInt() {

        int res = jdbcTemplate.queryForInt(conn, "select id from test where id = 1");
        System.out.println("res: " + res);

        assertEquals(1, 1);
    }

    @Test
    public void testQueryForString() {

        String res = jdbcTemplate.queryForString(conn, "select txt from test where id = 1");
        System.out.println("res: " + res);

        assertEquals("ala ma kota", res);
    }
}
