package pl.com.softproject.utils.hibernate.datatypes;

import org.hibernate.usertype.UserType;
import java.sql.Types;
import org.hibernate.HibernateException;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.Serializable;

/**
 * 
 * @author Adrian Lapierre
 */
public class UtcDateTimeType implements UserType  {

    /**
     * SQL type.
     */
    private static final int[] SQL_TYPES = { Types.BIGINT };

    /**
     * Make a copy of the date.
     * @see UserType#deepCopy(java.lang.Object)
     */
    public Object deepCopy(Object obj) throws HibernateException {
        return (obj == null) ? null : new Date(((Date)obj).getTime());
    }

    /**
     * Compare via {@link Object#equals(java.lang.Object)}.
     * @see UserType#equals(java.lang.Object, java.lang.Object)
     */
    public boolean equals(Object x, Object y) {
        return (x == null) ? (y == null) : x.equals(y);
    }

    /**
     * Dates are mutable.
     * @see net.sf.hibernate.UserType#isMutable()
     */
    public boolean isMutable() {
        return true;
    }

    /**
     * Return an instance of the date or null if no value is specified.
     * @see net.sf.hibernate.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
     */
    public Object nullSafeGet(ResultSet rs, String[] columns, Object owner)
            throws HibernateException, SQLException {

        long value = rs.getLong(columns[0]);
        Date date;
        if(rs.wasNull()) {
            date = null;
        } else {
            date = new Date(value);
        }
        return date;

    }

    /**
     * Set an instance of the date into the database field.
     * @see net.sf.hibernate.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
     */
    public void nullSafeSet(PreparedStatement statement, Object value, int index)
            throws HibernateException, SQLException {

        if(value == null) {
            statement.setNull(index, Types.BIGINT);
        } else {
            Date date = (Date)value;
            statement.setLong(index, date.getTime());
        }
    }

    /**
     * Return the {@link Date} class.
     * @see net.sf.hibernate.UserType#returnedClass()
     */
    public Class returnedClass() {
        return Date.class;
    }

    /**
     * Return the supported SQL types.
     * @see net.sf.hibernate.UserType#sqlTypes()
     */
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public int hashCode(Object object) throws HibernateException {
        return object.hashCode();
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }


}
