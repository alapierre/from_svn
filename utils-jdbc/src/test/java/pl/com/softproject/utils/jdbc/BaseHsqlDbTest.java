/*
 * Copyright 2010 the original author or authors.
 */

package pl.com.softproject.utils.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author Adrian Lapierre <alapierre@softproject.com.pl>
 */
public abstract class BaseHsqlDbTest {

    protected static Connection conn;
    protected static String url = "jdbc:hsqldb:file:src/test/resources/hsql/database";

    @BeforeClass
    public static void setUp() throws Exception {

        System.out.println("setUp()");

        Class.forName("org.hsqldb.jdbcDriver");
        conn = DriverManager.getConnection(
                url,
                "SA",
                "");
    }

    @AfterClass
    public static void down() throws Exception {

        System.out.println("down()");
        if(conn != null) {
            conn.createStatement().execute("SHUTDOWN");
            conn.close();
        } else System.err.println("Connection is close");

    }

}
