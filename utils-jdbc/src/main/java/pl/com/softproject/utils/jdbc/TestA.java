/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author Adrian Lapierre
 */
public class TestA {

    public static void main(String[] agrs) {
        
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://solaris.pl.chartsearch.net:5432/adrian_test", "pgsql", "1qasw2");

        } catch (SQLException ex) {
            Logger.getLogger(TestA.class.getName()).log(Level.SEVERE, null, ex);
        }

        JdbcTemplate tpl = new JdbcTemplate();
        List<String> list = tpl.queryForOneColumnList(conn, "select ip from moja_tabelka", String.class);

        System.out.println(list);

        
        

    }

}
