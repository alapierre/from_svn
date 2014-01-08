package pl.com.softproject.utils.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Genieric DAO interface
 *
 * @author Adrian Lapierre 
 * @param <T> - domain object type
 * @param <PK> - primary key type
 */
public interface GenericDAO <T, PK extends Serializable> {

    /** Persist the newInstance object into database */
    PK create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    T load(PK id);

    /** 
     * Save changes made to a persistent object.  
     */
    void update(T transientObject);
    
    /** 
     * Save changes made to a persistent object.  
     */
    public void saveOrUpdate(T o);

    /** Remove an object from persistent storage in the database */
    void delete(T persistentObject);
    
    void flush();
    
    /**
     * returns all entity
     * @return all entity list
     */
    public List<T> searchAll();

    /**
     *
     * @param sortProperity
     * @param ascending
     * @return
     */
    public List<T> searchAllOrdered(String sortProperity, boolean ascending);
    
    public List<T> searchAllOrdered(final String sortProperity, final boolean ascending, final boolean distinct);
    
    /**
     * Wyszukuje encje wed�ug podanego przyk�adu. Wszystkie w�a�ciwo�ci, r�ne od null i 0 
     * b�d� brane pod uwag�. Do por�wnywania string�w u�ywany jest operator like, wielko�� znak�w
     * jest ignorowana.
     * 
     * @param exampleEntity przyk�adowa encja
     * @return lista encji
     */
    public List<T> searchByExample(final T exampleEntity);
    
    /**
     * Wyszukuje encje wed�ug podanego przyk�adu i zwraca posortowan� list�
     * @param exampleEntity przyk�adowa encja
     * @param sortProperity nazwa w�a�ciwo�ci po kt�ej ma si� sotrowa�
     * @param ascending czy rosn�co czy malej�co
     * @return
     */
    public List<T> searchByExample(final T exampleEntity, String sortProperity, boolean ascending);
    
    /**
     * Wyszukuje encje wed�ug podanego przyk�adu. W�a�ciwo�ci o nazwach podanych w  excludeProperty
     * nie b�d� por�wnywane.
     * 
     * @param exampleEntity przyk�adowa encja
     * @param excludeProperty lista w�a�ciwo�ci, kt�re maj� nie by� uwzgl�dnione
     * @return lista encji
     */
    public List<T> searchByExample(final T exampleEntity, List<String> excludeProperty);

    void persist(T o);

    List<T> searchByCriteria(DetachedCriteria criteria);

    List<T> searchByHQLQuery(String query, Object... params);
    
    Object executeHibernateCallback(HibernateCallback callback);
}