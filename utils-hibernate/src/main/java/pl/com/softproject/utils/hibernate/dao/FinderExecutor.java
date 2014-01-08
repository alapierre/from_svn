package pl.com.softproject.utils.hibernate.dao;

import java.lang.reflect.Method;
import java.util.*;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Find Executor Interface
 *
 * @author Adrian Lapierre
 * @param <T>
 */
public interface FinderExecutor<T>
{
    /**
     * Execute a finder method with the appropriate arguments
     */
    List<T> executeFinder(Method method, Object[] queryArgs);

    Iterator<T> iterateFinder(Method method, Object[] queryArgs);

//    ScrollableResults scrollFinder(Method method, Object[] queryArgs);

    List<T> searchAllOrdered(final String sortProperity, final boolean ascending, final boolean distinct);

    
}
