package pl.com.softproject.utils.hibernate;

import java.util.*;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.logging.Log;
import org.hibernate.Hibernate;
import org.apache.commons.beanutils.PropertyUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import org.hibernate.Session;

/**
 * 
 * @author Adrian Lapierre
 */
public class ReflectionDeepInitializedCopy {

    private final static Log log = LogFactory.getLog(ReflectionDeepInitializedCopy.class);

    private Set stack = new LinkedHashSet();
    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    protected boolean isHibernateObject(Object obj) {
        if (session == null)
            return false;
        else
            return session.contains(obj);
    }


    public Object deepCopy(Object source) {

        Object targetObject;
        if(isHibernateObject(source)) {
            try {
                targetObject = source.getClass().getSuperclass().newInstance();

                if(targetObject.getClass().equals(Object.class)) {
                    targetObject = source;
                }
            } catch (IllegalAccessException ex) {
                log.warn("IllegalAccessException in object " + source.getClass() + " writing null");
                return null;
            } catch (InstantiationException ex) {
                log.warn("InstantiationException in object " + source.getClass() + " writing null");
                return null;
            }
        } else
            targetObject = source;


        if(log.isDebugEnabled()) {
            log.debug("object " + source.getClass());
            log.debug("parent object " + source.getClass().getSuperclass());
        }

        PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(targetObject);
        //PropertyDescriptor[] properties2 = PropertyUtils.getPropertyDescriptors(source);

        for (int i = 0; i < properties.length; i++) {
            String propertyName = properties[i].getName();
            Method readMethod = properties[i].getReadMethod();
            Class propClass =  properties[i].getPropertyType();

            log.debug(propertyName + " - " + readMethod);

            Object tmp = null;
            try {
                tmp = PropertyUtils.getProperty(source, propertyName);
            } catch (NoSuchMethodException ex1) {
                log.warn("no such method " + propertyName + " in " + source.getClass() + ", writing null");
            } catch (InvocationTargetException ex1) {
                log.warn("error invocation getter method " + propertyName + " in " + source.getClass() + " writing null");
            } catch (IllegalAccessException ex1) {
                log.warn("illegal access invocation getter method " + propertyName + " in " + source.getClass() + " writing null");
            }

            if (tmp != null) {
                if (log.isDebugEnabled())
                    log.debug(tmp.getClass());
                if(tmp instanceof Collection)
                    tmp = initializeCollection((Collection)tmp);
                if(isHibernateObject(tmp))
                    Hibernate.initialize(tmp);
            }

            try {
                if(!propertyName.equals("class"))
                    PropertyUtils.setProperty(targetObject, propertyName, tmp);
            } catch (NoSuchMethodException ex2) {
                log.warn("no such method for poperity " + propertyName + " in " + source.getClass());
            } catch (InvocationTargetException ex2) {
                log.warn("error invocation setter method for poperity " + propertyName + " in " + source.getClass());
            } catch (IllegalAccessException ex2) {
                log.warn("illegal access invocation setter method " + propertyName + " in " + source.getClass());
            }
        }
        return targetObject;
    }

    public Collection initializeCollection(Collection col) {
        Hibernate.initialize(col);
//        Iterator i = col.iterator();
//        while(i.hasNext()) {
//            Object o = i.next();
//            if(isHibernateObject(o)) {
//                o = deepCopy(o);
//            }
//        }
        return col;
    }


    public static void main(String[] args) {

        //ApplicationContext ctx = SpringContext.getContext();
        //HibernateTemplate tpl = (HibernateTemplate) ctx.getBean("hibernateTemplate");

//        Object obj = tpl.execute(new HibernateCallback() {
//            public Object doInHibernate(Session session) throws SQLException, HibernateException {
//                Tester t = (Tester) session.load(Tester.class, 101);
//                System.out.println(t);
//                ReflectionDeepInitializedCopy rdic = new ReflectionDeepInitializedCopy();
//                rdic.setSession(session);
//                t = (Tester) rdic.deepCopy(t);
//
//                return t;
//            }
//        });

        /*Object obj = tpl.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws SQLException, HibernateException {
                Project p = (Project) session.load(Project.class, 37);
                System.out.println(p);
                ReflectionDeepInitializedCopy rdic = new ReflectionDeepInitializedCopy();
                rdic.setSession(session);
                p = (Project) rdic.deepCopy(p);
                System.out.println("An object: " + ReflectionToStringBuilder.toString(p, ToStringStyle.MULTI_LINE_STYLE) );
                return p;
            }
        });*/



    }




}
