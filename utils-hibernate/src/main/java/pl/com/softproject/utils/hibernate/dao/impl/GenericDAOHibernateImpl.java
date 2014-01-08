package pl.com.softproject.utils.hibernate.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import pl.com.softproject.utils.hibernate.dao.FinderExecutor;
import pl.com.softproject.utils.hibernate.dao.GenericDAO;

public class GenericDAOHibernateImpl<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDAO<T, PK>, FinderExecutor<T> {

    private Class<T> type;

    public GenericDAOHibernateImpl(Class<T> type) {
        this.type = type;
    }

    @SuppressWarnings("unchecked")
    public PK create(T o) {
        return (PK) getHibernateTemplate().save(o);
    }

    @SuppressWarnings("unchecked")
    public T load(PK id) {
        return (T) getHibernateTemplate().load(type, id);
    }

    public void persist(T o) {
        getHibernateTemplate().persist(o);
    }

    public void update(T o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    public void saveOrUpdate(T o) {
        getHibernateTemplate().saveOrUpdate(o);
    }

    public void delete(T o) {
        getHibernateTemplate().delete(o);
    }

    public void flush() {
        getHibernateTemplate().flush();

    }

    @SuppressWarnings("unchecked")
    public List<T> searchAll() {
        return (List<T>) getHibernateTemplate().loadAll(type);
    }

    public List<T> searchByExample(final T exampleEntity) {
        return searchByExample(exampleEntity, null);
    }

    public List<T> searchByExample(final T exampleEntity, String sortProperity, boolean ascending) {

        Example example = Example.create(exampleEntity)
                .ignoreCase()
                .excludeZeroes()
                .enableLike(MatchMode.ANYWHERE);

        return executeExampleQuery(example, sortProperity, ascending);
    }

    @Override
    public List<T> searchByExample(final T exampleEntity, List<String> excludeProperty) {

        Example example = Example.create(exampleEntity)
                .ignoreCase()
                .excludeZeroes()
                .enableLike(MatchMode.ANYWHERE);

        if (excludeProperty != null) {
            for (String properity : excludeProperty) {
                example.excludeProperty(properity);
            }
        }

        return executeExampleQuery(example);
    }

    @Override
    public List<T> searchByCriteria(DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<T> searchByHQLQuery(String query, Object... params) {
        return getHibernateTemplate().find(query, params);
    }

    public List searchForObjectListByHQLQuery(String query, Object... params) {
        return getHibernateTemplate().find(query, params);
    }

    @Override
    public Object executeHibernateCallback(HibernateCallback callback) {
        return getHibernateTemplate().execute(callback);
    }

    @SuppressWarnings("unchecked")
    public List<T> searchAllOrdered(final String sortProperity, final boolean ascending) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = getSession()
                        .createCriteria(type);

                if (sortProperity != null && !"".equals(sortProperity)) {
                    if (ascending == true) {
                        criteria.addOrder(Order.asc(sortProperity));
                    } else {
                        criteria.addOrder(Order.desc(sortProperity));
                    }
                }
                return criteria.list();
            }
        });
    }
    
   @SuppressWarnings("unchecked")
    public List<T> searchAllOrdered(final String sortProperity, final boolean ascending, final boolean distinct) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = getSession()
                        .createCriteria(type);

                if(distinct) {
                    criteria.setProjection(Projections.distinct(Projections.property(sortProperity)));
                }
                
                if (sortProperity != null && !"".equals(sortProperity)) {
                    if (ascending == true) {
                        criteria.addOrder(Order.asc(sortProperity));
                    } else {
                        criteria.addOrder(Order.desc(sortProperity));
                    }
                }
                return criteria.list();
            }
        });
    } 

    @SuppressWarnings("unchecked")
    protected List<T> executeExampleQuery(final Example example) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return getSession()
                        .createCriteria(type)
                        .add(example).list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    protected List<T> executeExampleQuery(final Example example, final String sortProperity, final boolean ascending) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Criteria criteria = getSession()
                        .createCriteria(type)
                        .add(example);

                if (sortProperity != null && !"".equals(sortProperity)) {
                    if (ascending == true) {
                        criteria.addOrder(Order.asc(sortProperity));
                    } else {
                        criteria.addOrder(Order.desc(sortProperity));
                    }
                }
                return criteria.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<T> executeFinder(Method method, final Object[] queryArgs) {
        final Query namedQuery = prepareQuery(method, queryArgs);

        /* 
         final Query namedQuery = getSession().getNamedQuery(queryName);
         //String[] namedParameters = namedQuery.getNamedParameters();
         for(int i = 0; i < queryArgs.length; i++) {
         Object arg = queryArgs[i];
         namedQuery.setParameter(i, arg);
         }
         */

        return (List<T>) namedQuery.list();
    }

    @SuppressWarnings("unchecked")
    public Iterator<T> iterateFinder(Method method, Object[] queryArgs) {
        final Query namedQuery = prepareQuery(method, queryArgs);
        return (Iterator<T>) namedQuery.iterate();
    }

    public String queryNameFromMethod(Method finderMethod) {
        return type.getSimpleName() + "." + finderMethod.getName();
    }

    private Query prepareQuery(Method method, Object[] queryArgs) {
        final String queryName = queryNameFromMethod(method);
        final Query namedQuery = getSession().getNamedQuery(queryName);
        String[] namedParameters = namedQuery.getNamedParameters();
        if (namedParameters.length == 0) {
            setPositionalParams(queryArgs, namedQuery);
        } else {
            setNamedParams(namedParameters, queryArgs, namedQuery);
        }
        return namedQuery;
    }

    private void setPositionalParams(Object[] queryArgs, Query namedQuery) {
        // Set parameter. Use custom Hibernate Type if necessary
        if (queryArgs != null) {
            for (int i = 0; i < queryArgs.length; i++) {
                Object arg = queryArgs[i];
                Type argType = null;
                if (argType != null) {
                    namedQuery.setParameter(i, arg, argType);
                } else {
                    namedQuery.setParameter(i, arg);
                }
            }
        }
    }

    private void setNamedParams(String[] namedParameters, Object[] queryArgs,
            Query namedQuery) {
        // Set parameter. Use custom Hibernate Type if necessary
        if (queryArgs != null) {
            for (int i = 0; i < queryArgs.length; i++) {
                Object arg = queryArgs[i];
                //Type argType = getArgumentTypeFactory().getArgumentType(arg);
                Type argType = null;
                if (argType != null) {
                    namedQuery.setParameter(namedParameters[i], arg, argType);
                } else {
                    if (arg instanceof Collection) {
                        namedQuery.setParameterList(namedParameters[i],
                                (Collection) arg);
                    } else {
                        namedQuery.setParameter(namedParameters[i], arg);
                    }
                }
            }
        }
    }
}
